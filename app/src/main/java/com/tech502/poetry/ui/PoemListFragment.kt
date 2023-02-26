package com.tech502.poetry.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.R
import com.tech502.poetry.model.PoemInfo
import com.tech502.poetry.util.Utils
import kotlinx.android.synthetic.main.item_poem_list.view.*

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoemListFragment : ListFragment<PoemInfo>() {

    private val viewModel by viewModels<PoemListViewModel>()

    companion object {
        fun newInstance(): PoemListFragment {
            val fragment = PoemListFragment()
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listResource.observe(this, Observer {
            if (it.isSuccess) {
                loadDataSuccess(it.data)
            } else {
                Utils.showToast(mContext, it.msg)
                loadFailed()
            }
        })
    }

    override fun getAdapter(): BaseQuickAdapter<PoemInfo, out BaseViewHolder> =
            object : BaseQuickAdapter<PoemInfo, BaseViewHolder>(R.layout.item_poem_list) {
                init {
                    setEnableLoadMore(true)
                    setOnItemClickListener { _, _, position ->
                        ListContainerActivity.launch(mContext, ListContainerActivity.Type.PoetryListByPoem, data[position].name)
                    }
                }

                override fun convert(helper: BaseViewHolder, item: PoemInfo) {
                    helper.itemView.run {
                        Utils.setText(tv_name, item.name, false)
                        tv_count.text = item.count.toString()
                    }
                }

            }

    override fun loadData() = viewModel.loadListData(mPage)

}