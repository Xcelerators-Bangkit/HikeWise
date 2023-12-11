package com.example.hikewise.data.sewaalat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlatCamping(
    val id : Int,
    val name : String,
    val image : Int,
    val price : Int,
) : Parcelable
