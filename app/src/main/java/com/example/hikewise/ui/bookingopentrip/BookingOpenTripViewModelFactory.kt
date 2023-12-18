package com.example.hikewise.ui.bookingopentrip

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.opentrip.OpenTripRepository
import com.example.hikewise.ui.bookingopentrip.booking.BookingOpenTripViewModel
import com.example.hikewise.ui.bookingopentrip.detailbooking.DetailOpenTripViewModel
import com.example.hikewise.ui.bookingopentrip.listbooking.HistoryPendakianOpenTripViewModel

class BookingOpenTripViewModelFactory(private val openTripRepository: OpenTripRepository) : ViewModelProvider.Factory {

    companion object {

        @Volatile
        private var instance: BookingOpenTripViewModelFactory? = null

        fun getInstance(context: Context) : BookingOpenTripViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BookingOpenTripViewModelFactory(
                    OpenTripRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(BookingOpenTripViewModel::class.java) ->{
                BookingOpenTripViewModel(openTripRepository) as T
            }
            modelClass.isAssignableFrom(HistoryPendakianOpenTripViewModel::class.java) ->{
                HistoryPendakianOpenTripViewModel(openTripRepository) as T
            }
            modelClass.isAssignableFrom(DetailOpenTripViewModel::class.java) ->{
                DetailOpenTripViewModel(openTripRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}