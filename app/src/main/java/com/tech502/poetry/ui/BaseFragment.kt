package com.tech502.poetry.ui

import android.content.Context
import com.trello.rxlifecycle2.components.support.RxFragment

open class BaseFragment : RxFragment() {

    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}