package com.example.workouttracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Train::class], version = 1, exportSchema = false)
abstract class TrainDatabase : RoomDatabase() {

    abstract val trainDatabaseDao: TrainDao

    companion object {
        @Volatile
        private var INSTANCE: TrainDatabase? = null
        fun getInstance(context: Context): TrainDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TrainDatabase::class.java,
                        "train_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}