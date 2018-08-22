package com.example.lengary_l.wanandroidtodo.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData
import com.example.lengary_l.wanandroidtodo.room.dao.LoginDetailDataDao

/**
 * Created by CoderLengary
 */
@Database(
        entities = [LoginDetailData::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun LoginDetailDataDao(): LoginDetailDataDao

    companion object {
        const val DATABASE_NAME = "wan-android-db"

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext, AppDatabase::class.java, DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}