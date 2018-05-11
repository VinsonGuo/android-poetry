package com.tech502.poetry.ui

import android.app.Application
import android.content.res.Configuration
import com.tech502.poetry.util.Utils
import com.umeng.commonsdk.UMConfigure
import org.litepal.LitePal


/**
 * Created by Administrator on 2018/4/26 0026.
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
//        Stetho.initializeWithDefaults(this)

        UMConfigure.init(this, "5af544948f4a9d482b000116", "official", 1, null)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Utils.initLocation(this)
    }


}