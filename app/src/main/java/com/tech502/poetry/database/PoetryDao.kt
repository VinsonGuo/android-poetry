package com.tech502.poetry.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.tech502.poetry.model.PoemInfo
import com.tech502.poetry.model.Poetry

@Dao
interface PoetryDao {

    @Query("SELECT * FROM poetry WHERE is_like = 1 ORDER BY like_time DESC LIMIT :page * 10, 10")
    fun getLikePoetry(page: Int): List<Poetry>

    @Query("SELECT * FROM poetry WHERE is_like = 1 AND id = :id")
    fun getLikePoetryById(id: Int): List<Poetry>

    @Query("SELECT * FROM poetry WHERE id IN (SELECT id FROM poetry ORDER BY RANDOM() LIMIT 10)")
    fun get10PoetryRandom(): List<Poetry>

    @Query("SELECT author AS name, count(id) AS count FROM poetry GROUP BY author ORDER BY count DESC LIMIT :page * 30, 30")
    fun getPoemInfo(page: Int): List<PoemInfo>

    @Update
    fun updatePoetry(poetry: Poetry)

    @Query("""SELECT * from poetry
        WHERE poetry.author LIKE '%'||:s||'%' OR poetry.author LIKE '%'||:t||'%' 
        OR poetry.title LIKE '%'||:s||'%' OR poetry.title LIKE '%'||:t||'%'
        OR poetry.contents LIKE '%'||:s||'%' OR poetry.contents LIKE '%'||:t||'%'
        ORDER BY (CASE WHEN INSTR(poetry.author,:s)>0 THEN 0 ELSE 1 END),
            (CASE WHEN INSTR(poetry.author,:t)>0 THEN 0 ELSE 1 END),
            (CASE WHEN INSTR(poetry.title,:s)>0 THEN 0 ELSE 1 END),
            (CASE WHEN INSTR(poetry.title,:t)>0 THEN 0 ELSE 1 END),
            (CASE WHEN INSTR(poetry.contents,:s)>0 THEN 0 ELSE 1 END),
            (CASE WHEN INSTR(poetry.contents,:t)>0 THEN 0 ELSE 1 END)
        LIMIT :page * 10, 10
    """)
    fun search(s: String, t: String, page: Int): List<Poetry>

    @Query("SELECT * from poetry WHERE author = :s or author = :t LIMIT :page * 10, 10")
    fun searchByAuthor(s: String, t: String, page: Int): List<Poetry>
}
