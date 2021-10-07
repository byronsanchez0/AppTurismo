package com.example.taller2.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "site_table")

data class Site(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "direction" )
    val direction: String,
    @ColumnInfo(name = "experience")
    val experience: String
    )
