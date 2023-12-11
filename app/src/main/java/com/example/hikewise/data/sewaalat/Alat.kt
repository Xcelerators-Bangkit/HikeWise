package com.example.hikewise.data.sewaalat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    val name: String,


    val alat: String,


    val duration: Int,


    val price: Int,


    val image : Int

)
