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
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.R
import com.example.workouttracker.convertDurationToFormatted
import com.example.workouttracker.database.Train

class WorkoutTrackerAdapter(private val context: Context) : RecyclerView.Adapter<WorkoutTrackerAdapter.ViewHolder>() {

    private var trainingTimeTextColor = Color.WHITE
    private var trainingTimeText = "I'm training..."

    var data = listOf<Train>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        trainingTimeTextColor = ContextCompat.getColor(context, R.color.gold)
        trainingTimeText = context.resources.getString(R.string.just_start)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_trainings_time, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        if (item.startTime == item.endTime) {
            holder.trainingTime.text = trainingTimeText
            holder.trainingTime.setTextColor(trainingTimeTextColor)
        } else holder.trainingTime.text = convertDurationToFormatted(item.startTime, item.endTime, holder.itemView.context.resources)

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