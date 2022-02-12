package com.example.workouttracker.workouttracker

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.workouttracker.database.Train
import com.example.workouttracker.database.TrainDao
import com.example.workouttracker.formatTrains
import kotlinx.coroutines.*

class WorkoutTrackerViewModel(val database: TrainDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var presentTraining = MutableLiveData<Train?>()

    private var trains = database.getAllTrain()

    private val _navigateToQualityControl = MutableLiveData<Train?>()

    private val _snackBarEvent = MutableLiveData<Boolean>()

    val startButtonVisible = Transformations.map(presentTraining) {
        null == it
    }

    val stopButtonVisible = Transformations.map(presentTraining) {
        null != it
    }

    val clearButtonVisible = Transformations.map(trains) {
        it?.isNotEmpty()
    }

    val snackBarEvent: LiveData<Boolean>
        get() = _snackBarEvent

    val navigateToQualityControl: LiveData<Train?>
        get() = _navigateToQualityControl

    val trainsString = Transformations.map(trains) { trains ->
        formatTrains(trains, application.resources)
    }

    init {
        initializePresentTraining()
    }

    fun navigationDone() {
        _navigateToQualityControl.value = null
    }

    fun doneSnackBar() {
        _snackBarEvent.value = false
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
            _navigateToQualityControl.value = oldTrain
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
            _snackBarEvent.value = true
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