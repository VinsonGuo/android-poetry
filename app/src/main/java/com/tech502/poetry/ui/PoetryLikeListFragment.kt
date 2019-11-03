package com.tech502.poetry.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.ui.adapter.PoetryAdapter
import com.tech502.poetry.util.Repository
import com.tech502.poetry.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoetryLikeListFragment : ListFragment<Poetry>(){

    private val viewModel by viewModels<PoetryLikeListViewModel>()

    companion object {
        fun newInstance(): PoetryLikeListFragment {
            val fragment = PoetryLikeListFragment()
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listResource.observe(this, Observer {
            if(it.isSuccess) {
                loadDataSuccess(it.data)
            }else{
                Utils.showToast(mContext, it.msg)
                loadFailed()
            }
        })
    }

    override fun getAdapter(): BaseQuickAdapter<Poetry, out BaseViewHolder> {
        val adapter = PoetryAdapter()
        adapter.setOnItemClickListener { a, view, position ->
            ContentActivity.launch(view.context, mAdapter.data[position])
        }
        adapter.setEnableLoadMore(true)
        return adapter
    }

    override fun loadData() = viewModel.loadListData(mPage)

}