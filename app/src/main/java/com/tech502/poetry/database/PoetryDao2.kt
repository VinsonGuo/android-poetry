package com.tech502.poetry.database

import androidx.room.*
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.model.Poetry2

@Dao
interface PoetryDao2 {

    @Query("SELECT * FROM poetry LIMIT :page * 10, 10")
    fun getByPage2(page: Int): List<Poetry2>

    @Query("SELECT * FROM poetry WHERE id IN (SELECT id FROM poetry ORDER BY RANDOM() LIMIT 10)")
    fun get10PoetryRandom(): List<Poetry2>
}
