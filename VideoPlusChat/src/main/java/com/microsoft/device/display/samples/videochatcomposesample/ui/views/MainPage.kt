/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.microsoft.device.display.samples.videochatcomposesample.models.InfoProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

val infoProvider = InfoProvider()

@Composable
fun MainPage(windowState: WindowState) {
    val focusManager = LocalFocusManager.current

    var currentPosition by rememberSaveable {
        mutableStateOf(0L)
    }
    val updatePosition: (Long) -> Unit = { newPosition -> currentPosition = newPosition }

    TwoPaneLayout(
        paneMode = infoProvider.paneMode,
        pane1 = {
            if (infoProvider.isFullScreen) {
                VideoPage(currentPosition = currentPosition, updatePosition = updatePosition)
            } else {
                when {
                    windowState.isDualScreen() -> VideoPage(currentPosition = currentPosition, updatePosition = updatePosition)
                    windowState.isSingleLandscape() -> RowView(focusManager, currentPosition, updatePosition)
                    else -> ColumnView(focusManager, currentPosition, updatePosition)
                }
            }
        },
        pane2 = {
            ChatPage(focusManager)
        }
    )
}

@Composable
fun ColumnView(
    focusManager: FocusManager,
    currentPosition: Long,
    updatePosition: (Long) -> Unit) {
    Column {
        VideoPage(height = 0.45f, currentPosition = currentPosition, updatePosition = updatePosition)
        ChatPage(focusManager = focusManager)
    }
}

@Composable
fun RowView(focusManager: FocusManager,
            currentPosition: Long,
            updatePosition: (Long) -> Unit) {
    Row {
        VideoPage(width = 0.65f, currentPosition = currentPosition, updatePosition = updatePosition)
        ChatPage(focusManager = focusManager)
    }
}
