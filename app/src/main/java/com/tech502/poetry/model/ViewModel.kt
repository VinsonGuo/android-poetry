package com.tech502.poetry.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PoetryContentViewModel : ViewModel() {
    val isCollect: MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()

    fun setCollect(context: Context, poetry: Poetry) {
        viewModelScope.launch {
            try {
                if (isCollect.value != true) {
                    withContext(Dispatchers.IO) {
                        DataBase.getAppDataBase(context)
                                .poetryDao()
                                .insert(poetry.apply { update_time = System.currentTimeMillis() })
                    }
                    isCollect.value = true
                    message.value = "收藏成功❤️"

                } else {
                    withContext(Dispatchers.IO) {
                        DataBase.getAppDataBase(context)
                                .poetryDao()
                                .delete(poetry)
                    }
                    isCollect.value = false
                }
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }

    fun loadCollect(context: Context, poetry: Poetry) {
        // 搜索数据库是否保存
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    DataBase.getAppDataBase(context)
                            .poetryDao()
                            .getByPoetryId(poetry_id = poetry.poetry_id)
                }
                isCollect.value = list.isNotEmpty()
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }


}