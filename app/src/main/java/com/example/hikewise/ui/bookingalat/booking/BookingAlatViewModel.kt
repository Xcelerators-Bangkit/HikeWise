package com.example.hikewise.ui.bookingalat.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.data.sewaalat.AlatRepository
import com.example.hikewise.data.sewaalat.BookingEntity
import kotlinx.coroutines.launch

class BookingAlatViewModel(private val alatRepository: AlatRepository) : ViewModel() {

    fun booking(bookingEntity: BookingEntity) {
        viewModelScope.launch {
            alatRepository.insertBooking(bookingEntity)
        }
    }


}