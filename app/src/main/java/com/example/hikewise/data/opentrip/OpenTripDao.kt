package com.example.hikewise.data.opentrip

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OpenTripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpenTrip(openTrip: OpenTripEntity): Long

    @Query("SELECT * FROM opentrip")
    fun getAllOpenTrips(): PagingSource<Int, OpenTripEntity>

    @Query("SELECT * FROM opentrip WHERE id = :id")
    fun getOpenTripById(id: Int): LiveData<OpenTripEntity>

    @Query("DELETE FROM opentrip WHERE id = :id")
    suspend fun deleteOpenTripID(id: Long)
}