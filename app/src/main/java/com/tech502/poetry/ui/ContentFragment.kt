package com.tech502.poetry.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tech502.poetry.R
import com.tech502.poetry.model.DataBase
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.util.Utils
import com.tech502.poetry.view.HScrollView
import com.tech502.poetry.view.VerticalTextView
import io.reactivex.Observable


/**
 * Created by guoziwei on 2018/4/24 0024.
 */
class ContentFragment : BaseFragment(), View.OnClickListener {

    private var mTvContent: VerticalTextView? = null
    private var mTvCollect: TextView? = null
    private var mScrollView: HScrollView? = null

    private lateinit var poetry: Poetry
    private var isCollect: Boolean = false

    companion object {
        fun newInstance(poetry: Poetry): ContentFragment {
            val fragment = ContentFragment()
            val args = Bundle()
            args.putParcelable("data", poetry)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        poetry = arguments?.getParcelable("data") as Poetry
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_content, container, false)
        mScrollView = v.findViewById(R.id.scrollView)
        val tvTitle = v.findViewById<VerticalTextView>(R.id.tv_title)
        mTvContent = v.findViewById(R.id.tv_content)
        val tvAuthor = v.findViewById<VerticalTextView>(R.id.tv_author)
        v.findViewById<View>(R.id.tv_author_intro).setOnClickListener(this)
        v.findViewById<View>(R.id.tv_share).setOnClickListener(this)
        mTvCollect = v.findViewById(R.id.tv_collect)
        mTvCollect?.setOnClickListener(this)

        mScrollView?.post { mScrollView?.fullScroll(View.FOCUS_RIGHT) }

        Utils.setText(mTvContent, poetry.content)
        val dynasty = when (poetry.dynasty) {
            "T" -> "唐"
            "S" -> "宋"
            else -> ""
        }
        Utils.setText(tvAuthor, "︻$dynasty︼  ${poetry.author}")
        Utils.setText(tvTitle, poetry.title)
        return v
    }


    override fun onResume() {
        super.onResume()
        // 搜索数据库是否保存
        Observable.fromCallable {
            val list = DataBase.getAppDataBase(mContext)
                    .poetryDao()
                    .getByPoetryId(poetry_id = poetry.poetry_id)
            return@fromCallable list.isNotEmpty()
        }
                .compose(Utils.applySchedulers())
                .subscribe({
                    isCollect = it
                    mTvCollect?.setText(if (isCollect) R.string.cancel_collect else R.string.collect)
                }, { Utils.showToast(mContext, it.message) })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_share -> {
                val rxPermissions = RxPermissions(activity!!)
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe {
                            if (it) {
                                share()
                            } else {
                                Utils.showToast(context, "分享失败，请打开读写手机存储的权限")
                            }
                        }
            }
            R.id.tv_author_intro -> {
                PoemActivity.launch(v.context, poetry.author_id, poetry.author)
            }
            R.id.tv_collect -> {
                if (isCollect) {
                    AlertDialog.Builder(v.context)
                            .setMessage("确定要取消收藏么？")
                            .setPositiveButton("确定") { dialog, which ->
                                Observable.fromCallable {
                                    DataBase.getAppDataBase(mContext)
                                            .poetryDao()
                                            .delete(poetry)
                                    return@fromCallable true
                                }
                                        .compose(Utils.applySchedulers())
                                        .subscribe({
                                            isCollect = false
                                            mTvCollect?.setText(if (isCollect) R.string.cancel_collect else R.string.collect)
                                        }, { Utils.showToast(mContext, it.message) })
                            }
                            .setNegativeButton("取消", null)
                            .show()
                } else {
                    Observable.fromCallable {
                        DataBase.getAppDataBase(mContext)
                                .poetryDao()
                                .insert(poetry.apply { update_time = System.currentTimeMillis() })
                        return@fromCallable true
                    }
                            .compose(Utils.applySchedulers())
                            .subscribe({
                                Utils.showToast(context, "收藏成功❤️")
                                isCollect = true
                                mTvCollect?.setText(if (isCollect) R.string.cancel_collect else R.string.collect)
                            }, { Utils.showToast(mContext, it.message) })
                }
            }
        }
    }

    private fun share() {
        Observable.fromCallable {
            Utils.saveScreenshot(mScrollView?.getChildAt(0), mScrollView?.getChildAt(0)!!.width,
                    mScrollView?.getChildAt(0)!!.height)
        }
                .compose(Utils.applySchedulers())
                .subscribe({
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    val screenshotUri = FileProvider.getUriForFile(
                            context!!,
                            context?.packageName + ".provider",
                            it)

                    sharingIntent.type = "image/jpeg"
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
                    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_text)))
                }, {
                    it.printStackTrace()
                    Utils.showToast(context, R.string.share_error)
                })
    }


}