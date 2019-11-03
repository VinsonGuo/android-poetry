package com.tech502.poetry.model

data class Resource<out T>(val isSuccess: Boolean,
                       val msg: String,
                       val data: T? = null,
                       val throwable: Throwable? = null)