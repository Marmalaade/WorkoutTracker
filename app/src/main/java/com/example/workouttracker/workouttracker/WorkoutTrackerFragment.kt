package com.example.workouttracker.workouttracker

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.workouttracker.R
import com.example.workouttracker.convertLongToDateString
import com.example.workouttracker.database.TrainDatabase
import com.example.workouttracker.databinding.CustomDialogBinding
import com.example.workouttracker.databinding.FragmentWorkoutTrackerBinding
import com.example.workouttracker.mediaplayer.BackgroundMusicPlayer
import com.google.android.material.snackbar.Snackbar


class WorkoutTrackerFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutTrackerBinding
    private lateinit var dialogBinding: CustomDialogBinding

    private var transitionTime: Int = 4000

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
        val adapter = WorkoutTrackerAdapter(requireContext(), TrainingItemsListener { time ->
            showInfoDialog(time)
        })
        workoutTrackerViewModel.isPlaying.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.musicIcon.apply {
                    setImageResource(R.drawable.anim_volume_off_to_on)
                    (drawable as AnimatedVectorDrawable).start()
                    BackgroundMusicPlayer.resumePlayer()
                }
            } else binding.musicIcon.apply {
                setImageResource(R.drawable.anim_volume_on_to_off)
                (drawable as AnimatedVectorDrawable).start()
                BackgroundMusicPlayer.pausePlayer()
            }

        })
        binding.trainingsList.adapter = adapter
        backgroundTransition(binding.trainingsList.background as TransitionDrawable)

        workoutTrackerViewModel.trains.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
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

    private fun showSnackBar() {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), R.string.cleared, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.dark_lime))
            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            .show()
    }

    private fun backgroundTransition(transition: TransitionDrawable) {
        transition.startTransition(transitionTime)
    }

    private fun showInfoDialog(item: Long) {
        val dialog = context?.let { Dialog(it) }
        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_dialog, null, false)
        backgroundTransition(dialogBinding.dialogLayout.background as TransitionDrawable)
        "${getString(R.string.time_format)} ${convertLongToDateString(item)}".also { dialogBinding.dialogInfoText.text = it }
        dialog?.setContentView(dialogBinding.root)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setWindowAnimations(R.style.DialogAnimations)
        dialog?.show()
    }
}
