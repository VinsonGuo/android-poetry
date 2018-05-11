package com.tech502.poetry.ui

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.tech502.poetry.R


class AboutUsActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        findViewById<TextView>(R.id.tv_title).text = getVersion()
        findViewById<TextView>(R.id.tv_mail_address).setOnClickListener(this)
    }

    private fun getVersion(): String {
        return try {
            val version = packageManager.getPackageInfo(packageName, 0).versionName
            getString(R.string.app_name) + " v" + version
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_mail_address -> onClickCopy()
        }
    }

    private fun onClickCopy() {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = getString(R.string.mail502tech)
        Toast.makeText(this, "已复制：" + getString(R.string.mail502tech), Toast.LENGTH_LONG).show()
    }

}

