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

@Composable
fun MainPage(windowState: WindowState, player: ExoPlayer) {
    val infoProvider: InfoProvider = InfoProvider()
    val focusManager = LocalFocusManager.current

    TwoPaneLayout(
        paneMode = infoProvider.paneMode,
        pane1 = {
            if (infoProvider.isFullScreen) {
                VideoPage(player, infoProvider)
            } else {
                when {
                    windowState.isDualScreen() -> VideoPage(player)
                    windowState.isSingleLandscape() -> RowView(focusManager, player, infoProvider)
                    else -> ColumnView(focusManager, player, infoProvider)
                }
            }
        },
        pane2 = {
            ChatPage(focusManager)
        }
    )
}

@Composable
fun ColumnView(focusManager: FocusManager, player: ExoPlayer, infoProvider: InfoProvider) {
    Column {
        VideoPage(height = 0.45f, player = player, infoProvider = infoProvider)
        ChatPage(focusManager = focusManager, infoProvider = infoProvider)
    }
}

@Composable
fun RowView(focusManager: FocusManager, player: ExoPlayer, infoProvider: InfoProvider) {
    Row {
        VideoPage(width = 0.65f, player = player, infoProvider = infoProvider)
        ChatPage(focusManager = focusManager, infoProvider = infoProvider)
    }
}
