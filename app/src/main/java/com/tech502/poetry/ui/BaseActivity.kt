package com.tech502.poetry.ui

import android.os.Bundle
import com.gyf.barlibrary.ImmersionBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.umeng.analytics.MobclickAgent

/**
 * Created by Administrator on 2018/4/26 0026.
 */
open class BaseActivity : RxAppCompatActivity() {
    private var mImmersionBar: ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true)
        mImmersionBar?.init()   //所有子类都将继承这些相同的属性

    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        mImmersionBar?.destroy()
    }
}