package com.example.workouttracker.workouttracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.workouttracker.database.TrainDao

class WorkoutTrackerViewModel(val database: TrainDao, application: Application) : AndroidViewModel(application) {

}