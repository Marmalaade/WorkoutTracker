package com.example.workouttracker.workouttracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.R
import com.example.workouttracker.convertDurationToFormatted
import com.example.workouttracker.database.Train
import com.example.workouttracker.databinding.ListItemTrainingsTimeBinding

private var trainingTimeTextColor = Color.WHITE
private var trainingTimeText = "I'm training..."

class WorkoutTrackerAdapter(private val context: Context) : ListAdapter<Train, WorkoutTrackerAdapter.ViewHolder>(TrainingDiffCallback()) {
    init {
        trainingTimeTextColor = ContextCompat.getColor(context, R.color.gold)
        trainingTimeText = context.resources.getString(R.string.just_start)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(private val binding: ListItemTrainingsTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Train) {
            if (item.startTime == item.endTime) {
                binding.trainingLenght.text = trainingTimeText
                binding.trainingLenght.setTextColor(trainingTimeTextColor)
            } else binding.trainingLenght.text = convertDurationToFormatted(item.startTime, item.endTime, itemView.context.resources)
            binding.training = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTrainingsTimeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class TrainingDiffCallback : DiffUtil.ItemCallback<Train>() {
    override fun areItemsTheSame(oldItem: Train, newItem: Train): Boolean {
        return oldItem.trainingId == newItem.trainingId
    }

    override fun areContentsTheSame(oldItem: Train, newItem: Train): Boolean {
        return oldItem == newItem
    }

}