package com.tech502.poetry.ui

import android.app.Application
import android.content.res.Configuration
import com.tech502.poetry.database.DataBaseHelper
import com.tech502.poetry.util.Utils


/**
 * Created by Administrator on 2018/4/26 0026.
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DataBaseHelper.init(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Utils.initLocation(this)
    }


}