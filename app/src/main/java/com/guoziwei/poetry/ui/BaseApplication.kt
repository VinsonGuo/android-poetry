package com.guoziwei.poetry.ui

import android.app.Application
import android.content.res.Configuration
import com.guoziwei.poetry.util.Utils
import java.util.*


/**
 * Created by Administrator on 2018/4/26 0026.
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setLocation()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        setLocation()
    }

    private fun setLocation() {
        if (Utils.getLanCode(this) != 0) {
            val resources = this.resources
            val dm = resources.displayMetrics
            val config = resources.configuration
            config.locale = if (Utils.getLanCode(this) == Utils.SIMPLIFY_VALUE) Locale.SIMPLIFIED_CHINESE else Locale.TRADITIONAL_CHINESE
            resources.updateConfiguration(config, dm)
        }
    }

}