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
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun MainPage(windowState: WindowState) {
    val focusManager = LocalFocusManager.current

    var isFullScreen by rememberSaveable {
        mutableStateOf(false)
    }
    val updateFullScreen: (Boolean) -> Unit = { newValue -> isFullScreen = newValue }

    var currentPosition by rememberSaveable {
        mutableStateOf(0L)
    }
    val updatePosition: (Long) -> Unit = { newPosition -> currentPosition = newPosition }

    val paneMode = if (isFullScreen) TwoPaneMode.VerticalSingle else TwoPaneMode.TwoPane

    TwoPaneLayout(
        paneMode = paneMode,
        pane1 = {
            if (isFullScreen) {
                VideoPage(
                    isFullScreen = isFullScreen,
                    updateFullScreen = updateFullScreen,
                    currentPosition = currentPosition,
                    updatePosition = updatePosition
                )
            } else {
                when {
                    windowState.isDualScreen() -> VideoPage(
                        isFullScreen = isFullScreen,
                        updateFullScreen = updateFullScreen,
                        currentPosition = currentPosition,
                        updatePosition = updatePosition
                    )
                    windowState.isSingleLandscape() -> RowView(focusManager, isFullScreen, updateFullScreen, currentPosition, updatePosition)
                    else -> ColumnView(focusManager, isFullScreen, updateFullScreen, currentPosition, updatePosition)
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
    isFullScreen: Boolean,
    updateFullScreen: (Boolean) -> Unit,
    currentPosition: Long,
    updatePosition: (Long) -> Unit
) {
    Column {
        VideoPage(height = 0.45f, isFullScreen = isFullScreen, updateFullScreen = updateFullScreen, currentPosition = currentPosition, updatePosition = updatePosition)
        ChatPage(focusManager = focusManager)
    }
}

@Composable
fun RowView(
    focusManager: FocusManager,
    isFullScreen: Boolean,
    updateFullScreen: (Boolean) -> Unit,
    currentPosition: Long,
    updatePosition: (Long) -> Unit
) {
    Row {
        VideoPage(width = 0.65f, isFullScreen = isFullScreen, updateFullScreen = updateFullScreen, currentPosition = currentPosition, updatePosition = updatePosition)
        ChatPage(focusManager = focusManager)
    }
}
