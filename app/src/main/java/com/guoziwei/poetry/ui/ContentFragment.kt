package com.guoziwei.poetry.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import com.guoziwei.poetry.R
import com.guoziwei.poetry.util.Utils
import com.guoziwei.poetry.view.VerticalTextView
import java.net.URLEncoder

/**
 * Created by guoziwei on 2018/4/24 0024.
 */
class ContentFragment : Fragment(), View.OnClickListener {

    private var mTvContent: TextView? = null

    val text = "枯藤老树昏鸦\n小桥流水人家\n古道西风瘦马\n夕阳西下\n断肠人在天涯"
    val title = "天净沙·秋思"


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater?.inflate(R.layout.fragment_content, container, false)!!
        val scrollView: HorizontalScrollView = v.findViewById(R.id.scrollView)
        val tvTitle = v.findViewById<VerticalTextView>(R.id.tv_title)
        mTvContent = v.findViewById<VerticalTextView>(R.id.tv_content)
        val tvAuthor = v.findViewById<VerticalTextView>(R.id.tv_author)
        v.findViewById<View>(R.id.tv_author_intro).setOnClickListener(this)
        v.findViewById<View>(R.id.tv_poetry_appreciation).setOnClickListener(this)
        scrollView.post({ scrollView.fullScroll(View.FOCUS_RIGHT) })
//        ViewCompat.setNestedScrollingEnabled(scrollView, true)

        Utils.setText(mTvContent, text)
        return v
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_author_intro -> {
            }
            R.id.tv_poetry_appreciation -> {
                val url = "https://wapbaike.baidu.com/item/${URLEncoder.encode(title, "utf-8")}"
                startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
            }
        }
    }


}