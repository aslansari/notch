package com.aslansari.notch.bucket.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class NotchDatabase: RoomDatabase() {

    abstract fun getItemDAO(): ItemDAO

    companion object {

        @Volatile
        private var INSTANCE: NotchDatabase? = null

        fun getDatabase(context: Context): NotchDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotchDatabase::class.java,
                    "notch.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}