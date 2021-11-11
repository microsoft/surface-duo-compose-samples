/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.home

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import kotlinx.coroutines.flow.collect

private lateinit var appStateViewModel: AppStateViewModel
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun SetupUI(viewModel: AppStateViewModel, windowInfoRep: WindowInfoRepository) {
    appStateViewModel = viewModel

    var isAppSpanned by remember { mutableStateOf(false) }
    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
                var viewWidth = 0
                if (isAppSpanned) {
                    val foldingFeature = displayFeatures.first() as FoldingFeature
                    viewWidth = if (foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL) {
                        foldingFeature.bounds.left
                    } else {
                        foldingFeature.bounds.top
                    }
                }
                appStateViewModel.viewWidth = viewWidth
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualScreen = isAppSpanned || isTablet
    DualScreenUI(isDualScreen)
}

@Composable
fun DualScreenUI(isDualScreen: Boolean) {
    TwoPaneLayout(
        pane1 = { RestaurantViewWithTopBar(isDualScreen = isDualScreen, appStateViewModel = appStateViewModel) },
        pane2 = { MapViewWithTopBar(isDualScreen = isDualScreen, appStateViewModel = appStateViewModel) }
    )
}

@Composable
fun ImageView(imageId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.FillWidth
    )
}
