package com.example.taller2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taller2.dao.SiteDao
import com.example.taller2.entities.Site


@Database(entities = [Site::class], version = 1, exportSchema = false)
 abstract class SiteRoomDatabase : RoomDatabase() {
    abstract fun siteDao(): SiteDao
    companion object {

        @Volatile
        private var INSTANCE: SiteRoomDatabase? = null
        fun getDatabase(context: Context): SiteRoomDatabase{

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SiteRoomDatabase::class.java,
                    "site_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }}