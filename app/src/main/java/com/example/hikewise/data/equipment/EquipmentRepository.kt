package com.example.hikewise.data.equipment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData

class EquipmentRepository(private val equipmentDao: EquipmentDao) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDER = true

        @Volatile
        private var instance: EquipmentRepository? = null

        fun getInstance(context: Context) : EquipmentRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = EquipmentDatabase.getInstance(context)
                    instance = EquipmentRepository(database.equipmentDao())
                }
                return instance as EquipmentRepository
            }

        }
    }

    fun getAllEquipment(): LiveData<PagingData<EquipmentEntity>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACEHOLDER
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { equipmentDao.getAllEquipment()}
        ).liveData

    }

    suspend fun insertEquipment(equipment: EquipmentEntity): Long {
        return equipmentDao.insertEquipment(equipment)
    }

    suspend fun deleteAllEquipment() {
        equipmentDao.deleteAllEquipment()
    }
}