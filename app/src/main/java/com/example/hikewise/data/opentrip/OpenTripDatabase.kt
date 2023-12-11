package com.example.hikewise.data.opentrip

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OpenTripEntity::class], version = 1, exportSchema = false)
abstract class OpenTripDatabase : RoomDatabase() {
    abstract fun openTripDao(): OpenTripDao

    companion object {
        private const val DATABASE_NAME = "open_trip_database.db"

        @Volatile
        private var INSTANCE: OpenTripDatabase? = null

        fun getInstance(context: Context): OpenTripDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OpenTripDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
