package com.example.hikewise.ui.bookingalat.listbooking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.hikewise.data.sewaalat.AlatRepository
import com.example.hikewise.data.sewaalat.BookingEntity

class HistoryBookingAlatViewModel(private val alatRepository: AlatRepository) : ViewModel() {

    fun getAllBookings(): LiveData<PagingData<BookingEntity>> {
        Log.d("TAG", "getAllBookings: " + alatRepository.getAllBookings())
        return alatRepository.getAllBookings()
    }

}