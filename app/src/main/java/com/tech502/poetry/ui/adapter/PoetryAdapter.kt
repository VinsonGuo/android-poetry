package com.tech502.poetry.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.R
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.util.Utils
import kotlinx.android.synthetic.main.item_poetry_list.view.*

/**
 * Created by Administrator on 2018/4/26 0026.
 */
class PoetryAdapter : BaseQuickAdapter<Poetry, BaseViewHolder>(R.layout.item_poetry_list, null) {

    override fun convert(helper: BaseViewHolder, item: Poetry) {
        helper.itemView.run {
            Utils.setText(tv_title, item.title, false)
            val dynasty = when (item.dynasty) {
                "T" -> "唐"
                "S" -> "宋"
                else -> ""
            }
            Utils.setText(tv_author, "[$dynasty] ${item.author}", false)
            Utils.setText(tv_content, item.content, false)
        }
    }
}