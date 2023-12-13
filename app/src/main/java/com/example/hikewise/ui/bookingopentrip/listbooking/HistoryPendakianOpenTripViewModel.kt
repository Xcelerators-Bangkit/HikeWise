package com.example.hikewise.ui.bookingopentrip.listbooking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.hikewise.data.opentrip.OpenTripEntity
import com.example.hikewise.data.opentrip.OpenTripRepository

class HistoryPendakianOpenTripViewModel(private val openTripRepository: OpenTripRepository): ViewModel() {

    fun getAllOpenTrips(): LiveData<PagingData<OpenTripEntity>> {
        Log.d("TAG", "getAllOpenTrips: " + openTripRepository.getAllOpenTrips())
        return openTripRepository.getAllOpenTrips()
    }
}