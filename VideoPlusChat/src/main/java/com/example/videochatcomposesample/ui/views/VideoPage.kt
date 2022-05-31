package com.example.videochatcomposesample.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode

@Composable
fun VideoPage(player: ExoPlayer) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()

    ) {
        Video(modifier = Modifier.align(Alignment.Center), player = player)
        FullscreenButton(modifier = Modifier.align(Alignment.TopEnd))
    }
}

@Composable
fun FullscreenButton(modifier: Modifier) {
    Button(
        modifier = modifier, colors = ButtonDefaults.buttonColors(MaterialTheme.colors.background),
        onClick = {
            if (infoProvider.isFullScreen) infoProvider.updatePaneMode(TwoPaneMode.TwoPane) else infoProvider.updatePaneMode(TwoPaneMode.VerticalSingle)
        }
    ) {
        if (infoProvider.isFullScreen) Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Full") else Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Min")
    }
}

@Composable
fun Video(modifier: Modifier, player: ExoPlayer) {
    Box(modifier = modifier) { // Fetching the Local Context
        val context = LocalContext.current
        val playerView = PlayerView(context)
        playerView.player = player
        AndroidView(
            factory = {
                playerView
            }
        )
    }
}
