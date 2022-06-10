/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.microsoft.device.display.samples.videochatcomposesample.R
import com.microsoft.device.display.samples.videochatcomposesample.models.InfoProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode

@Composable
fun VideoPage(player: ExoPlayer, infoProvider: InfoProvider) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Video(modifier = Modifier.align(Alignment.Center), player = player)
        FullscreenButton(modifier = Modifier.align(Alignment.TopEnd), infoProvider)
    }
}

@Composable
fun FullscreenButton(modifier: Modifier, infoProvider: InfoProvider) {
    fun onClick() = if (infoProvider.isFullScreen) infoProvider.updatePaneMode(TwoPaneMode.TwoPane) else infoProvider.updatePaneMode(TwoPaneMode.VerticalSingle)

    if (infoProvider.isFullScreen) Icon(
        tint = MaterialTheme.colors.onBackground,
        painter = painterResource(id = R.drawable.exitfullscreen),
        contentDescription = stringResource(
            id = R.string.contentFull
        ),
        modifier = modifier.clickable(onClick = { onClick() })

    ) else Icon(
        tint = MaterialTheme.colors.onBackground,
        painter = painterResource(id = R.drawable.fullscreen),
        contentDescription = stringResource(
            id = R.string.contentMin
        ),
        modifier = modifier.clickable(onClick = { onClick() })
    )
}

@Composable
fun Video(modifier: Modifier, player: ExoPlayer) {
    DisposableEffect(true) {
        onDispose {
            player.pause()
        }
    }
    val context = LocalContext.current
    val playerView = StyledPlayerView(context)
    playerView.player = player
    playerView.setShowPreviousButton(false)
    playerView.setShowNextButton(false)
    AndroidView(
        modifier = modifier,
        factory = {
            playerView
        }
    )
}
