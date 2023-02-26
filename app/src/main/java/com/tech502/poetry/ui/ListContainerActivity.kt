package com.tech502.poetry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tech502.poetry.R
import kotlinx.android.synthetic.main.activity_list_container.*

class ListContainerActivity : BaseActivity() {

    enum class Type {
        LikeList, PoemList, PoetryListByPoem
    }

    companion object {
        fun launch(context: Context, type: Type, args: String = "") {
            context.startActivity(Intent(context, ListContainerActivity::class.java)
                    .putExtra("type", type.name)
                    .putExtra("args", args))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)
        val type = intent.getStringExtra("type")
        val args = intent.getStringExtra("args")
        val fragment = when (type) {
            Type.LikeList.name -> PoetryLikeListFragment.newInstance()
            Type.PoemList.name -> PoemListFragment.newInstance()
            else -> PoetryListFragment.newInstance(args, PoetryListFragment.Type.Author)
        }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()

        tv_title.setText(when (type) {
            Type.LikeList.name -> R.string.collect_list
            Type.PoemList.name -> R.string.poem_list
            else -> R.string.poem_list
        })
        tv_back.setOnClickListener { finish() }
    }
}
