/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.ui.components.ItemTopBar
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2
import kotlinx.coroutines.flow.collect

const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository) {
    // Create variables to track foldable device layout information
    var isAppSpanned by remember { mutableStateOf(false) }
    var isHingeVertical by remember { mutableStateOf(false) }
    var hingeSize by remember { mutableStateOf(0) }

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
                if (isAppSpanned) {
                    val foldingFeature = displayFeatures.first() as FoldingFeature
                    isHingeVertical =
                        foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
                    hingeSize = if (isHingeVertical)
                        foldingFeature.bounds.width()
                    else
                        foldingFeature.bounds.height()
                }
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualScreen = (isAppSpanned || isTablet)
    val isDualPortrait = when {
        isAppSpanned -> isHingeVertical
        isTablet -> LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
        else -> false
    }
    val isDualLandscape = when {
        isAppSpanned -> !isHingeVertical
        isTablet -> LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }

    DualScreenUI(isDualScreen, isDualPortrait, isDualLandscape, hingeSize.dp)
}

@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DualScreenUI(
    isDualScreen: Boolean,
    isDualPortrait: Boolean,
    isDualLandscape: Boolean,
    hingeSize: Dp,
) {
    // Set up starting route for navigation in pane 1
    var currentRoute by rememberSaveable { mutableStateOf(navDestinations[0].route) }
    val updateRoute: (String) -> Unit = { newRoute -> currentRoute = newRoute }

    // Set up variable to store selected image id
    var imageId: Int? by rememberSaveable { mutableStateOf(null) }
    val updateImageId: (Int?) -> Unit = { newId -> imageId = newId }

    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = {
            Pane1(isDualScreen, isDualPortrait, imageId, updateImageId, currentRoute, updateRoute)
        },
        pane2 = {
            Pane2(isDualPortrait, isDualLandscape, hingeSize, imageId, updateImageId, currentRoute)
        },
    )

    // If only one pane is being displayed, make sure the correct pane is displayed
    if (!isDualPortrait) {
        if (imageId != null)
            navigateToPane2()
        else
            navigateToPane1()
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Pane1(
    isDualScreen: Boolean,
    isDualPortrait: Boolean,
    imageId: Int?,
    updateImageId: (Int?) -> Unit,
    currentRoute: String,
    updateRoute: (String) -> Unit
) {
    ShowWithNav(isDualScreen, isDualPortrait, imageId, updateImageId, currentRoute, updateRoute)
}

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun Pane2(
    isDualPortrait: Boolean,
    isDualLandscape: Boolean,
    hingeSize: Dp,
    imageId: Int?,
    updateImageId: (Int?) -> Unit,
    currentRoute: String,
) {
    // Retrieve selected image information
    val selectedImage = imageId?.let { DataProvider.getImage(imageId) }

    // Set up back press action to return to pane 1 and clear image selection
    val onBackPressed = {
        navigateToPane1()
        updateImageId(null)
    }
    BackHandler { if (!isDualPortrait) onBackPressed() }

    ItemDetailView(isDualPortrait, isDualLandscape, hingeSize, selectedImage, currentRoute)
    // If only one pane is being displayed, show a "back" icon
    if (!isDualPortrait) {
        ItemTopBar(
            onClick = { onBackPressed() }
        )
    }
}
