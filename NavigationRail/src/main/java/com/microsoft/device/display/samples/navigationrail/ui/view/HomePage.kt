/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayoutNav
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable
import com.microsoft.device.dualscreen.windowstate.WindowState

private val GALLERY_HORIZ_PADDING = 16.dp

@Composable
fun NavigationRailApp(windowState: WindowState) {
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
        updateImageId = updateImageId
    )
}

@Composable
fun NavigationRailAppContent(
    isDualScreen: Boolean,
    isDualPortrait: Boolean,
    isDualLandscape: Boolean,
    foldIsOccluding: Boolean,
    foldBoundsDp: DpRect,
    windowHeight: Dp,
    imageId: Int?,
    updateImageId: (Int?) -> Unit
) {
    val navController = rememberNavController()

    TwoPaneLayoutNav(
        paneMode = TwoPaneMode.HorizontalSingle,
        navController = navController,
        singlePaneStartDestination = GallerySections.PLANTS.route,
        pane1StartDestination = GallerySections.PLANTS.route,
        pane2StartDestination = ITEM_DETAIL_ROUTE
    ) {
        navDestinations.map { section ->
            composable(section.route) {
                GalleryViewWithTopBar(
                    section = section,
                    horizontalPadding = GALLERY_HORIZ_PADDING,
                    navController = navController,
                    isDualScreen = isDualScreen,
                    imageId = imageId,
                    updateImageId = updateImageId
                )
            }
        }
        composable(ITEM_DETAIL_ROUTE) {
            ItemDetailViewWithTopBar(
                isDualPortrait = isDualPortrait,
                isDualLandscape = isDualLandscape,
                foldIsOccluding = foldIsOccluding,
                foldBoundsDp = foldBoundsDp,
                windowHeight = windowHeight,
                imageId = imageId,
                updateImageId = updateImageId,
                navController = navController
            )
        }
    }
}
