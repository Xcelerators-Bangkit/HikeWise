package com.example.hikewise.data.equipment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EquipmentEntity::class], version = 1, exportSchema = false)
abstract class EquipmentDatabase : RoomDatabase() {

    abstract fun equipmentDao(): EquipmentDao

    companion object {

        private const val DATABASE_NAME = "equipment_database.db"

        @Volatile
        private var INSTANCE: EquipmentDatabase? = null

        fun getInstance(context: Context): EquipmentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EquipmentDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}