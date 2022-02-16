package com.example.workouttracker.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import com.example.workouttracker.R

object BackgroundMusicPlayer {

    private lateinit var mediaPLayer: MediaPlayer

    fun startPlayer(context: Context) {
        mediaPLayer = MediaPlayer.create(context, R.raw.background_music)
        mediaPLayer.start()
    }

    fun pausePlayer() {
        mediaPLayer.pause()
    }

    fun resumePlayer() {
        if (!mediaPLayer.isPlaying) {
            mediaPLayer.seekTo(mediaPLayer.currentPosition)
            mediaPLayer.start()
        }

    }
}