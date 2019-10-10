package com.tech502.poetry.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer
import com.tech502.poetry.R
import com.tech502.poetry.model.isSuccess
import com.tech502.poetry.ui.adapter.SimpleFragmentPagerAdapter
import com.tech502.poetry.util.HttpUtil
import com.tech502.poetry.util.Utils
import com.yalantis.guillotine.animation.GuillotineAnimation
import com.yalantis.guillotine.interfaces.GuillotineListener
import immortalz.me.library.TransitionsHeleper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guillotine_menu.view.*
import kotlinx.coroutines.*
import kotlin.math.max


class MainActivity : BaseActivity(), CoroutineScope by MainScope(), View.OnClickListener {

    private var adapter: SimpleFragmentPagerAdapter? = null

    private var mIsMenuOpen = false

    private val fragments = mutableListOf<ContentFragment>()

    private var mMenu: GuillotineAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_search.setOnClickListener(this)
        view_pager.setPageTransformer(false, CubeOutTransformer())
        view_pager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == fragments.size - 1) {
                    Handler().postDelayed({ requestData() }, 500)
                }
            }

        })
        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_menu, root, false)
        root.addView(guillotineMenu)
        guillotineMenu.tv_toggle_simplify.setOnClickListener(this)
        guillotineMenu.tv_menu_list.setOnClickListener(this)
        guillotineMenu.tv_menu_about.setOnClickListener(this)

        mMenu = GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.tv_close, tv_menu)
                .setActionBarViewForAnimation(findViewById(R.id.toolbar))
                .setClosedOnStart(true)
                .setGuillotineListener(object : GuillotineListener {
                    override fun onGuillotineClosed() {
                        mIsMenuOpen = false
                    }

                    override fun onGuillotineOpened() {
                        mIsMenuOpen = true
                    }
                })
                .build()

        requestData()
    }

    private fun requestData() {
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    HttpUtil.create().randomTenPoetry()
                }
                if (response.isSuccess()) {
                    fragments += response.data.map { item -> ContentFragment.newInstance(item) }
                    adapter = SimpleFragmentPagerAdapter(supportFragmentManager, fragments)
                    view_pager.adapter = adapter
                    view_pager.setCurrentItem(max(0, fragments.size - 11), false)
                } else {
                    Utils.showToast(this@MainActivity, response.msg)
                }
            } catch (e: Exception) {
                Utils.showToast(this@MainActivity, e.message)
            }
        }
    }

    override fun onBackPressed() {
        if (mIsMenuOpen) {
            mMenu?.close()
        } else {
            super.onBackPressed()
        }

    }



    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_toggle_simplify -> Utils.toggleSimplify(this)
            R.id.tv_search -> TransitionsHeleper.startActivity(this, SearchActivity::class.java, v)
            R.id.tv_menu_list -> startActivity(Intent(this, PoetryListActivity::class.java))
            R.id.tv_menu_about -> startActivity(Intent(this, AboutUsActivity::class.java))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
