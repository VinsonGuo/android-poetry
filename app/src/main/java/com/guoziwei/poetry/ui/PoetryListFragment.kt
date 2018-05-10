package com.guoziwei.poetry.ui

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoziwei.poetry.model.BaseResponse
import com.guoziwei.poetry.model.Poetry
import com.guoziwei.poetry.ui.adapter.PoetryAdapter
import com.guoziwei.poetry.util.HttpUtil
import com.guoziwei.poetry.util.Utils
import com.trello.rxlifecycle2.android.FragmentEvent
import com.zqc.opencc.android.lib.ChineseConverter
import com.zqc.opencc.android.lib.ConversionType

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoetryListFragment : ListFragment<Poetry>() {

    private var queryKey: String? = ""

    companion object {
        fun newInstance(queryKey: String): PoetryListFragment {
            val fragment = PoetryListFragment()
            val args = Bundle()
            args.putString("data", queryKey)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryKey = arguments?.getString("data")
    }

    override fun getAdapter(): BaseQuickAdapter<Poetry, out BaseViewHolder> {
        val adapter = PoetryAdapter()
        adapter.setOnItemClickListener { a, view, position ->
            ContentActivity.launch(context, mAdapter.data[position])
        }
        adapter.setEnableLoadMore(true)
        return adapter
    }

    override fun loadData() {
        HttpUtil.create().searchPoetry(ChineseConverter.convert(queryKey, ConversionType.S2T, context), mPage)
                .compose(Utils.applyBizSchedulers<BaseResponse<MutableList<Poetry>>>())
                .compose(bindUntilEvent<BaseResponse<MutableList<Poetry>>>(FragmentEvent.DESTROY))
                .subscribe({
                    loadDataSuccess(it.data)
                }, {
                    Utils.showToast(context, it.message)
                    loadFailed()
                })
    }
}