package com.example.workouttracker.workouttracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.workouttracker.R
import com.example.workouttracker.database.TrainDatabase
import com.example.workouttracker.databinding.FragmentWorkoutTrackerBinding


class WorkoutTrackerFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout_tracker, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = TrainDatabase.getInstance(application).trainDatabaseDao
        val viewModelFactory = WorkoutTrackerViewModelFactory(dataSource, application)
        val workoutTrackerViewModel = ViewModelProvider(this, viewModelFactory)[WorkoutTrackerViewModel::class.java]
        binding.workoutTrackerViewModel = workoutTrackerViewModel
        binding.lifecycleOwner = this

        workoutTrackerViewModel.navigateToQualityControl.observe(viewLifecycleOwner, Observer { train ->
            train?.let {
                view?.findNavController()
                    ?.navigate(WorkoutTrackerFragmentDirections.actionWorkoutTrackerFragmentToQualityControlFragment(train.trainingId))
                workoutTrackerViewModel.navigationDone()
            }
        })
        return binding.root
    }
}
