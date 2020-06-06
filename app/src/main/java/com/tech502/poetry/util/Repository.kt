package com.tech502.poetry.util

import com.tech502.poetry.database.DataBaseHelper
import com.tech502.poetry.model.BaseResponse
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    companion object {
        val instance = Repository()
    }

    private val http by lazy { HttpUtil.create() }
    private val dao by lazy { DataBase.getInstance().poetryDao() }
    private val dao2 by lazy { DataBaseHelper.getInstance().poetryDao2() }


    private suspend fun <T> parseResponse(block: suspend () -> BaseResponse<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = block()
                if (response.isSuccess()) {
                    Resource(true, response.msg, response.data)
                } else {
                    Resource(false, response.msg, null)
                }
            } catch (e: Throwable) {
                // todo 修改string
                Resource(false, "network error", null, throwable = e)
            }
        }
    }

    private suspend fun <T> parseDb(block: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = block()
                Resource(true, "success", result)
            } catch (e: Throwable) {
                // todo 修改string
                Resource(false, "db error", null, throwable = e)
            }
        }
    }

    suspend fun randomTenPoetry() = parseResponse { http.randomTenPoetry() }

    suspend fun poemInfo(author_id: String, author_name: String) = parseResponse { http.poemInfo(author_id, author_name) }

    suspend fun searchPoetry(keyword: String, page: Int) = parseResponse { http.searchPoetry(keyword, page) }

    suspend fun getLocalPoetryByPage(page: Int) = parseDb { dao.getByPage(page) }

    suspend fun getLocalPoetryByPoetryId(poetry_id: String) = parseDb { dao.getByPoetryId(poetry_id) }

    suspend fun insertLocalPoetry(m: Poetry) = parseDb { dao.insert(m) }

    suspend fun deleteLocalPoetry(m: Poetry) = parseDb { dao.delete(m) }

    suspend fun getLocalPoetryByPage2(page: Int) = parseDb { dao2.getByPage2(page) }

    suspend fun get10PoetryRandom() = parseDb { dao2.get10PoetryRandom() }
}