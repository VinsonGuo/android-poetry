package com.tech502.poetry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
data class BaseResponse<out T>(val status: Int, val msg: String, val data: T)

@Parcelize
@Entity
data class Poetry(@PrimaryKey(autoGenerate = false) @SerializedName("id") val poetry_id: String,
                  val title: String, val author_id: String,
                  val content: String, val dynasty: String,
                  val author: String, var update_time: Long = System.currentTimeMillis()) : Parcelable

data class Poem(val name: String, @SerializedName("intro_l") val introduce: String)