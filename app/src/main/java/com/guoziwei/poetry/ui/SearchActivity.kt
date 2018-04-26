package com.guoziwei.poetry.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import com.guoziwei.poetry.R
import immortalz.me.library.TransitionsHeleper
import immortalz.me.library.bean.InfoBean
import immortalz.me.library.method.ColorShowMethod

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        TransitionsHeleper.build(this)
                .setShowMethod(object : ColorShowMethod(R.color.colorPrimary, R.color.colorAccent) {
                    override fun loadPlaceholder(bean: InfoBean<*>, placeholder: ImageView) {
                        val set = AnimatorSet()
                        set.playTogether(
                                ObjectAnimator.ofFloat(placeholder, "rotation", 0f, 180f),
                                ObjectAnimator.ofFloat(placeholder, "scaleX", 1f, 0f),
                                ObjectAnimator.ofFloat(placeholder, "scaleY", 1f, 0f)
                        )
                        set.interpolator = AccelerateInterpolator()
                        set.start()
                    }

                    override fun loadTargetView(bean: InfoBean<*>?, targetView: View?) {
                    }

                })
                .setExposeColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show()
    }

    override fun onDestroy() {
        TransitionsHeleper.unbind(this)
        super.onDestroy()
    }
}
