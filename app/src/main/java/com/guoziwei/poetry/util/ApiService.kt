package com.guoziwei.poetry.util

import com.guoziwei.poetry.model.BaseResponse
import com.guoziwei.poetry.model.PageResponse
import com.guoziwei.poetry.model.Poetry
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Administrator on 2018/4/26 0026.
 */
interface ApiService {
    @POST("randomTenPoetry")
    fun randomTenPoetry(): Observable<BaseResponse<MutableList<Poetry>>>


    @FormUrlEncoded
    @POST("poetPoetrys")
    fun poetPoetrys(@Field("queryKey") queryKey: String?, @Field("page") page: Int): Observable<BaseResponse<PageResponse<MutableList<Poetry>>>>

    @POST("randomPoetry")
    fun randomPoetry(): Observable<BaseResponse<Poetry>>
}