package com.example.workouttracker

import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.workouttracker.database.Train
import com.example.workouttracker.database.TrainDao
import com.example.workouttracker.database.TrainDatabase

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var trainDao: TrainDao
    private lateinit var db: TrainDatabase

    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, TrainDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        trainDao = db.trainDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllTrains() {
        val train = Train()
        trainDao.insert(train)
        val allTrain: LiveData<List<Train>> = trainDao.getAllTrain()
        assertEquals(allTrain.value?.get(1), null)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTodaysTrain() {
        val train = Train()
        trainDao.insert(train)
        val presentTrain = trainDao.getTodaysTrains()
        assertEquals(presentTrain?.trainingQuality, -1)
    }
}

