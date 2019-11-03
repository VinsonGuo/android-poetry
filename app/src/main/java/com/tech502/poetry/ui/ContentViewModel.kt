package com.tech502.poetry.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.util.Repository
import com.tech502.poetry.util.Utils
import com.tech502.poetry.view.HScrollView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ContentViewModel : ViewModel() {

    val isCollect: MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    val shareFile = MutableLiveData<File>()

    private val repository by lazy { Repository.instance }

    fun setCollect(poetry: Poetry) {
        viewModelScope.launch {
            if (isCollect.value != true) {
                val res = repository.insertLocalPoetry(poetry.apply { update_time = System.currentTimeMillis() })
                if (res.isSuccess) {
                    isCollect.value = true
                    message.value = "收藏成功❤️"
                } else {
                    message.value = res.msg
                }
            } else {
                val res = repository.deleteLocalPoetry(poetry)
                if (res.isSuccess) {
                    isCollect.value = false
                } else {
                    message.value = res.msg
                }
            }
        }
    }

    fun loadCollect(poetry: Poetry) {
        // 搜索数据库是否保存
        viewModelScope.launch {
            val res = repository.getLocalPoetryByPoetryId(poetry.poetry_id)
            if (res.isSuccess) {
                isCollect.value = res.data?.isNotEmpty()
            } else {
                message.value = res.msg
            }
        }
    }

    fun share(v: HScrollView) {
        viewModelScope.launch {
            val file = withContext(Dispatchers.IO) {
                Utils.saveScreenshot(v.getChildAt(0), v.getChildAt(0)!!.width,
                        v.getChildAt(0)!!.height)
            }
            shareFile.value = file
        }
    }


}