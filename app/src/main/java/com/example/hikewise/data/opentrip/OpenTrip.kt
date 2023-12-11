package com.example.hikewise.data.opentrip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "opentrip")
data class OpenTripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val tripName: String,

    val mountainName: String,

    val price : Int
)
