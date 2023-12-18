package com.example.hikewise.data.sewaalat

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "booking")
@Parcelize
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,


    val name: String,


    val alat: String,


    val duration: Int,


    val price: Int,


    val image : Int

): Parcelable
