package com.example.hikewise.ui.bookingopentrip.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.data.opentrip.OpenTripEntity
import com.example.hikewise.data.opentrip.OpenTripRepository
import kotlinx.coroutines.launch

class BookingOpenTripViewModel(private val openTripRepository: OpenTripRepository) :ViewModel() {

    fun bookingOpenTrip(openTrip : OpenTripEntity) {
        viewModelScope.launch {
            openTripRepository.insertOpenTrip(openTrip)
        }
    }
}