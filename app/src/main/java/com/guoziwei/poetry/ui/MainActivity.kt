package com.guoziwei.poetry.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer
import com.guoziwei.poetry.R
import com.guoziwei.poetry.ui.adapter.SimpleFragmentPagerAdapter
import com.guoziwei.poetry.util.HttpUtil
import com.guoziwei.poetry.util.Utils
import com.trello.rxlifecycle2.android.ActivityEvent
import com.yalantis.guillotine.animation.GuillotineAnimation
import immortalz.me.library.TransitionsHeleper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvSearch = findViewById<TextView>(R.id.tv_search)
        tvSearch.setOnClickListener(this)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val viewpager = findViewById<com.guoziwei.poetry.view.YViewPager>(R.id.view_pager)
        val fragments = MutableList(10, { index: Int -> ContentFragment() })
        val adapter = SimpleFragmentPagerAdapter(supportFragmentManager, fragments)
        viewpager.adapter = adapter
        viewpager.setPageTransformer(false, DepthPageTransformer())
        val rootView = findViewById<FrameLayout>(R.id.root)
        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_menu, rootView, false)
        rootView.addView(guillotineMenu)
        guillotineMenu.findViewById<View>(R.id.tv_toggle_simplify).setOnClickListener(this)

        GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.tv_close), findViewById(R.id.tv_menu))
                .setActionBarViewForAnimation(findViewById(R.id.toolbar))
                .setClosedOnStart(true)
                .build()
        HttpUtil.create().hitCountCheck("query", "json", "search", "java")
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d("test", result.toString()) },
                        { error -> Log.e("test", error.message) }
                )
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_toggle_simplify -> Utils.toggleSimplify(this)
            R.id.tv_search -> TransitionsHeleper.startActivity(this, SearchActivity::class.java, v)
        }
    }


}
