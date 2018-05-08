package com.guoziwei.poetry.ui

import android.app.Application
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.facebook.stetho.Stetho
import com.guoziwei.poetry.util.Utils
import org.litepal.LitePal
import java.util.*


/**
 * Created by Administrator on 2018/4/26 0026.
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Utils.initLocation(this)
    }



}