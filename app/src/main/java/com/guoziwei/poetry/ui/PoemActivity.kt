package com.guoziwei.poetry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.guoziwei.poetry.R
import com.guoziwei.poetry.model.BaseResponse
import com.guoziwei.poetry.model.Poem
import com.guoziwei.poetry.util.HttpUtil
import com.guoziwei.poetry.util.Utils
import com.trello.rxlifecycle2.android.ActivityEvent

class PoemActivity : BaseActivity() {

    companion object {
        fun launch(context: Context, id: String?) {
            val intent = Intent(context, PoemActivity::class.java)
            intent.putExtra("data", id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poem)
        val id = intent.getStringExtra("data")

        findViewById<View>(R.id.tv_back).setOnClickListener { finish() }
        HttpUtil.create().poemInfo(id)
                .compose(Utils.applyBizSchedulers<BaseResponse<Poem>>())
                .compose(bindUntilEvent<BaseResponse<Poem>>(ActivityEvent.DESTROY))
                .subscribe({
                    Utils.setText(findViewById<TextView>(R.id.tv_poem), it.data.introduce, false)
                    Utils.setText(findViewById<TextView>(R.id.tv_title), it.data.name)
                }, {
                    Utils.showToast(this, it.message)
                })
    }
}
