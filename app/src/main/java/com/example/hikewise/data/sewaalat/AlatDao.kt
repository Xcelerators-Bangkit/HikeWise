package com.example.hikewise.data.sewaalat

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("SELECT * FROM booking")
    fun getAllBookings(): PagingSource<Int, BookingEntity>

    @Query("SELECT * FROM booking WHERE id = :id")
    fun getBookingById(id: Int): LiveData<BookingEntity>

    @Delete
    suspend fun deleteBooking(booking: BookingEntity)
}