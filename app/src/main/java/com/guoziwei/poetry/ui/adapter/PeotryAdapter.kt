package com.guoziwei.poetry.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoziwei.poetry.R
import com.guoziwei.poetry.util.Utils

/**
 * Created by Administrator on 2018/4/26 0026.
 */
class PeotryAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_poetry_list, null) {

    val text = "枯藤老树昏鸦，小桥流水人家。\n古道西风瘦马，夕阳西下，断肠人在天涯。"
    override fun convert(helper: BaseViewHolder, item: String) {
        val tvTitle: TextView = helper.getView(R.id.tv_title)
        val tvAuthor: TextView = helper.getView(R.id.tv_author)
        val tvContent: TextView = helper.getView(R.id.tv_content)
        Utils.setText(tvTitle, item)
        Utils.setText(tvAuthor, item)
        Utils.setText(tvContent, text)
    }
}