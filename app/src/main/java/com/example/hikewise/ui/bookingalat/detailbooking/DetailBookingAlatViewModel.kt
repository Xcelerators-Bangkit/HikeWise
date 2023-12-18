package com.example.hikewise.ui.bookingalat.detailbooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.hikewise.data.sewaalat.AlatRepository
import com.example.hikewise.data.sewaalat.BookingEntity
import kotlinx.coroutines.launch

class DetailBookingAlatViewModel(private val alatRepository: AlatRepository): ViewModel() {

    private val _alatId = MutableLiveData<Int>()

    private val _alat = _alatId.switchMap { id ->
        alatRepository.getBookingById(id)
    }

    val alat: LiveData<BookingEntity> = _alat

    fun setAlatId(alatId: Int) {
        if (alatId == _alatId.value) {
            return
        }
        _alatId.value = alatId
    }

    fun deleteBookingById(id: Long) {
        viewModelScope.launch {
            alatRepository.deleteBookingById(id)
        }
    }
}