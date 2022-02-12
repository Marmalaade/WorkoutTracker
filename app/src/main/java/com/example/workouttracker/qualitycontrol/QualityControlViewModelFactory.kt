package com.example.workouttracker.qualitycontrol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workouttracker.database.TrainDao

class QualityControlViewModelFactory(private val trainKey: Long, private val dataSource: TrainDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QualityControlViewModel::class.java)) {
            return QualityControlViewModel(trainKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}