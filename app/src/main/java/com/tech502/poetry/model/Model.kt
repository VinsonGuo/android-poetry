package com.tech502.poetry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
data class BaseResponse<T>(val status: Int, val msg: String, val data: T) {
    fun isSuccess() = status == 0
}

@Parcelize
@Entity(tableName = "poetry")
data class Poetry(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val title: String,
        val author: String,
        val contents: String,
        var is_like: Boolean,
        var like_time: Long) : Parcelable

data class PoemInfo(
        val name: String,
        val count: Int
)