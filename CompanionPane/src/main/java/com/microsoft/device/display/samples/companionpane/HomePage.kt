/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.companionpane.ui.view.DualLandscapePane1
import com.microsoft.device.display.samples.companionpane.ui.view.DualLandscapePane2
import com.microsoft.device.display.samples.companionpane.ui.view.DualPortraitPane1
import com.microsoft.device.display.samples.companionpane.ui.view.DualPortraitPane2
import com.microsoft.device.display.samples.companionpane.ui.view.LandscapeLayout
import com.microsoft.device.display.samples.companionpane.ui.view.PortraitLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowMode
import com.microsoft.device.dualscreen.windowstate.WindowState


@Composable
fun CompanionPaneApp(windowState: WindowState) {
    var windowMode by remember { mutableStateOf(WindowMode.SINGLE_PORTRAIT) }
    windowMode = windowState.windowMode

    TwoPaneLayout(
        pane1 = { Pane1(windowMode) },
        pane2 = { Pane2(windowMode) },
    )
}

@Composable
fun CompanionPaneTopBar(title: String? = null) {
    TopAppBar(
        modifier = Modifier.testTag(stringResource(R.string.top_bar)),
        title = { Text(text = title ?: "") },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun Pane1(windowMode: WindowMode) {
    Scaffold(
        topBar = { CompanionPaneTopBar(stringResource(R.string.app_name)) }
    ) {
        when (windowMode) {
            WindowMode.SINGLE_PORTRAIT -> PortraitLayout()
            WindowMode.SINGLE_LANDSCAPE -> LandscapeLayout()
            WindowMode.DUAL_PORTRAIT -> DualPortraitPane1()
            WindowMode.DUAL_LANDSCAPE -> DualLandscapePane1()
        }
    }
}

@Composable
fun Pane2(windowMode: WindowMode) {
    when (screenState) {
        ScreenState.DualPortrait -> {
            Scaffold(
                topBar = { CompanionPaneTopBar() }
            ) {
                DualPortraitPane2()
            }
        }
        WindowMode.DUAL_LANDSCAPE -> DualLandscapePane2()
        else -> {}
    }
}
