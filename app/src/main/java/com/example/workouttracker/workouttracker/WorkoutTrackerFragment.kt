package com.example.workouttracker.workouttracker

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.workouttracker.R
import com.example.workouttracker.database.TrainDatabase
import com.example.workouttracker.databinding.FragmentWorkoutTrackerBinding
import com.example.workouttracker.mediaplayer.BackgroundMusicPlayer
import com.google.android.material.snackbar.Snackbar


class WorkoutTrackerFragment : Fragment() {

    private lateinit var transition: TransitionDrawable
    private lateinit var binding: FragmentWorkoutTrackerBinding

    @SuppressLint("ResourceAsColor", "ResourceType")
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
        val adapter = WorkoutTrackerAdapter(requireContext(), TrainingItemsListener { trainingId ->
            Toast.makeText(context, "$trainingId", Toast.LENGTH_SHORT).show()
        })
        binding.trainingsList.adapter = adapter
        backgroundTransition()

        workoutTrackerViewModel.trains.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }

        })
        workoutTrackerViewModel.navigateToQualityControl.observe(viewLifecycleOwner, Observer { train ->
            train?.let {
                view?.findNavController()
                    ?.navigate(WorkoutTrackerFragmentDirections.actionWorkoutTrackerFragmentToQualityControlFragment(train.trainingId))
                workoutTrackerViewModel.navigationDone()
            }
        })

        workoutTrackerViewModel.snackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                showSnackBar()
                workoutTrackerViewModel.doneSnackBar()
            }
        })

        return binding.root
    }

    @SuppressLint("ShowToast")
    private fun showSnackBar() {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), R.string.cleared, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.dark_lime))
            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            .show()
    }

    private fun backgroundTransition() {
        transition = binding.trainingsList.background as TransitionDrawable
        transition.startTransition(4000)
    }
}
