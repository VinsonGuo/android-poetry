package com.guoziwei.poetry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView

/**
 * Created by guoziwei on 2018/4/24 0024.
 */
class ContentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater?.inflate(R.layout.fragment_content, container, false)!!
        val scrollView: HorizontalScrollView = v.findViewById(R.id.scrollView)
        val tvTitle = v.findViewById<VerticalTextView>(R.id.tv_title)
        val tvContent = v.findViewById<VerticalTextView>(R.id.tv_content)
        val tvAuthor = v.findViewById<VerticalTextView>(R.id.tv_author)
        scrollView.post({ scrollView.fullScroll(View.FOCUS_RIGHT) })
//        ViewCompat.setNestedScrollingEnabled(scrollView, true)
        return v
    }
}