package com.example.hikewise.ui.bookingalat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.sewaalat.AlatRepository
import com.example.hikewise.ui.bookingalat.booking.BookingAlatViewModel
import com.example.hikewise.ui.bookingalat.listbooking.HistoryBookingAlatViewModel

class BookingViewModelFactory(private val alatRepository: AlatRepository) : ViewModelProvider.Factory {

    companion object {

        @Volatile
        private var instance: BookingViewModelFactory? = null

        fun getInstance(context: Context) : BookingViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BookingViewModelFactory(
                    AlatRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(BookingAlatViewModel::class.java) ->{
                BookingAlatViewModel(alatRepository) as T
            }
            modelClass.isAssignableFrom(HistoryBookingAlatViewModel::class.java) ->{
                HistoryBookingAlatViewModel(alatRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}