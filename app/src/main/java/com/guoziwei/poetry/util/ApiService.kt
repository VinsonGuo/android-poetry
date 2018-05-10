package com.guoziwei.poetry.util

import com.guoziwei.poetry.model.BaseResponse
import com.guoziwei.poetry.model.Poem
import com.guoziwei.poetry.model.Poetry
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Administrator on 2018/4/26 0026.
 */
interface ApiService {

    @POST("randomTenPoetry")
    fun randomTenPoetry(): Observable<BaseResponse<MutableList<Poetry>>>

    @POST("poemInfo?name=李白")
    fun poemInfo(@Query("author_id") author_id: String): Observable<BaseResponse<Poem>>


    @POST("searchPoetry")
    fun searchPoetry(@Query("keyword") queryKey: String?, @Query("page") page: Int): Observable<BaseResponse<MutableList<Poetry>>>

    @POST("randomPoetry")
    fun randomPoetry(): Observable<BaseResponse<Poetry>>
}