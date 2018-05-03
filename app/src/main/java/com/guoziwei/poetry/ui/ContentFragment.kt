package com.guoziwei.poetry.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.guoziwei.poetry.R
import com.guoziwei.poetry.model.Poetry
import com.guoziwei.poetry.util.Utils
import com.guoziwei.poetry.view.HScrollView
import com.guoziwei.poetry.view.VerticalTextView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import java.net.URLEncoder


/**
 * Created by guoziwei on 2018/4/24 0024.
 */
class ContentFragment : Fragment(), View.OnClickListener {

    private var mTvContent: TextView? = null
    private var mScrollView: HScrollView? = null

    private var poetry: Poetry? = null

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
        poetry = arguments.getParcelable("data")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater?.inflate(R.layout.fragment_content, container, false)!!
        mScrollView = v.findViewById(R.id.scrollView)
        val tvTitle = v.findViewById<VerticalTextView>(R.id.tv_title)
        mTvContent = v.findViewById<VerticalTextView>(R.id.tv_content)
        val tvAuthor = v.findViewById<VerticalTextView>(R.id.tv_author)
        v.findViewById<View>(R.id.tv_author_intro).setOnClickListener(this)
        v.findViewById<View>(R.id.tv_poetry_appreciation).setOnClickListener(this)
        mScrollView?.post({ mScrollView?.fullScroll(View.FOCUS_RIGHT) })

        Utils.setText(mTvContent, poetry?.content)
        Utils.setText(tvAuthor, poetry?.author)
        Utils.setText(tvTitle, poetry?.title)
        return v
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_author_intro -> {
                val rxPermissions = RxPermissions(activity)
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe({
                            if (it) {
                                share()
                            } else {
                                Utils.showToast(context, "分享失败，请打开读写手机存储的权限")
                            }
                        })
            }
            R.id.tv_poetry_appreciation -> {
                val url = "https://wapbaike.baidu.com/item/${URLEncoder.encode(poetry?.title, "utf-8")}"
                startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
            }
        }
    }

    private fun share() {
        Observable.just(Utils.saveScreenshot(mScrollView, mScrollView?.getChildAt(0)!!.width,
                mScrollView?.getChildAt(0)!!.height))
                .compose(Utils.applySchedulers())
                .subscribe({
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    val screenshotUri = FileProvider.getUriForFile(
                            context,
                            context.packageName + ".provider",
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