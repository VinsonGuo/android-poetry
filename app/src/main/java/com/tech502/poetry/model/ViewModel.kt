package com.tech502.poetry.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tech502.poetry.util.Utils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class PoetryContentViewModel : ViewModel() {
    val isCollect: MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    private var disposable: Disposable? = null

    fun setCollect(context: Context, poetry: Poetry) {
        if (isCollect.value != true) {
            disposable = Observable.fromCallable {
                DataBase.getAppDataBase(context)
                        .poetryDao()
                        .insert(poetry.apply { update_time = System.currentTimeMillis() })
                return@fromCallable true
            }
                    .compose(Utils.applySchedulers())
                    .subscribe({
                        isCollect.value = true
                        message.value = "收藏成功❤️"
                    }, { message.value = it.message })
        } else {
            disposable = Observable.fromCallable {
                DataBase.getAppDataBase(context)
                        .poetryDao()
                        .delete(poetry)
                return@fromCallable true
            }
                    .compose(Utils.applySchedulers())
                    .subscribe({
                        isCollect.value = false
                    }, { message.value = it.message })
        }
    }

    fun loadCollect(context: Context, poetry: Poetry) {
        // 搜索数据库是否保存
        disposable = Observable.fromCallable {
            val list = DataBase.getAppDataBase(context)
                    .poetryDao()
                    .getByPoetryId(poetry_id = poetry.poetry_id)
            return@fromCallable list.isNotEmpty()
        }
                .compose(Utils.applySchedulers())
                .subscribe({
                    isCollect.value = it
                }, { message.value = it.message })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}