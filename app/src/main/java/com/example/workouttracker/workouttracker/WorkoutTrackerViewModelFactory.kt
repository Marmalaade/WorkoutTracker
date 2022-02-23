package com.example.workouttracker.workouttracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workouttracker.database.TrainDao

class WorkoutTrackerViewModelFactory(private val dataSource: TrainDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutTrackerViewModel::class.java)) {
            return WorkoutTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}