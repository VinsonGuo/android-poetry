package com.tech502.poetry.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech502.poetry.R
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.model.Poetry2
import com.tech502.poetry.util.Utils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.fragment_content.view.*
import kotlinx.coroutines.*


/**
 * Created by guoziwei on 2018/4/24 0024.
 */
class ContentFragment : BaseFragment() {


    private lateinit var poetry: Poetry2

    companion object {
        fun newInstance(poetry: Poetry2): ContentFragment {
            val fragment = ContentFragment()
            val args = Bundle()
            args.putParcelable("data", poetry)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        poetry = arguments?.getParcelable("data") as Poetry2
    }

    private val viewModel: ContentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_content, container, false)

        v.scrollView.post { v.scrollView.fullScroll(View.FOCUS_RIGHT) }

        Utils.setText(v.tv_content, poetry.contents)
//        val dynasty = when (poetry.dynasty) {
//            "T" -> "唐"
//            "S" -> "宋"
//            else -> ""
//        }
//        Utils.setText(v.tv_author, "︻$dynasty︼  ${poetry.author}")
        Utils.setText(v.tv_title, poetry.title)

//        v.tv_author_intro.setOnClickListener { PoemActivity.launch(v.context, poetry.author_id, poetry.author) }
        v.tv_share.setOnClickListener {
            AndPermission.with(this@ContentFragment)
                    .runtime()
                    .permission(Permission.Group.STORAGE)
                    .onGranted {
                        viewModel.share(v.scrollView)
                    }
                    .onDenied {
                        Utils.showToast(mContext, "分享失败，请打开读写手机存储的权限")
                    }
                    .start()

        }
//        v.tv_collect.setOnClickListener { viewModel.setCollect(poetry) }

        viewModel.isCollect.observe(this, Observer {
            v.tv_collect.setText(if (it) R.string.cancel_collect else R.string.collect)
        })
        viewModel.message.observe(this, Observer { Utils.showToast(mContext, it) })
        viewModel.shareFile.observe(this, Observer {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            val screenshotUri = FileProvider.getUriForFile(
                    mContext,
                    mContext.packageName + ".provider",
                    it)

            sharingIntent.type = "image/jpeg"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_text)))
        })
        return v
    }


    override fun onResume() {
        super.onResume()
//        viewModel.loadCollect(poetry)
    }


}