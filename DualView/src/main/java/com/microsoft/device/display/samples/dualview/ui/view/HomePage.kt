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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState
import kotlin.math.roundToInt

@Composable
fun DualViewApp(windowState: WindowState) {
    var selectedIndex by rememberSaveable { mutableStateOf(-1) }
    val updateSelectedIndex: (Int) -> Unit = { newIndex -> selectedIndex = newIndex }
    val pane1SizeWidthDp = windowState.pane1SizeDp().width.dp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val viewWidth = if (windowState.isDualScreen()) pane1SizeWidthDp else screenWidthDp

    DualViewAppContent(
        isDualScreen = windowState.isDualScreen(),
        viewWidth = with(LocalDensity.current) { viewWidth.toPx() }.roundToInt(),
        selectedIndex = selectedIndex,
        updateSelectedIndex = updateSelectedIndex,
    )
}

@Composable
fun DualViewAppContent(
    isDualScreen: Boolean,
    viewWidth: Int,
    selectedIndex: Int,
    updateSelectedIndex: (Int) -> Unit,
) {
    TwoPaneLayout(
        pane1 = { RestaurantViewWithTopBar(isDualScreen, viewWidth, selectedIndex, updateSelectedIndex) },
        pane2 = { MapViewWithTopBar(isDualScreen, selectedIndex) }
    )
}
