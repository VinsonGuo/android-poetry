package com.guoziwei.poetry.ui

import android.os.Bundle
import android.view.View
import com.guoziwei.poetry.R

class PoetryListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poetry_list)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, PoetryListFragment())
                .commit()

        findViewById<View>(R.id.tv_back).setOnClickListener { finish() }
    }
}
