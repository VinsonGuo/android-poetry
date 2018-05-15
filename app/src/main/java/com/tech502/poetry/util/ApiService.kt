package com.tech502.poetry.util

import com.tech502.poetry.model.BaseResponse
import com.tech502.poetry.model.Poem
import com.tech502.poetry.model.Poetry
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Administrator on 2018/4/26 0026.
 */
interface ApiService {

    @POST("poetrys/randomTenPoetry")
    fun randomTenPoetry(): Observable<BaseResponse<MutableList<Poetry>>>

    @POST("poem/poemInfo")
    fun poemInfo(@Query("author_id") author_id: String, @Query("author_name") author_name: String): Observable<BaseResponse<Poem>>


    @POST("poetrys/searchPoetry")
    fun searchPoetry(@Query("keyword") queryKey: String?, @Query("page") page: Int): Observable<BaseResponse<MutableList<Poetry>>>

}