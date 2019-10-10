package com.tech502.poetry.util

import com.tech502.poetry.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Administrator on 2018/4/26 0026.
 */
object HttpUtil {
    private val instance by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create())
//                .baseUrl("http://devices.e-toys.cn/api/poet/")
                .baseUrl("http://502tech.com:8090/poetry/")
                .build()
        retrofit.create(ApiService::class.java)
    }

    fun create(): ApiService {
        return instance
    }
}