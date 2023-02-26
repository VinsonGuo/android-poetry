package com.tech502.poetry.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.tech502.poetry.R

/**
 * Created by Administrator on 2018/4/26 0026.
 */
open class BaseActivity : AppCompatActivity() {
    private var mImmersionBar: ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimaryDark)
                .navigationBarColor(R.color.colorPrimary)
                .autoNavigationBarDarkModeEnable(true)
        mImmersionBar?.init()   //所有子类都将继承这些相同的属性

    }


}