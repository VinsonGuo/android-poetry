package com.tech502.poetry.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.ui.adapter.PoetryAdapter
import org.litepal.crud.DataSupport

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoetryLikeListFragment : ListFragment<Poetry>() {


    companion object {
        fun newInstance(): PoetryLikeListFragment {
            val fragment = PoetryLikeListFragment()
            return fragment
        }
    }


    override fun getAdapter(): BaseQuickAdapter<Poetry, out BaseViewHolder> {
        val adapter = PoetryAdapter()
        adapter.setOnItemClickListener { a, view, position ->
            ContentActivity.launch(view.context, mAdapter.data[position])
        }
        adapter.setEnableLoadMore(true)
        return adapter
    }

    override fun loadData() {
        val list = DataSupport
                .limit(pageCount)
                .offset(mPage * pageCount)
                .order("id desc")
                .find(Poetry::class.java)
        loadDataSuccess(list)
    }
}