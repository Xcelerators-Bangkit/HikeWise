package com.example.hikewise.data.sewaalat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//@Database(entities = [BookingEntity::class], version = 1, exportSchema = false)
//abstract class AlatDatabase : RoomDatabase(){
//
//    abstract fun bookingDao(): AlatDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AlatDatabase? = null
//
//        fun getInstance(context: Context): AlatDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AlatDatabase::class.java,
//                    "alat_database.db"
//                ).build()
//
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//
//}


@Database(entities = [BookingEntity::class], version = 2, exportSchema = false)
abstract class AlatDatabase : RoomDatabase() {

    abstract fun bookingDao(): AlatDao

    companion object {
        private const val DATABASE_NAME = "alat_database.db"

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Tambahkan logika migrasi sensual dengan perubahan skema
                // Contoh: database.execSQL("ALTER TABLE nama_tabel ADD COLUMN nama_kolom INTEGER")
                database.execSQL("CREATE TABLE IF NOT EXISTS `booking_new` " +
                        "(`id` INTEGER PRIMARY KEY NOT NULL, `name` TEXT NOT NULL, " +
                        "`alat` TEXT NOT NULL, `duration` INTEGER NOT NULL, " +
                        "`price` INTEGER NOT NULL, `image` INTEGER NOT NULL)")
                database.execSQL("INSERT INTO booking_new (id, name, alat, duration, price, image) " +
                        "SELECT id, name, alat, duration, price, 0 AS image FROM booking")
                database.execSQL("DROP TABLE booking")
                database.execSQL("ALTER TABLE booking_new RENAME TO booking")
            }
        }

        @Volatile
        private var INSTANCE: AlatDatabase? = null

        fun getInstance(context: Context): AlatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlatDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

