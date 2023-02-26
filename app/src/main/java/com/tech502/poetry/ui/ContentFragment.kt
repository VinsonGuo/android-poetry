package com.tech502.poetry.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tech502.poetry.R
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.util.Utils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.fragment_content.view.*


/**
 * Created by guoziwei on 2018/4/24 0024.
 */
class ContentFragment : BaseFragment() {


    private lateinit var poetry: Poetry

    private lateinit var viewModel: ContentViewModel

    private val chromeTab by lazy {
        CustomTabsIntent.Builder()
                .setToolbarColor(resources.getColor(R.color.colorPrimaryDark))
                .build()
    }

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
        viewModel = ViewModelProviders.of(this).get("ContentViewModel" + poetry.id.toString(), ContentViewModel::class.java)

        val v: View = inflater.inflate(R.layout.fragment_content, container, false)

        v.scrollView.post { v.scrollView.fullScroll(View.FOCUS_RIGHT) }

        Utils.setText(v.tv_content, poetry.contents)

        Utils.setText(v.tv_author, poetry.author)
        Utils.setText(v.tv_title, poetry.title)

        v.tv_author_intro.setOnClickListener {
            chromeTab.launchUrl(mContext, "https://zh.wikipedia.org/wiki/${poetry.author}".toUri())
        }
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
        v.tv_collect.setOnClickListener {
            if (viewModel.isCollect.value == true) { //取消收藏弹窗
                AlertDialog.Builder(mContext)
                        .setMessage(R.string.cancel_like_msg)
                        .setPositiveButton(R.string.confirm) { dialog, _ ->
                            viewModel.setCollect(poetry)
                            dialog.dismiss()
                        }
                        .show()
            } else {
                viewModel.setCollect(poetry)
            }
        }

        viewModel.isCollect.observe(viewLifecycleOwner, Observer {
            v.tv_collect.setText(if (it) R.string.cancel_collect else R.string.collect)
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            if (lifecycle.currentState > Lifecycle.State.STARTED) {
                Utils.showToast(mContext, it)
            }
        })
        viewModel.shareFile.observe(viewLifecycleOwner, Observer {
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
        viewModel.loadCollect(poetry)
    }


}