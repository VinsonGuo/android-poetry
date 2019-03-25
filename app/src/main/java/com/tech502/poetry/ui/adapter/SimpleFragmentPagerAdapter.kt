package com.tech502.poetry.ui.adapter

import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by guoziwei on 2018/4/3.
 */
class SimpleFragmentPagerAdapter(fm: androidx.fragment.app.FragmentManager,
                                 private val fragments: List<androidx.fragment.app.Fragment>,
                                 private val titles: List<String>? = null)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (!titles.isNullOrEmpty()) {
            titles[position]
        } else super.getPageTitle(position)
    }

}