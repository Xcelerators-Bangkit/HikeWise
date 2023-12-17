package com.example.hikewise.data.equipment

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipment(equipment: EquipmentEntity): Long

    @Query("SELECT * FROM equipment")
    fun getAllEquipment(): PagingSource<Int, EquipmentEntity>

    @Query("DELETE FROM equipment")
    suspend fun deleteAllEquipment()

}