package com.tech502.poetry.model

import androidx.room.*

@Dao
interface PoetryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(m: Poetry)

    @Delete
    fun delete(m: Poetry)

    @Query("SELECT * FROM Poetry WHERE poetry_id = :poetry_id")
    fun getByPoetryId(poetry_id: String): List<Poetry>

    @Query("SELECT * FROM Poetry ORDER BY update_time DESC LIMIT :page * 10, 10")
    fun getByPage(page: Int): List<Poetry>
}
