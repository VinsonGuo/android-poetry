package com.guoziwei.poetry

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by guoziwei on 2018/4/3.
 */
class SimpleFragmentPagerAdapter : FragmentStatePagerAdapter {
    private var mFragments: List<Fragment>? = null
    private var mTitles: List<String>? = null


    constructor(fm: FragmentManager, fragments: List<Fragment>) : super(fm) {
        mFragments = fragments
    }


    constructor(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) : this(fm, fragments) {
        mTitles = titles
    }

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getCount(): Int {
        return mFragments!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (mTitles != null && mTitles!!.isNotEmpty()) {
            mTitles!![position]
        } else super.getPageTitle(position)
    }

}