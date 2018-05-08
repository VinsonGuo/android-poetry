package com.guoziwei.poetry.model

import com.google.gson.annotations.SerializedName
import org.litepal.annotation.Column
import org.litepal.crud.DataSupport
import java.io.Serializable

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
data class BaseResponse<out T>(val status: Int, val msg: String, val data: T)

data class PageResponse<out T>(val data: T)

data class Poetry(@SerializedName("id") @Column(unique = true) val poetry_id: String,
                  val title: String, val yunlv_rule: String, val author_id: String,
                  val content: String, val dynasty: String, val author: String) : Serializable, DataSupport() {

    constructor() : this("", "", "", "", "", "", "")
}