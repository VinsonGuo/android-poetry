package com.guoziwei.poetry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.guoziwei.poetry.R

class ContentActivity : BaseActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, ContentActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, ContentFragment())
                .commit()
        findViewById<View>(R.id.tv_back).setOnClickListener { finish() }
    }
}
