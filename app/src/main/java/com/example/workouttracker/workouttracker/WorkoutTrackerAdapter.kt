package com.example.workouttracker.workouttracker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.R
import com.example.workouttracker.convertDurationToFormatted
import com.example.workouttracker.database.Train

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


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val trainingTime: TextView = itemView.findViewById(R.id.training_lenght)
        private val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
        fun bind(item: Train) {
            if (item.startTime == item.endTime) {
                trainingTime.text = trainingTimeText
                trainingTime.setTextColor(trainingTimeTextColor)
            } else trainingTime.text = convertDurationToFormatted(item.startTime, item.endTime, itemView.context.resources)

            qualityImage.setImageResource(
                when (item.trainingQuality) {
                    1 -> R.drawable.ic_mood_1
                    2 -> R.drawable.ic_mood_2
                    3 -> R.drawable.ic_mood_3
                    4 -> R.drawable.ic_mood_4
                    5 -> R.drawable.ic_mood_5
                    6 -> R.drawable.ic_mood_6
                    else -> R.drawable.training_cat
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_trainings_time, parent, false)
                return ViewHolder(view)
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