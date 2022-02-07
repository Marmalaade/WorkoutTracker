package com.example.workouttracker.workouttracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.workouttracker.R
import com.example.workouttracker.databinding.FragmentWorkoutTrackerBinding


class WorkoutTrackerFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWorkoutTrackerBinding.inflate(inflater, container, false)
        binding.nextbutton.setOnClickListener {
            requireView().findNavController()
                .navigate(WorkoutTrackerFragmentDirections.actionWorkoutTrackerFragmentToQualityControlFragment())
        }

        return binding.root
    }


}
