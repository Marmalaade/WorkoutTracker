package com.example.workouttracker.workouttracker

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.workouttracker.R
import com.example.workouttracker.database.Train

@BindingAdapter("trainingImage")
fun ImageView.setQuaityImage(item: Train?) {
    item?.let {
        setImageResource(
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
}
