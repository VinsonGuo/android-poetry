package com.guoziwei.poetry.ui

import android.os.Bundle
import com.guoziwei.poetry.R

class PeotryListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peotry_list)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, PeotryListFragment())
                .commit()
    }
}
