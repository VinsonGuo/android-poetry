package com.tech502.poetry.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tech502.poetry.R
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.util.Utils

/**
 * Created by Administrator on 2018/4/26 0026.
 */
class PoetryAdapter : BaseQuickAdapter<Poetry, BaseViewHolder>(R.layout.item_poetry_list, null) {

    override fun convert(helper: BaseViewHolder, item: Poetry) {
        val tvTitle: TextView = helper.getView(R.id.tv_title)
        val tvAuthor: TextView = helper.getView(R.id.tv_author)
        val tvContent: TextView = helper.getView(R.id.tv_content)
        Utils.setText(tvTitle, item.title, false)
        val dynasty = when (item.dynasty) {
            "T" -> "唐"
            "S" -> "宋"
            else -> ""
        }
        Utils.setText(tvAuthor, "[$dynasty] ${item.author}", false)
        Utils.setText(tvContent, item.content, false)
    }
}