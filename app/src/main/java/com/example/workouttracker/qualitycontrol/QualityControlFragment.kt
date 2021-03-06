package com.example.workouttracker.qualitycontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.workouttracker.R
import com.example.workouttracker.database.TrainDatabase
import com.example.workouttracker.databinding.FragmentQualityControlBinding


class QualityControlFragment : Fragment() {

    private lateinit var binding: FragmentQualityControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quality_control, container, false)
        val application = requireNotNull(this.activity).application
        val arguments = QualityControlFragmentArgs.fromBundle(requireArguments())
        val dataSource = TrainDatabase.getInstance(application).trainDatabaseDao
        val viewModelFactory = QualityControlViewModelFactory(arguments.trainKey, dataSource)
        val qualityControlViewModel = ViewModelProvider(this, viewModelFactory)[QualityControlViewModel::class.java]

        binding.qualityControlViewModel = qualityControlViewModel
        qualityControlViewModel.navigateToWorkoutTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                view?.findNavController()?.navigate(QualityControlFragmentDirections.actionQualityControlFragmentToWorkoutTrackerFragment())
                qualityControlViewModel.navigationDone()
            }
        })
        animateCardViews()
        return binding.root
    }

    private fun animateCardViews() {
        val array: List<CardView> = listOf(
            binding.firstCardview,
            binding.secondCardview,
            binding.thirdCardview,
            binding.fourthCardview,
            binding.fifthCardview,
            binding.sixthCardview
        )
        for (el in array) {
            el.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake))
        }
    }

}


