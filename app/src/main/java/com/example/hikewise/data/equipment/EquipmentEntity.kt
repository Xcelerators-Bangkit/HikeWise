package com.example.hikewise.data.equipment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipment")
data class EquipmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val equipment: String
)
