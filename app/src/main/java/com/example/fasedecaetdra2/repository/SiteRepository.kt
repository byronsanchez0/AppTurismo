package com.example.taller2.repository

import android.content.Context

import com.example.taller2.dao.SiteDao

import com.example.taller2.database.SiteRoomDatabase

import com.example.taller2.entities.Site
import kotlinx.coroutines.flow.Flow

class SiteRepository(private val siteDao: SiteDao)  {
    companion object {
        private var INSTANCE : SiteRepository? = null
        fun getRepository(context: Context) : SiteRepository {
            return INSTANCE ?: synchronized(this) {
                val database = SiteRoomDatabase.getDatabase(context)
                val instance = SiteRepository(database.siteDao())
                INSTANCE = instance
                instance
            }
        }
    }

    val allSite: Flow<List<Site>> = siteDao.getAlphabetizedSites()

    suspend fun insert(site: Site) {
        siteDao.insert(site)
    }

    suspend fun deleteOneItem(Id:Int) {
        siteDao.deleteOneItem(Id)
    }

    suspend fun updateItem(site: Site){
        siteDao.updateItem(site)
    }



   // suspend fun wachtDestails(Id:Int) {
   //     siteDao.wachtDestails(Id)
   // }


}