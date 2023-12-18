package com.example.hikewise.data.opentrip

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "opentrip")
@Parcelize
data class OpenTripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val tripName: String,

    val mountainName: String,

    val price : Int
): Parcelable
