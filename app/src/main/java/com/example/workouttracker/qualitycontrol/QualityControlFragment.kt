package com.example.workouttracker.qualitycontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workouttracker.databinding.FragmentQualityControlBinding


class QualityControlFragment : Fragment() {

    private lateinit var binding: FragmentQualityControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentQualityControlBinding.inflate(inflater, container, false)
        return binding.root
    }
}
