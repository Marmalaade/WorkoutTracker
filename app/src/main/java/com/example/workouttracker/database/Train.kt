package com.example.workouttracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "training_table")
data class Train(
    @PrimaryKey(autoGenerate = true)
    var trainingId: Long = 0L,
    @ColumnInfo(name = "start_time")
    val startTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time")
    var endTime: Long = startTime,
    @ColumnInfo(name = "quality")
    var trainingQuality: Int = -1
)