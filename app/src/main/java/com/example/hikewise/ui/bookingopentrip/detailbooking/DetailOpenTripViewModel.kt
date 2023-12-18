package com.example.hikewise.ui.bookingopentrip.detailbooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.hikewise.data.opentrip.OpenTripEntity
import com.example.hikewise.data.opentrip.OpenTripRepository
import kotlinx.coroutines.launch

class DetailOpenTripViewModel(private val openTripRepository: OpenTripRepository): ViewModel() {

    private val _openTripId = MutableLiveData<Int>()

    private val _openTrip = _openTripId.switchMap { id ->
        openTripRepository.getOpenTripById(id)
    }

    val openTrip: LiveData<OpenTripEntity> = _openTrip

    fun setOpenTripId(openTripId: Int) {
        if (openTripId == _openTripId.value) {
            return
        }
        _openTripId.value = openTripId
    }

    fun deleteOpenTripById(id: Long) {
        viewModelScope.launch {
             openTripRepository.deleteOpenTripById(id)
        }
    }
}