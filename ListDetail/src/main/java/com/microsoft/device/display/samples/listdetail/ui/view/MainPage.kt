/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun ListDetailApp(windowState: WindowState) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val updateSelectedIndex: (Int) -> Unit = { newIndex -> selectedIndex = newIndex }

    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = { ListViewWithTopBar(windowState.isDualPortrait(), selectedIndex, updateSelectedIndex) },
        pane2 = { DetailViewWithTopBar(windowState.isDualPortrait(), selectedIndex) }
    )
}

