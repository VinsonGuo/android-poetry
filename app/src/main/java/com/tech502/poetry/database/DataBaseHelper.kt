package com.tech502.poetry.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.huma.room_for_asset.RoomAsset
import com.tech502.poetry.model.Poetry

@Database(entities = [Poetry::class], version = 2)
abstract class DataBaseHelper : RoomDatabase() {
    abstract fun poetryDao(): PoetryDao2

    companion object {
        private lateinit var INSTANCE: DataBaseHelper

        fun init(context: Context) {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(DataBaseHelper::class) {
                    INSTANCE = RoomAsset.databaseBuilder(context.applicationContext, DataBaseHelper::class.java, "poetry.sqlite")
                            .build()
                }
            }
        }

        fun getInstance() = INSTANCE
    }
}