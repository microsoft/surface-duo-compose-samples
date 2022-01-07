/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun DualViewApp(windowState: WindowState, viewSize: Int? = null) {
    var selectedIndex by rememberSaveable { mutableStateOf(-1) }
    val updateSelectedIndex: (Int) -> Unit = { newIndex -> selectedIndex = newIndex }

    DualViewAppContent(
        windowState.isDualScreen(),
        windowState.foldablePaneWidth,
        selectedIndex,
        updateSelectedIndex,
        viewSize
    )
}

@Composable
fun DualViewAppContent(
    isDualScreen: Boolean,
    viewWidth: Int,
    selectedIndex: Int,
    updateSelectedIndex: (Int) -> Unit,
    viewSize: Int? = null
) {
    TwoPaneLayout(
        pane1 = { RestaurantViewWithTopBar(isDualScreen, viewWidth, selectedIndex, updateSelectedIndex) },
        pane2 = { MapViewWithTopBar(isDualScreen, selectedIndex, viewSize) }
    )
}
