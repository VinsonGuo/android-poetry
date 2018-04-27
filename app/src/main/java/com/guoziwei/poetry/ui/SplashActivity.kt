package com.guoziwei.poetry.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.guoziwei.poetry.R
import com.guoziwei.poetry.util.Utils
import com.xhinliang.lunarcalendar.LunarCalendar
import java.util.*

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
        val tv = findViewById<TextView>(R.id.tv)
        val now: Calendar = Calendar.getInstance()
        val calendar: LunarCalendar = LunarCalendar.obtainCalendar(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH))
        Utils.setText(tv, calendar.fullLunarStr)

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
