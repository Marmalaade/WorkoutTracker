package com.example.workouttracker.workouttracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.workouttracker.database.Train
import com.example.workouttracker.database.TrainDao
import kotlinx.coroutines.*

class WorkoutTrackerViewModel(val database: TrainDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var presentTraining = MutableLiveData<Train?>()

    private var trains = database.getTodaysTrains()

    init {
        initializePresentTraining()
    }

    private fun initializePresentTraining() {
        uiScope.launch {
            presentTraining.value = getPresentTrainFromDB()
        }
    }

    private suspend fun getPresentTrainFromDB(): Train? {
        return withContext(Dispatchers.IO) {
            var train = database.getTodaysTrains()
            if (train?.endTime != train?.startTime) {
                train = null
            }
            train
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newTrain = Train()
            insert(newTrain)
            presentTraining.value = getPresentTrainFromDB()
        }
    }

    private suspend fun insert(newTrain: Train) {
        withContext(Dispatchers.IO) {
            database.insert(newTrain)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldTrain = presentTraining.value ?: return@launch
            oldTrain.endTime = System.currentTimeMillis()
            update(oldTrain)
        }
    }

    private suspend fun update(oldTrain: Train) {
        withContext(Dispatchers.IO) {
            database.update(oldTrain)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            presentTraining.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}