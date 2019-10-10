package com.tech502.poetry.util

import com.tech502.poetry.model.BaseResponse
import com.tech502.poetry.model.Poem
import com.tech502.poetry.model.Poetry
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Administrator on 2018/4/26 0026.
 */
interface ApiService {

    @POST("poem/poemInfo")
    suspend fun poemInfo(@Query("author_id") author_id: String, @Query("author_name") author_name: String): BaseResponse<Poem>


    @POST("poetrys/searchPoetry")
    suspend fun searchPoetry(@Query("keyword") queryKey: String?, @Query("page") page: Int): BaseResponse<MutableList<Poetry>>

    @POST("poetrys/randomTenPoetry")
    suspend fun randomTenPoetry(): BaseResponse<MutableList<Poetry>>
}