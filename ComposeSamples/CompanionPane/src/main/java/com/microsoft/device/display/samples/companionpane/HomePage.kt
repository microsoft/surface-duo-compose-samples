/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.companionpane.uicomponent.BrightnessPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.DefinitionPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.EffectPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.FullFilterControl
import com.microsoft.device.display.samples.companionpane.uicomponent.ImagePanel
import com.microsoft.device.display.samples.companionpane.uicomponent.MagicWandPanel
import com.microsoft.device.display.samples.companionpane.uicomponent.ShortFilterControl
import com.microsoft.device.display.samples.companionpane.uicomponent.VignettePanel
import kotlinx.coroutines.flow.collect

private val shortSlideWidth = 200.dp
private val longSlideWidth = 350.dp
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

enum class ScreenState {
    SinglePortrait,
    SingleLandscape,
    DualPortrait,
    DualLandscape
}

@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository) {
    var screenState by remember { mutableStateOf(ScreenState.SinglePortrait) }

    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    screenState = if (isPortrait) {
        ScreenState.SinglePortrait
    } else {
        ScreenState.SingleLandscape
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    if (isTablet) {
        screenState = if (isPortrait) {
            ScreenState.DualLandscape
        } else {
            ScreenState.DualPortrait
        }
    }

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                val isScreenSpanned = displayFeatures.isNotEmpty()
                if (isScreenSpanned) {
                    val foldingFeature = displayFeatures.first() as FoldingFeature
                    val isVertical = foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
                    screenState = if (isVertical) {
                        ScreenState.DualPortrait
                    } else {
                        ScreenState.DualLandscape
                    }
                }
            }
    }

    when (screenState) {
        ScreenState.SinglePortrait -> PortraitLayout()
        ScreenState.SingleLandscape -> LandscapeLayout()
        ScreenState.DualPortrait -> DualPortraitLayout()
        ScreenState.DualLandscape -> DualLandscapeLayout()
    }
}

@Composable
fun DualPortraitLayout() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(50.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ImagePanel(
                Modifier.padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            )
            EffectPanel()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(35.dp)
            ) {
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
fun DualLandscapeLayout() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 30.dp)
                .clipToBounds()
                .weight(.9f),
            contentAlignment = Alignment.Center
        ) {
            ImagePanel(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                .weight(1.1f),
            verticalArrangement = Arrangement.SpaceEvenly
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
