package com.tech502.poetry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tech502.poetry.model.Poetry

@Database(entities = [Poetry::class], version = 1)
abstract class DataBaseHelper : RoomDatabase() {
    abstract fun poetryDao(): PoetryDao

    companion object {
        private lateinit var INSTANCE: DataBaseHelper

        fun init(context: Context) {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(DataBaseHelper::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DataBaseHelper::class.java, "poetry.sqlite")
                            .createFromAsset("databases/poetry.sqlite")
                            .build()
                }
            }
        }

        fun getInstance() = INSTANCE
    }
}