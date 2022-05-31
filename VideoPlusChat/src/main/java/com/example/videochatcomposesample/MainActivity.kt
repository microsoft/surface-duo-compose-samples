package com.example.videochatcomposesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.videochatcomposesample.ui.theme.VideoChatComposeSampleTheme
import com.example.videochatcomposesample.ui.views.MainPage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.dualscreen.windowstate.rememberWindowState

//TODO Animate Using Constraint Layouts

/*
Levi O. Notes:
Currently the keyboard toggle is not the best when it comes to the vertical fold dual alignment (it is meant to go from video on top and chat on bottom
to a single pane row with chat and video alongside and keyboard on bottom).
I have tried to work with shifting focuses as you can see a small example in the MainPage.kt at the bottom, but this does not work, at least with how
I implemented it. I believe a better way to do this is to embed the app's composable into views and use constraint layouts and a global keyboard listener,
however I believe this would take away from the idea of creating a pure compose application and thus I have not done it.
 */


class MainActivity : ComponentActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var windowState: WindowState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(this).build()
        setContent {
            windowState = rememberWindowState()
            VideoChatComposeSampleTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    MainPage(windowState = windowState, player = player)
                }


            }
        }
    }

    override fun onStart() {
        super.onStart()
        val mediaItem =
            MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    companion object {
        const val STATE_PLAY_WHEN_READY = "playerPlayWhenReady"
        const val STATE_CURRENT_POSITION = "playerPlaybackPosition"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        player.playWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY)
        player.seekTo(savedInstanceState.getLong(STATE_CURRENT_POSITION))
        player.prepare()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putBoolean(STATE_PLAY_WHEN_READY, player.playWhenReady)
            putLong(STATE_CURRENT_POSITION, player.currentPosition)
        }

        super.onSaveInstanceState(outState)
    }
}


data class ChatMessage(val author:String, val message: String, val color: Color)



