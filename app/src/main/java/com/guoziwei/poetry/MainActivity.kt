package com.guoziwei.poetry

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewpager = findViewById<CustomViewPager>(R.id.view_pager)
        viewpager.setChildId(R.id.scrollView)
        val fragments = MutableList(10, { index: Int -> ContentFragment() })
        val adapter = SimpleFragmentPagerAdapter(supportFragmentManager, fragments)
        viewpager.adapter = adapter
    }
}
