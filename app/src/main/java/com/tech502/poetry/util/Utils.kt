package com.tech502.poetry.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.os.LocaleList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.tech502.poetry.R
import com.tech502.poetry.ui.MainActivity
import com.tech502.poetry.view.VerticalTextView
import com.zqc.opencc.android.lib.ChineseConverter
import com.zqc.opencc.android.lib.ConversionType
import kotlinx.coroutines.CoroutineExceptionHandler
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Field
import java.util.*

/**
 * Created by guo on 2017/12/2.
 */

object Utils {

    const val LAN_CODE = "lan_code"
    const val SIMPLIFY_VALUE = 1
    const val TRADITIONAL_VALUE = 2

    fun defaultCoroutineExceptionHandler(context: Context): CoroutineExceptionHandler =
            CoroutineExceptionHandler { coroutineContext, throwable ->
                showToast(context, throwable.message ?: context.getString(R.string.error))
            }

    /**
     * dp2px
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 实现文本复制功能
     *
     * @param text
     */
    fun copyText(context: Context, text: String) {
        // 得到剪贴板管理器
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val cmb = context
                    .getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
            cmb.text = text.trim { it <= ' ' }
        } else {
            val cmb = context
                    .getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            cmb.text = text.trim { it <= ' ' }
        }
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(x)

        } catch (e1: Exception) {
            statusBarHeight = 0
            e1.printStackTrace()
        }

        return statusBarHeight
    }

    fun getLanCode(context: Context): Int {
        val sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE)
        return sp.getInt(LAN_CODE, 0)
    }

    fun toggleSimplify(context: Context) {
        val resources = context.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        val value: Int
        if (getLanCode(context) == 0) {  // 默認情況，要看系統的語言
            value = if (config.locale == Locale.SIMPLIFIED_CHINESE) TRADITIONAL_VALUE else SIMPLIFY_VALUE
        } else {
            value = if (getLanCode(context) == SIMPLIFY_VALUE) TRADITIONAL_VALUE else SIMPLIFY_VALUE
        }
        val sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE)
        if (sp.edit().putInt(LAN_CODE, value).commit()) {

            // 应用用户选择语言
            val locale = if (value == SIMPLIFY_VALUE) Locale.SIMPLIFIED_CHINESE else Locale.TRADITIONAL_CHINESE
            config.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.locales = LocaleList(locale!!)
            }
            resources.updateConfiguration(config, dm)
            showToast(context, R.string.change_language_msg)
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    fun initLocation(context: Context) {
        if (Utils.getLanCode(context) != 0) {
            val resources = context.resources
            val dm = resources.displayMetrics
            val config = resources.configuration
            val newLocal = if (Utils.getLanCode(context) == Utils.SIMPLIFY_VALUE) Locale.SIMPLIFIED_CHINESE else Locale.TRADITIONAL_CHINESE
            Locale.setDefault(newLocal)
            config.setLocale(newLocal)
            resources.updateConfiguration(config, dm)

        }
    }

    fun showToast(context: Context, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, @StringRes id: Int) {
        showToast(context, context.getString(id))
    }


    fun setText(tv: TextView, @StringRes id: Int) {
        setText(tv, tv.context.getString(id))
    }


    @JvmOverloads
    fun setText(tv: TextView, text: String, filterSymbol: Boolean = true) {
        var text = text
        text = text.replace("\\|".toRegex(), "\n")
        if (filterSymbol) {
            text = text.replace("[，。？！、]".toRegex(), "\t")
        }
        if (tv is VerticalTextView) {
            text = text.replace('“', '﹃')
                    .replace('”', '﹄')
                    .replace('‘', '﹁')
                    .replace('’', '﹂')
                    .replace('「', '﹁')
                    .replace('」', '﹂')
                    .replace('（', '︵')
                    .replace('）', '︶')
                    .replace('《', '︻')
                    .replace('》', '︼')
        }
        val config = tv.context.resources.configuration
        if (config.locale == Locale.TRADITIONAL_CHINESE) {
            tv.text = ChineseConverter.convert(text, ConversionType.S2T, tv.context)
            //            tv.setText(text);
        } else {
            tv.text = ChineseConverter.convert(text, ConversionType.T2S, tv.context)
        }
    }

    fun closeKeyboard(context: Activity) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = context.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(context)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Throws(Exception::class)
    fun saveScreenshot(view: View, width: Int, height: Int): File {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        val imagePath = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + "/share.jpg")
        val fos: FileOutputStream
        fos = FileOutputStream(imagePath)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
        return imagePath
    }
}