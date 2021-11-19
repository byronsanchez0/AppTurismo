package com.example.taller2.dao

import androidx.room.*
import com.example.taller2.entities.Site
import kotlinx.coroutines.flow.Flow

@Dao
interface SiteDao {
    @Query("SELECT * FROM site_table ORDER BY name ASC")
    fun getAlphabetizedSites(): Flow<List<Site>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(site: Site)
    @Query("DELETE FROM  site_table WHERE id=:id")
    suspend fun deleteOneItem(id:Int)

    @Update
    suspend fun updateItem(site: Site)


    //@Query("SELECT * FROM site_table WHERE id=:id ")
    //suspend fun wachtDestails(id:Int)




}