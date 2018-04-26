package com.guoziwei.poetry.ui

import android.os.Bundle
import com.guoziwei.poetry.R
import com.guoziwei.poetry.util.Utils
import com.gyf.barlibrary.ImmersionBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.util.*

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

        Utils.initLocation(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        mImmersionBar?.destroy()
    }
}