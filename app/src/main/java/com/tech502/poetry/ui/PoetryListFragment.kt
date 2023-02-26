package com.tech502.poetry.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.ui.adapter.PoetryAdapter
import com.tech502.poetry.util.Utils
import com.zqc.opencc.android.lib.ChineseConverter
import com.zqc.opencc.android.lib.ConversionType

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoetryListFragment : ListFragment<Poetry>() {

    private var queryKey: String? = null
    private var type: String? = null

    private val viewModel by viewModels<PoetryListViewModel>()

    enum class Type { Full, Author }

    companion object {
        fun newInstance(queryKey: String, type:Type = Type.Full): PoetryListFragment {
            val fragment = PoetryListFragment()
            val args = Bundle()
            args.putString("data", queryKey)
            args.putString("type", type.name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryKey = arguments?.getString("data")
        type = arguments?.getString("type")
        viewModel.listResource.observe(this, Observer {
            if (it.isSuccess) {
                loadDataSuccess(it.data)
            } else {
                Utils.showToast(mContext, it.msg)
                loadFailed()
                Log.e("zmsc", "db error", it.throwable)
            }
        })
    }

    override fun getAdapter(): BaseQuickAdapter<Poetry, out BaseViewHolder> {
        val adapter = PoetryAdapter()
        adapter.setOnItemClickListener { a, view, position ->
            ContentActivity.launch(mContext, mAdapter.data[position])
        }
        adapter.setEnableLoadMore(true)
        return adapter
    }

    override fun loadData() {
        (mAdapter as PoetryAdapter).keyword = queryKey
        val s = ChineseConverter.convert(queryKey, ConversionType.T2S, context)
        val t = ChineseConverter.convert(queryKey, ConversionType.S2T, context)
        val type = type ?: Type.Full
        if(type == Type.Full.name) {
            viewModel.loadListData(s, t, mPage)
        }else{
            viewModel.loadListDataByAuth(s, t, mPage)
        }
    }
}