package com.tech502.poetry.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by guoziwei on 2018/4/3.
 */
class SimpleFragmentPagerAdapter(fm: FragmentManager,
                                 private val fragments: List<Fragment>,
                                 private val titles: List<String>? = null)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (!titles.isNullOrEmpty()) {
            titles[position]
        } else super.getPageTitle(position)
    }

}