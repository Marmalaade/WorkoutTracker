package com.example.workouttracker

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workouttracker.database.Train
import java.text.SimpleDateFormat

fun convertNumericQualityToString(quality: Int, resources: Resources): String {
    var qualityString = resources.getString(R.string.four_ok)
    when (quality) {
        -1 -> qualityString = "--"
        1 -> qualityString = resources.getString(R.string.one_very_bad)
        2 -> qualityString = resources.getString(R.string.two_poor)
        3 -> qualityString = resources.getString(R.string.three_soso)
        4 -> qualityString = resources.getString(R.string.four_ok)
        5 -> qualityString = resources.getString(R.string.five_pretty_good)
        6 -> qualityString = resources.getString(R.string.six_excellent)
    }
    return qualityString
}


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}


fun formatTrains(nights: List<Train>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTime)}<br>")
            if (it.endTime != it.startTime) {
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTime)}<br>")
                append(resources.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.trainingQuality, resources)}<br>")
                append(resources.getString(R.string.hours_train))
                // Hours
                append("\t ${it.endTime.minus(it.startTime) / 1000 / 60 / 60}:")
                // Minutes
                append("${it.endTime.minus(it.startTime) / 1000 / 60}:")
                // Seconds
                append("${it.endTime.minus(it.startTime) / 1000}<br><br>")
            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)