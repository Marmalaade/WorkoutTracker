package com.example.workouttracker.qualitycontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workouttracker.database.TrainDao
import kotlinx.coroutines.*

class QualityControlViewModel(private val trainKey: Long = 0L, val database: TrainDao) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToWorkoutTracker = MutableLiveData<Boolean?>()

    val navigateToWorkoutTracker: LiveData<Boolean?>
        get() = _navigateToWorkoutTracker

    fun navigationDone() {
        _navigateToWorkoutTracker.value = null
    }

    fun onSetWorkoutQuality(quality: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val presentTrain = database.get(trainKey) ?: return@withContext
                presentTrain.trainingQuality = quality
                database.update(presentTrain)
            }
            _navigateToWorkoutTracker.value = true
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}