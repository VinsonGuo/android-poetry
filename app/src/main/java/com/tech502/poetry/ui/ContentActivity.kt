package com.tech502.poetry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tech502.poetry.R
import com.tech502.poetry.model.Poetry

class ContentActivity : BaseActivity() {

    companion object {
        fun launch(context: Context, poetry: Poetry) {
            val intent = Intent(context, ContentActivity::class.java)
            intent.putExtra("data", poetry)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val poetry = intent.getParcelableExtra("data") as Poetry
        supportFragmentManager
                .beginTransaction()
//                .replace(R.id.fl_container, ContentFragment.newInstance(poetry))
                .commit()
        findViewById<View>(R.id.tv_back).setOnClickListener { finish() }
    }
}
