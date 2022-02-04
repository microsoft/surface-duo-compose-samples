/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.graphics.RectF
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.ui.components.ItemTopBar
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2
import com.microsoft.device.dualscreen.windowstate.WindowState

@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NavigationRailApp(windowState: WindowState) {
    // Set up starting route for navigation in pane 1
    var currentRoute by rememberSaveable { mutableStateOf(navDestinations[0].route) }
    val updateRoute: (String) -> Unit = { newRoute -> currentRoute = newRoute }

    // Set up variable to store selected image id
    var imageId: Int? by rememberSaveable { mutableStateOf(null) }
    val updateImageId: (Int?) -> Unit = { newId -> imageId = newId }

    NavigationRailAppContent(
        isDualScreen = windowState.isDualScreen(),
        isDualPortrait = windowState.isDualPortrait(),
        isDualLandscape = windowState.isDualLandscape(),
        foldIsOccluding = windowState.foldIsOccluding,
        foldBoundsDp = windowState.foldBoundsDp,
        windowHeight = windowState.windowHeightDp,
        imageId = imageId,
        updateImageId = updateImageId,
        currentRoute = currentRoute,
        updateRoute = updateRoute
    )
}

@ExperimentalUnitApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NavigationRailAppContent(
    isDualScreen: Boolean,
    isDualPortrait: Boolean,
    isDualLandscape: Boolean,
    foldIsOccluding: Boolean,
    foldBoundsDp: RectF,
    windowHeight: Dp,
    imageId: Int?,
    updateImageId: (Int?) -> Unit,
    currentRoute: String,
    updateRoute: (String) -> Unit
) {
    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = {
            Pane1(isDualScreen, isDualPortrait, imageId, updateImageId, currentRoute, updateRoute)
        },
        pane2 = {
            Pane2(
                isDualPortrait = isDualPortrait,
                isDualLandscape = isDualLandscape,
                foldIsOccluding = foldIsOccluding,
                foldBoundsDp = foldBoundsDp,
                windowHeight = windowHeight,
                imageId = imageId,
                updateImageId = updateImageId,
                currentRoute = currentRoute
            )
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
    foldIsOccluding: Boolean,
    foldBoundsDp: RectF,
    windowHeight: Dp,
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

    ItemDetailView(
        isDualPortrait = isDualPortrait,
        isDualLandscape = isDualLandscape,
        foldIsOccluding = foldIsOccluding,
        foldBoundsDp = foldBoundsDp,
        windowHeight = windowHeight,
        selectedImage = selectedImage,
        currentRoute = currentRoute
    )
    // If only one pane is being displayed, show a "back" icon
    if (!isDualPortrait) {
        ItemTopBar(
            onClick = { onBackPressed() }
        )
    }
}
