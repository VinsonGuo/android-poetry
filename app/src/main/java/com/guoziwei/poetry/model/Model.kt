package com.guoziwei.poetry.model

/**
 * Created by Administrator on 2018/4/26 0026.
 */
data class Result(val query: Query)
data class Query(val searchinfo: SearchInfo)
data class SearchInfo(val totalhits: Int)