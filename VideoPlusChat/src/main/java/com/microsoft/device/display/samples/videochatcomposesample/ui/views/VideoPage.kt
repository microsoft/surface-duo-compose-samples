/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.microsoft.device.display.samples.videochatcomposesample.R

@Composable
fun VideoPage(
    width: Float = 1.0f,
    height: Float = 1.0f,
    isFullScreen: Boolean,
    updateFullScreen: (Boolean) -> Unit,
    currentPosition: Long,
    updatePosition: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .fillMaxHeight(height)
    ) {
        Video(
            modifier = Modifier.align(Alignment.Center),
            currentPosition = currentPosition,
            updatePosition = updatePosition
        )
        FullscreenButton(modifier = Modifier.align(Alignment.TopEnd), isFullScreen, updateFullScreen)
    }
}

@Composable
fun FullscreenButton(modifier: Modifier, isFullScreen: Boolean, updateFullScreen: (Boolean) -> Unit) {
    fun onClick() = updateFullScreen(!isFullScreen)
    val drawableResId = if (isFullScreen) R.drawable.exitfullscreen else R.drawable.fullscreen
    val stringResId = if (isFullScreen) R.string.contentMin else R.string.contentFull

    IconButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            tint = MaterialTheme.colors.onBackground,
            painter = painterResource(id = drawableResId),
            contentDescription = stringResource(id = stringResId)
        )
    }
}

@Composable
fun Video(modifier: Modifier, currentPosition: Long, updatePosition: (Long) -> Unit) {
    val context = LocalContext.current
    val mediaItem =
        MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")

    val player = remember {
        mutableStateOf(
            ExoPlayer.Builder(context).build().apply {
                this.setMediaItem(mediaItem)
                this.playWhenReady = true
                this.prepare()
            }
        )
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                StyledPlayerView(context).apply {
                    this.player = player.value
                    setShowPreviousButton(false)
                    setShowNextButton(false)
                }
            },
            update = {
                player.value.seekTo(currentPosition)
            }
        )
    ) {
        onDispose {
            updatePosition(player.value.currentPosition)
            player.value.release()
        }
    }
}
