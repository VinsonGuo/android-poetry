package com.tech502.poetry.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.model.DataBase
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.ui.adapter.PoetryAdapter
import com.tech502.poetry.util.Utils
import kotlinx.coroutines.*

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
class PoetryLikeListFragment : ListFragment<Poetry>(), CoroutineScope by MainScope() {


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
        launch {
            try {
                val it = withContext(Dispatchers.IO) {
                    DataBase.getAppDataBase(mContext)
                            .poetryDao()
                            .getByPage(mPage)
                }
                loadDataSuccess(it)
            } catch (e: Exception) {
                Utils.showToast(mContext, e.message)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}