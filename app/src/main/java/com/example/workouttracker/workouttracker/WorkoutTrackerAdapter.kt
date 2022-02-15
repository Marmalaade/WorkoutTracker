package com.example.workouttracker.workouttracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.R
import com.example.workouttracker.convertDurationToFormatted
import com.example.workouttracker.database.Train

class WorkoutTrackerAdapter : RecyclerView.Adapter<WorkoutTrackerAdapter.ViewHolder>() {

    var data = listOf<Train>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_trainings_time, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        if (item.startTime == item.endTime) {
            "I'm training ...".also { holder.trainingTime.text = it }
        } else holder.trainingTime.text = convertDurationToFormatted(item.startTime, item.endTime, res)

        holder.qualityImage.setImageResource(
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainingTime: TextView = itemView.findViewById(R.id.training_lenght)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    }
}