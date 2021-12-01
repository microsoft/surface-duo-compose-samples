/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.companionpane.uicomponent.BrightnessPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.DefinitionPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.EffectPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.FullFilterControl
import com.microsoft.device.display.samples.companionpane.uicomponent.ImagePanel
import com.microsoft.device.display.samples.companionpane.uicomponent.MagicWandPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.ShortFilterControl
import com.microsoft.device.display.samples.companionpane.uicomponent.VignettePanel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.window.WindowMode
import com.microsoft.device.dualscreen.window.WindowState

private val shortSlideWidth = 200.dp
private val longSlideWidth = 350.dp

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
fun ShowWithTopBar(content: @Composable () -> Unit, title: String? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicText(
                        text = title ?: "",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
        },
        content = { content() }
    )
}

@Composable
fun Pane1(windowMode: WindowMode) {
    ShowWithTopBar(
        content = {
            when (windowMode) {
                WindowMode.SINGLE_PORTRAIT -> PortraitLayout()
                WindowMode.SINGLE_LANDSCAPE -> LandscapeLayout()
                WindowMode.DUAL_PORTRAIT -> DualPortraitPane1()
                WindowMode.DUAL_LANDSCAPE -> DualLandscapePane1()
            }
        },
        title = stringResource(R.string.app_name)
    )
}

@Composable
fun Pane2(windowMode: WindowMode) {
    when (windowMode) {
        WindowMode.DUAL_PORTRAIT -> {
            ShowWithTopBar(
                content = { DualPortraitPane2() }
            )
        }
        WindowMode.DUAL_LANDSCAPE -> DualLandscapePane2()
        else -> {}
    }
}

@Composable
fun DualPortraitPane1() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ImagePanel(Modifier.padding(horizontal = 30.dp))
        EffectPanel()
    }
}

@Composable
fun DualPortraitPane2() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(80.dp, Alignment.CenterVertically)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
            MagicWandPanel(modifier = Modifier.width(longSlideWidth))
            DefinitionPanel(modifier = Modifier.width(longSlideWidth))
            VignettePanel(modifier = Modifier.width(longSlideWidth))
            BrightnessPanel(modifier = Modifier.width(longSlideWidth))
        }
        ShortFilterControl()
    }
}

@Composable
fun DualLandscapePane1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        ImagePanel(modifier = Modifier.padding(all = 20.dp))
    }
}

@Composable
fun DualLandscapePane2() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        EffectPanel()
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
        ) {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                MagicWandPanel(modifier = Modifier.width(shortSlideWidth))
                Spacer(Modifier.height(20.dp))
                DefinitionPanel(modifier = Modifier.width(shortSlideWidth))
            }
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                VignettePanel(modifier = Modifier.width(shortSlideWidth))
                Spacer(Modifier.height(20.dp))
                BrightnessPanel(modifier = Modifier.width(shortSlideWidth))
            }
        }
        ShortFilterControl()
    }
}

@Composable
fun PortraitLayout() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        ImagePanel(
            Modifier
                .padding(start = 50.dp, end = 50.dp)
                .wrapContentHeight()
        )
        EffectPanel()
        FullFilterControl()
    }
}

@Composable
fun LandscapeLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 10.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ImagePanel(Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MagicWandPanel(modifier = Modifier.width(shortSlideWidth))
                DefinitionPanel(modifier = Modifier.width(shortSlideWidth))
                VignettePanel(modifier = Modifier.width(shortSlideWidth))
                BrightnessPanel(modifier = Modifier.width(shortSlideWidth))
            }
        }
        ShortFilterControl()
    }
}
