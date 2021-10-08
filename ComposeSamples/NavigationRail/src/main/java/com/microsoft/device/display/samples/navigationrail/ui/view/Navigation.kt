/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.display.samples.navigationrail.ui.components.GalleryBottomNav
import com.microsoft.device.display.samples.navigationrail.ui.components.GalleryNavRail
import com.microsoft.device.display.samples.navigationrail.ui.components.GalleryTopBar
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2
import java.lang.NullPointerException

// Dp values for UI design
private val GALLERY_HORIZ_PADDING = 16.dp

// Nav destinations for app
val navDestinations = GallerySections.values()

enum class GallerySections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String,
    val list: List<Image>,
    @DrawableRes val placeholderImage: Int,
) {
    PLANTS(
        R.string.plants,
        R.drawable.plant_icon,
        "plants",
        DataProvider.plantList,
        R.drawable.plants_placeholder
    ),
    BIRDS(
        R.string.birds,
        R.drawable.bird_icon,
        "birds",
        DataProvider.birdList,
        R.drawable.birds_placeholder
    ),
    ANIMALS(
        R.string.animals,
        R.drawable.animal_icon,
        "animals",
        DataProvider.animalList,
        R.drawable.animals_placeholder
    ),
    LAKES(
        R.string.lakes,
        R.drawable.lake_icon,
        "lakes",
        DataProvider.lakeList,
        R.drawable.lakes_placeholder
    ),
    ROCKS(
        R.string.rocks,
        R.drawable.rock_icon,
        "rocks",
        DataProvider.rockList,
        R.drawable.rocks_placeholder
    )
}

/**
 * Build nav graph with the different galleries as destinations
 */
@ExperimentalFoundationApi
fun NavGraphBuilder.addGalleryGraph(
    currentImageId: Int?,
    onImageSelected: (Int) -> Unit,
    showItemView: Boolean,
    horizontalPadding: Dp
) {
    navDestinations.forEach { section ->
        composable(section.route) {
            Scaffold(
                topBar = { GalleryTopBar(section.route, horizontalPadding) }
            ) {
                GalleryOrItemView(
                    galleryList = section.list,
                    currentImageId = currentImageId,
                    onImageSelected = { id -> onImageSelected(id) },
                    showItemView = showItemView,
                    horizontalPadding = horizontalPadding,
                )
            }
        }
    }
}

/**
 * Show the NavHost with the gallery composables, surrounded by a top bar and the appropriate nav
 * component (BottomNavigation or NavigationRail)
 */
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShowWithNav(
    isDualScreen: Boolean,
    isDualPortrait: Boolean,
    imageId: Int?,
    updateImageId: (Int?) -> Unit,
    currentRoute: String,
    updateRoute: (String) -> Unit
) {
    val navController = rememberNavController()

    // Use navigation rail when dual screen (more space), otherwise use bottom navigation
    Scaffold(
        bottomBar = {
            if (!isDualScreen)
                GalleryBottomNav(navController, navDestinations, updateImageId, updateRoute)
        },
    ) { paddingValues ->
        Row(Modifier.padding(paddingValues)) {
            if (isDualScreen)
                GalleryNavRail(navController, navDestinations, updateImageId, updateRoute)
            NavHost(
                modifier = Modifier.onGloballyPositioned {
                    // Once layouts have been positioned, check that nav controller is at correct
                    // current route. If not, try to navigate to the current route (unless nav
                    // graph hasn't been created yet).
                    if (navController.currentDestination?.route != currentRoute) {
                        try {
                            navController.navigate(currentRoute)
                        } catch (e: NullPointerException) {
                            // Nav graph may be null if this is the first run through
                            Log.i("Navigation Rail Sample", "Caught the following exception: ${e.message}")
                        }
                    }
                },
                navController = navController,
                startDestination = currentRoute,
            ) {
                addGalleryGraph(
                    currentImageId = imageId,
                    onImageSelected = { id -> onImageSelected(id, updateImageId, isDualPortrait) },
                    showItemView = !isDualPortrait && imageId != null,
                    horizontalPadding = GALLERY_HORIZ_PADDING
                )
            }
        }
    }
}

/**
 * When an image in a gallery is selected, update the id of the currently selected image and
 * show the detail view of the item
 */
private fun onImageSelected(id: Int, updateImageId: (Int?) -> Unit, isDualPortrait: Boolean) {
    // Update image id
    updateImageId(id)

    // Navigate to ItemDetailView (pane 2) if not showing two panes
    if (!isDualPortrait) {
        navigateToPane2()
    }
}
