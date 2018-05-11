package com.tech502.poetry.ui

import android.os.Bundle
import android.view.View
import com.tech502.poetry.R

class PoetryListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poetry_list)
        supportFragmentManager
                .beginTransaction()
//                .replace(R.id.fl_container, PoetryListFragment.newInstance(""))
                .replace(R.id.fl_container, PoetryLikeListFragment.newInstance())
                .commit()

        findViewById<View>(R.id.tv_back).setOnClickListener { finish() }
    }
}
