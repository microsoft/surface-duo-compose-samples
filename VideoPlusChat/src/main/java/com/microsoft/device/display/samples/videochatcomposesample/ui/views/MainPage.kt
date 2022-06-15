/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.google.android.exoplayer2.ExoPlayer
import com.microsoft.device.display.samples.videochatcomposesample.models.InfoProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

val infoProvider = InfoProvider()

@Composable
fun MainPage(windowState: WindowState, player: ExoPlayer) {

    val focusManager = LocalFocusManager.current

    TwoPaneLayout(
        paneMode = infoProvider.paneMode,
        pane1 = {
            if (infoProvider.isFullScreen) {
                VideoPage(player = player)
            } else {
                when {
                    windowState.isDualScreen() -> VideoPage(player = player)
                    windowState.isSingleLandscape() -> RowView(focusManager, player)
                    else -> ColumnView(focusManager, player)
                }
            }
        },
        pane2 = {
            ChatPage(focusManager)
        }
    )
}

@Composable
fun ColumnView(focusManager: FocusManager, player: ExoPlayer) {
    Column {
        VideoPage(height = 0.45f, player = player)
        ChatPage(focusManager = focusManager)
    }
}

@Composable
fun RowView(focusManager: FocusManager, player: ExoPlayer) {
    Row {
        VideoPage(width = 0.65f, player = player)
        ChatPage(focusManager = focusManager)
    }
}
