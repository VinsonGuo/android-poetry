package com.tech502.poetry.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.tech502.poetry.R
import com.tech502.poetry.util.Utils

class SplashActivity : BaseActivity() {

    private var mHandler: Handler = Handler()

    private val callback = {
        toMainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Utils.initLocation(this)
        mHandler.postDelayed(callback, 2000)

    }

    private fun toMainActivity() {
        if (!isFinishing) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(callback)
    }

    override fun onRestart() {
        super.onRestart()
        toMainActivity()
    }
}
