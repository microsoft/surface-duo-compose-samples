/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.uicomponent.BrightnessPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.DefinitionPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.EffectPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.FullFilterControl
import com.microsoft.device.display.samples.companionpane.uicomponent.ImagePanel
import com.microsoft.device.display.samples.companionpane.uicomponent.MagicWandPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.ShortFilterControl
import com.microsoft.device.display.samples.companionpane.uicomponent.VignettePanel
import com.microsoft.device.display.samples.companionpane.viewModels.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel
private val shortSlideWidth = 200.dp
private val longSlideWidth = 350.dp

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel
    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

    val isScreenPortraitLiveData = appStateViewModel.getIsScreenPortraitLiveData()
    val isScreenPortrait = isScreenPortraitLiveData.observeAsState(initial = true).value

    if (isScreenSpanned) {
        if (isScreenPortrait) {
            PortraitSpannedLayout()
        } else {
            LandscapeSpannedLayout()
        }
    } else {
        if (isScreenPortrait) {
            PortraitLayout()
        } else {
            LandscapeLayout()
        }
    }
}

@Composable
fun LandscapeSpannedLayout() { // dual portrait mode
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(20.dp))
            ImagePanel(Modifier.height(380.dp).fillMaxWidth())
            Spacer(Modifier.height(25.dp))
            EffectPanel()
        }
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
                MagicWandPanel(modifier = Modifier.width(longSlideWidth))
                DefinitionPanel(modifier = Modifier.width(longSlideWidth))
                VignettePanel(modifier = Modifier.width(longSlideWidth))
                BrightnessPanel(modifier = Modifier.width(longSlideWidth))
            }
            Spacer(Modifier.height(80.dp))
            ShortFilterControl()
        }
    }
}

@Composable
fun PortraitSpannedLayout() { // dual landscape mode
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            ImagePanel(modifier = Modifier.width(500.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            EffectPanel()
            Spacer(Modifier.height(60.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                Spacer(Modifier.width(20.dp))
                Column(modifier = Modifier.wrapContentWidth()) {
                    MagicWandPanel(modifier = Modifier.width(shortSlideWidth))
                    Spacer(Modifier.height(20.dp))
                    DefinitionPanel(modifier = Modifier.width(shortSlideWidth))
                }
                Column(modifier = Modifier.wrapContentWidth()) {
                    VignettePanel(modifier = Modifier.width(shortSlideWidth))
                    Spacer(Modifier.height(20.dp))
                    BrightnessPanel(modifier = Modifier.width(shortSlideWidth))
                }
            }
            Spacer(Modifier.height(60.dp))
            ShortFilterControl()
        }
    }
}

@Composable
fun PortraitLayout() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        ImagePanel(Modifier.height(350.dp).fillMaxWidth())
        EffectPanel()
        FullFilterControl()
    }
}

@Composable
fun LandscapeLayout() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(40.dp))
        Row(modifier = Modifier.wrapContentWidth()) {
            Spacer(Modifier.width(20.dp))
            ImagePanel(Modifier.width(360.dp))
            Spacer(Modifier.width(20.dp))
            Column(
                modifier = Modifier.wrapContentWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MagicWandPanel(modifier = Modifier.width(shortSlideWidth))
                DefinitionPanel(modifier = Modifier.width(shortSlideWidth))
                VignettePanel(modifier = Modifier.width(shortSlideWidth))
                BrightnessPanel(modifier = Modifier.width(shortSlideWidth))
            }
            Spacer(Modifier.width(20.dp))
        }
        Spacer(Modifier.height(30.dp))
        ShortFilterControl()
    }
}
