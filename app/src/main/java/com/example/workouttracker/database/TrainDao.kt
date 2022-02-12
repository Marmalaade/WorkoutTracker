package com.example.workouttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TrainDao {
    @Insert
    fun insert(train: Train)

    @Update
    fun update(train: Train)

    @Query("SELECT * FROM training_table WHERE trainingId = :key")
    fun get(key: Long): Train?

    @Query("SELECT * FROM training_table ORDER BY trainingId DESC")
    fun getAllTrain(): LiveData<List<Train>>

    @Query("SELECT * FROM TRAINING_TABLE ORDER BY trainingId DESC LIMIT 1")
    fun getTodaysTrains(): Train?

    @Query("DELETE FROM training_table")
    fun clear()
}