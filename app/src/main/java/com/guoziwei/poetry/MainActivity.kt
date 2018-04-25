package com.guoziwei.poetry

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import cn.youngkaaa.yviewpager.YViewPager
import com.ToxicBakery.viewpager.transforms.*
import com.yalantis.guillotine.animation.GuillotineAnimation


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewpager = findViewById<com.guoziwei.poetry.YViewPager>(R.id.view_pager)
        val fragments = MutableList(10, { index: Int -> ContentFragment() })
        val adapter = SimpleFragmentPagerAdapter(supportFragmentManager, fragments)
        viewpager.adapter = adapter
        viewpager.setPageTransformer(false, DepthPageTransformer())
        val rootView = findViewById<FrameLayout>(R.id.root)
        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_menu, rootView, false)
        rootView.addView(guillotineMenu)

        GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.tv_close), findViewById(R.id.tv_menu))
                .setActionBarViewForAnimation(findViewById(R.id.toolbar))
                .setClosedOnStart(true)
                .build()
    }

}
