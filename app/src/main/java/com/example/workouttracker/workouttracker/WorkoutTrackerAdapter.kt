package com.example.workouttracker.workouttracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.R
import com.example.workouttracker.TextItemViewHolder
import com.example.workouttracker.database.Train

class WorkoutTrackerAdapter : RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Train>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false)
        return TextItemViewHolder(view as TextView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.trainingId.toString()
    }
}