package com.example.hikewise.data.sewaalat

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.flow.Flow

class AlatRepository(private val alatDao: AlatDao) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDER = true

        @Volatile
        private var instance: AlatRepository? = null

        fun getInstance(context: Context) : AlatRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = AlatDatabase.getInstance(context)
                    instance = AlatRepository(database.bookingDao())
                }
                return instance as AlatRepository
            }
        }

    }

    fun getAllBookings(): LiveData<PagingData<BookingEntity>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACEHOLDER
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { alatDao.getAllBookings()}

        ).liveData

    }

    suspend fun insertBooking(booking: BookingEntity): Long {
        return alatDao.insertBooking(booking)
    }

    fun getBookingById(id: Int): LiveData<BookingEntity> {
        return alatDao.getBookingById(id)
    }

    suspend fun deleteBookingById(id: Long) {
        alatDao.deleteBookingID(id)
    }


}