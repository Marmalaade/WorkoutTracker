package com.example.workouttracker.qualitycontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.workouttracker.R
import com.example.workouttracker.databinding.FragmentQualityControlBinding


class QualityControlFragment : Fragment() {

    private lateinit var binding: FragmentQualityControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quality_control, container, false)
        return binding.root
    }
}
