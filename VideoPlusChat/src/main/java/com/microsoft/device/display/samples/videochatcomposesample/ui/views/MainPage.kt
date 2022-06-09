/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.microsoft.device.display.samples.videochatcomposesample.models.InfoProvider
import com.google.android.exoplayer2.ExoPlayer
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

var infoProvider: InfoProvider = InfoProvider()

@Composable
fun MainPage(windowState: WindowState, player: ExoPlayer) {

    // very strange bug with Exoplayer and Windowstate so I have to define them here or else the player will not show
    // Most likely due to the fact that it does not recompose this main page
    val dual = windowState.isDualScreen()
    val horizontal = windowState.isSingleLandscape()

    val focusManager = LocalFocusManager.current
    TwoPaneLayout(
        paneMode = infoProvider.paneMode,
        pane1 = {
            if (infoProvider.isFullScreen) {
                VideoPage(player = player)
            } else {
                when {
                    dual -> VideoPage(player = player)
                    horizontal -> RowView(focusManager = focusManager, player = player)
                    else -> ColumnView(focusManager = focusManager, player = player)
                }
            }
        },
        pane2 = {
            ChatPage(focusManager = focusManager)
        }
    )
}

@Composable
fun ColumnView(focusManager: FocusManager, player: ExoPlayer) {
    Column {
        Box(modifier = Modifier.fillMaxHeight(0.45f)) {
            VideoPage(player = player)
        }

        Box(modifier = Modifier.fillMaxHeight()) {
            ChatPage(focusManager = focusManager)
        }
    }
}

@Composable
fun RowView(focusManager: FocusManager, player: ExoPlayer) {
    Row {
        Box(modifier = Modifier.fillMaxWidth(0.65f)) {
            VideoPage(player = player)
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            ChatPage(focusManager = focusManager)
        }
    }
}
