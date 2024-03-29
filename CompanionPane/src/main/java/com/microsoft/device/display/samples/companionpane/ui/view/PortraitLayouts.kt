/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.R
import com.microsoft.device.display.samples.companionpane.ui.components.BrightnessPanel
import com.microsoft.device.display.samples.companionpane.ui.components.DefinitionPanel
import com.microsoft.device.display.samples.companionpane.ui.components.FilterPanel
import com.microsoft.device.display.samples.companionpane.ui.components.FullFilterControl
import com.microsoft.device.display.samples.companionpane.ui.components.ImagePanel
import com.microsoft.device.display.samples.companionpane.ui.components.MagicWandPanel
import com.microsoft.device.display.samples.companionpane.ui.components.ShortFilterControl
import com.microsoft.device.display.samples.companionpane.ui.components.SliderState
import com.microsoft.device.display.samples.companionpane.ui.components.VignettePanel

private val longSliderWidth = 350.dp

@Composable
fun DualPortraitPane1(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .testTag(stringResource(R.string.dual_port_pane1)),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ImagePanel(Modifier.padding(horizontal = 30.dp))
        FilterPanel()
    }
}

@Composable
fun DualPortraitPane2(sliderState: SliderState, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .testTag(stringResource(R.string.dual_port_pane2)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(80.dp, Alignment.CenterVertically)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
            MagicWandPanel(modifier = Modifier.width(longSliderWidth), sliderState.magicWand) {
                sliderState.magicWand = it
            }
            DefinitionPanel(modifier = Modifier.width(longSliderWidth), sliderState.definition) {
                sliderState.definition = it
            }
            VignettePanel(modifier = Modifier.width(longSliderWidth), sliderState.vignette) {
                sliderState.vignette = it
            }
            BrightnessPanel(modifier = Modifier.width(longSliderWidth), sliderState.brightness) {
                sliderState.brightness = it
            }
        }
        ShortFilterControl()
    }
}

@Composable
fun PortraitLayout(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .testTag(stringResource(R.string.single_port)),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        ImagePanel(
            Modifier
                .padding(horizontal = 50.dp)
                .wrapContentHeight()
        )
        FilterPanel()
        FullFilterControl()
    }
}
