package com.example.hikewise.ui.bookingopentrip

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.opentrip.OpenTripRepository
import com.example.hikewise.ui.bookingopentrip.booking.BookingOpenTripViewModel

class BookingOpenTripViewModelFactory(private val openTripRepository: OpenTripRepository) : ViewModelProvider.Factory {

    companion object {

        @Volatile
        private var instance: BookingOpenTripViewModelFactory? = null

        fun getInstance(context: Context) : BookingOpenTripViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: BookingOpenTripViewModelFactory(
                    OpenTripRepository.getInstance(context)
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(BookingOpenTripViewModel::class.java) ->{
                return BookingOpenTripViewModel(openTripRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}