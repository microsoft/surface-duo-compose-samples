/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.ui.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.R
import com.microsoft.device.display.samples.companionpane.ui.components.BrightnessPanel
import com.microsoft.device.display.samples.companionpane.ui.components.DefinitionPanel
import com.microsoft.device.display.samples.companionpane.ui.components.FilterPanel
import com.microsoft.device.display.samples.companionpane.ui.components.ImagePanel
import com.microsoft.device.display.samples.companionpane.ui.components.MagicWandPanel
import com.microsoft.device.display.samples.companionpane.ui.components.ShortFilterControl
import com.microsoft.device.display.samples.companionpane.ui.components.SliderState
import com.microsoft.device.display.samples.companionpane.ui.components.VignettePanel

private val shortSlideWidth = 200.dp

@Composable
fun DualLandscapePane1(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(bottom = 20.dp)
            .clipToBounds()
            .testTag(stringResource(R.string.dual_land_pane1)),
        contentAlignment = Alignment.Center
    ) {
        ImagePanel(modifier = Modifier.padding(all = 20.dp))
    }
}

@Composable
fun DualLandscapePane2(sliderState: SliderState, paddingValues: PaddingValues = PaddingValues()) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                .testTag(stringResource(R.string.dual_land_pane2)),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            FilterPanel()
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .horizontalScroll(rememberScrollState())
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    MagicWandPanel(
                        modifier = Modifier.width(shortSlideWidth),
                        sliderState.magicWand
                    ) {
                        sliderState.magicWand = it
                    }
                    Spacer(Modifier.height(20.dp))
                    DefinitionPanel(
                        modifier = Modifier.width(shortSlideWidth),
                        sliderState.definition
                    ) {
                        sliderState.definition = it
                    }
                }
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    VignettePanel(
                        modifier = Modifier.width(shortSlideWidth),
                        sliderState.vignette
                    ) {
                        sliderState.vignette = it
                    }
                    Spacer(Modifier.height(20.dp))
                    BrightnessPanel(
                        modifier = Modifier.width(shortSlideWidth),
                        sliderState.brightness
                    ) {
                        sliderState.brightness = it
                    }
                }
            }
            ShortFilterControl()
        }
    }
}

@Composable
fun LandscapeLayout(sliderState: SliderState, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 20.dp, start = 20.dp, end = 10.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState())
            .testTag(stringResource(R.string.single_land)),
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
                MagicWandPanel(modifier = Modifier.width(shortSlideWidth), sliderState.magicWand) {
                    sliderState.magicWand = it
                }
                DefinitionPanel(
                    modifier = Modifier.width(shortSlideWidth),
                    sliderState.definition
                ) {
                    sliderState.definition = it
                }
                VignettePanel(modifier = Modifier.width(shortSlideWidth), sliderState.vignette) {
                    sliderState.vignette = it
                }
                BrightnessPanel(
                    modifier = Modifier.width(shortSlideWidth),
                    sliderState.brightness
                ) {
                    sliderState.brightness = it
                }
            }
        }
        ShortFilterControl()
    }
}
