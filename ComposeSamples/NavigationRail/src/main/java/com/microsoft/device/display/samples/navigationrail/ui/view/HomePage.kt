/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

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
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import kotlinx.coroutines.flow.collect

const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository) {
    // Create variables to track foldable device layout information
    var isAppSpanned by remember { mutableStateOf(false) }
    var isHingeVertical by remember { mutableStateOf(false) }

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
                if (isAppSpanned) {
                    val foldingFeature = displayFeatures.first() as FoldingFeature
                    isHingeVertical =
                        foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
                }
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualScreen = (isAppSpanned || isTablet)
    val isDualPortrait = isDualScreen && isHingeVertical

    DualScreenUI(isDualScreen, isDualPortrait)
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DualScreenUI(isDualScreen: Boolean, isDualPortrait: Boolean) {
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
        pane2 = { Pane2(isDualPortrait, imageId, updateImageId, currentRoute) },
    )
}

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

@Composable
fun Pane2(isDualPortrait: Boolean, imageId: Int?, updateImageId: (Int?) -> Unit, currentRoute: String) {
    // Retrieve selected image information
    val selectedImage = imageId?.let { DataProvider.getImage(imageId) }

    ShowWithTopBar(
        navIcon = if (isDualPortrait) null else { { BackNavIcon(updateImageId) } },
    ) {
        ItemDetailView(selectedImage, currentRoute)
    }
}
