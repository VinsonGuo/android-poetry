package com.tech502.poetry.ui.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
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

    var keyword: String? = null

    override fun convert(helper: BaseViewHolder, item: Poetry) {

        helper.itemView.run {
            tv_title.text = highlightKeyword(item.title)
            tv_author.text = highlightKeyword(item.author)
            tv_content.text = highlightKeyword(item.contents)
        }
    }

    private fun highlightKeyword(text: String): CharSequence {
        // 高亮关键词
        val msg = SpannableString(Utils.getI18NText(mContext, text))
        if (keyword.isNullOrEmpty()) {
            return msg
        }
        val kw = Utils.getI18NText(mContext, keyword!!)
        var indexOfKeyword = msg.indexOf(kw)
        while (indexOfKeyword >= 0) {
            msg.setSpan(
                    ForegroundColorSpan(
                            ContextCompat.getColor(mContext, R.color.color_red)
                    ),
                    indexOfKeyword,
                    indexOfKeyword + kw.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            indexOfKeyword = msg.indexOf(kw, indexOfKeyword + kw.length)
        }
        return msg
    }
}