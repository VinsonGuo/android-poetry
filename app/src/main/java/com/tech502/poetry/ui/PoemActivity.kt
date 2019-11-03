package com.tech502.poetry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.tech502.poetry.R
import com.tech502.poetry.util.Utils
import kotlinx.android.synthetic.main.activity_poem.*

class PoemActivity : BaseActivity() {

    private val viewModel by viewModels<PoemViewModel>()

    companion object {
        fun launch(context: Context, id: String?, name: String?) {
            val intent = Intent(context, PoemActivity::class.java)
            intent.putExtra("data", id)
            intent.putExtra("name", name)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poem)
        val id = intent.getStringExtra("data")
        val name = intent.getStringExtra("name")

        tv_back.setOnClickListener { finish() }

        viewModel.poemModel.observe(this, Observer {
            if (it.isSuccess) {
                Utils.setText(tv_poem, it.data?.introduce ?: "", false)
                Utils.setText(tv_title, it.data?.name ?: "")
            } else {
                Utils.showToast(this@PoemActivity, it.msg)
            }
        })
        viewModel.loadData(id, name)
    }

}
