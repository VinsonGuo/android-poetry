package com.guoziwei.poetry.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoziwei.poetry.ui.adapter.PeotryAdapter

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoetryListFragment : ListFragment<String>() {
    override fun getAdapter(): BaseQuickAdapter<String, out BaseViewHolder> {
        val adapter = PeotryAdapter()
        adapter.setOnItemClickListener { a, view, position ->
            ContentActivity.launch(context)
        }
        return adapter
    }

    override fun loadData() {
        val list = MutableList(100, { index: Int ->
            "测试$index"
        })
        loadDataSuccess(list)
    }
}