package com.example.workouttracker.workouttracker

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.R
import com.example.workouttracker.convertDurationToFormatted
import com.example.workouttracker.convertLongToDateString
import com.example.workouttracker.database.Train
import com.example.workouttracker.databinding.ListItemTrainingsTimeBinding
import com.example.workouttracker.formatTrains
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

private var trainingTimeTextColor = Color.WHITE
private var trainingTimeText = "I'm training..."

class WorkoutTrackerAdapter(private val context: Context, private val clickListener: TrainingItemsListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(TrainingDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Train>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.TrainingItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    init {
        trainingTimeTextColor = ContextCompat.getColor(context, R.color.gold)
        trainingTimeText = context.resources.getString(R.string.just_start)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.TrainingItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val trainItem = getItem(position) as DataItem.TrainingItem
                holder.bind(trainItem.train, clickListener)
            }

        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(private val binding: ListItemTrainingsTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Train, clickListener: TrainingItemsListener) {
            binding.clickListener = clickListener
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

class TrainingDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

class TrainingItemsListener(val clickListener: (trainingId: Long) -> Unit) {
    fun onClick(train: Train) = clickListener(train.startTime)

}

sealed class DataItem {
    abstract val id: Long

    data class TrainingItem(val train: Train) : DataItem() {
        override val id = train.trainingId
    }

    object Header : DataItem() {
        override val id = Long.MAX_VALUE
    }
}