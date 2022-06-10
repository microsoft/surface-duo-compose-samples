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
                VideoPage(player = player, infoProvider)
            } else {
                when {
                    windowState.isDualScreen() -> VideoPage(player = player)
                    windowState.isSingleLandscape() -> RowView(focusManager = focusManager, player = player, infoProvider)
                    else -> ColumnView(focusManager = focusManager, player = player, infoProvider)
                }
            }
        },
        pane2 = {
            ChatPage(focusManager = focusManager)
        }
    )
}

@Composable
fun ColumnView(focusManager: FocusManager, player: ExoPlayer, infoProvider: InfoProvider) {
    Column {
        Box(modifier = Modifier.fillMaxHeight(0.45f)) {
            VideoPage(player = player, infoProvider)
        }

        Box(modifier = Modifier.fillMaxHeight()) {
            ChatPage(focusManager = focusManager)
        }
    }
}

@Composable
fun RowView(focusManager: FocusManager, player: ExoPlayer, infoProvider: InfoProvider) {
    Row {
        Box(modifier = Modifier.fillMaxWidth(0.65f)) {
            VideoPage(player = player, infoProvider)
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            ChatPage(focusManager = focusManager)
        }
    }
}
