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

    private val dao by lazy { DataBaseHelper.getInstance().poetryDao() }


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

    suspend fun searchPoetry(s: String, t: String, page: Int) = parseDb { dao.search(s, t, page) }

    suspend fun searchPoetryByAuthor(s: String, t: String, page: Int) = parseDb { dao.searchByAuthor(s, t, page) }

    suspend fun getLikePoetryById(id: Int) = parseDb { dao.getLikePoetryById(id) }

    suspend fun updatePoetry(m: Poetry) = parseDb { dao.updatePoetry(m) }

    suspend fun getLikePoetry(page: Int) = parseDb { dao.getLikePoetry(page) }

    suspend fun get10PoetryRandom() = parseDb { dao.get10PoetryRandom() }

    suspend fun getPoemInfo(page: Int) = parseDb { dao.getPoemInfo(page) }
}