package com.tech502.poetry.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Poetry::class], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun poetryDao(): PoetryDao

    companion object {
        private lateinit var INSTANCE: DataBase

        fun getAppDataBase(context: Context): DataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DataBase::class.java, "appDB")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}