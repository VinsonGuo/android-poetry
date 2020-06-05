package com.tech502.poetry.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tech502.poetry.model.Poetry

@Database(entities = [Poetry::class], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun poetryDao(): PoetryDao

    companion object {
        private lateinit var INSTANCE: DataBase

        fun init(context: Context) {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DataBase::class.java, "appDB")
                            .build()
                }
            }
        }

        fun getInstance() = INSTANCE
    }
}