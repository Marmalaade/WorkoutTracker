package com.example.workouttracker.customview

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.example.workouttracker.R
import com.example.workouttracker.mediaplayer.BackgroundMusicPlayer

class MediaPlayerSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatImageButton(context, attrs) {

    fun setState(state: Boolean) {
        if (state) {
            setImageResource(R.drawable.anim_volume_off_to_on)
            BackgroundMusicPlayer.resumePlayer()
        } else {
            setImageResource(R.drawable.anim_volume_on_to_off)
            BackgroundMusicPlayer.pausePlayer()
        }
        (drawable as AnimatedVectorDrawable).start()
    }

}