package com.example.workouttracker.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.workouttracker.R
import com.example.workouttracker.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            view?.findNavController()?.navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToWorkoutTrackerFragment())
        }, 2000)

        return binding.root
    }

}