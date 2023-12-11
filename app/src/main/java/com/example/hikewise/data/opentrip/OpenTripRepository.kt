package com.example.hikewise.data.opentrip

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData

class OpenTripRepository(private val openTripDao: OpenTripDao) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDER = true

        @Volatile
        private var instance: OpenTripRepository? = null

        fun getInstance(context: Context) : OpenTripRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = OpenTripDatabase.getInstance(context)
                    instance = OpenTripRepository(database.openTripDao())
                }
                return instance as OpenTripRepository
            }
        }
    }

    fun getAllOpenTrips(): LiveData<PagingData<OpenTripEntity>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACEHOLDER
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { openTripDao.getAllOpenTrips()}
        ).liveData
    }

    suspend fun insertOpenTrip(openTrip: OpenTripEntity): Long {
        return openTripDao.insertOpenTrip(openTrip)
    }

    fun getOpenTripById(id: Int): LiveData<OpenTripEntity> {
        return openTripDao.getOpenTripById(id)
    }

    suspend fun deleteOpenTrip(openTrip: OpenTripEntity) {
        openTripDao.deleteOpenTrip(openTrip)
    }
}