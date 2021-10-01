/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

// Nav destinations for app
val navDestinations = GallerySections.values()
enum class GallerySections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
    val list: List<Image>
) {
    PLANTS(R.string.plants, Icons.Filled.Favorite, "plants", DataProvider.plantList),
    BIRDS(R.string.birds, Icons.Filled.AccountCircle, "birds", DataProvider.birdList),
    ANIMALS(R.string.animals, Icons.Filled.Face, "animals", DataProvider.animalList),
    ROCKS(R.string.rocks, Icons.Filled.AddCircle, "rocks", DataProvider.rockList),
    LAKES(R.string.lakes, Icons.Filled.ArrowDropDown, "lakes", DataProvider.lakeList)
}

/**
 * Build nav graph with the different galleries as destinations
 */
@ExperimentalFoundationApi
fun NavGraphBuilder.addGalleryGraph(onImageSelected: (Int) -> Unit, showItemView: Boolean) {
    navDestinations.forEach { section ->
        composable(section.route) {
            GalleryOrItemView(
                galleryList = section.list,
                onImageClick = { id -> onImageSelected(id) },
                showItemView = showItemView,
            )
        }
    }
}

/**
 * Show the NavHost with the gallery composables, surrounded by a top bar and the appropriate nav
 * component (BottomNavigation or NavigationRail)
 */
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
    // Set up nav controller and nav graph
    val navController = rememberNavController()
    val navHost: @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = currentRoute,
        ) {
            addGalleryGraph(
                onImageSelected = { id -> onImageSelected(id, updateImageId, isDualPortrait) },
                showItemView = !isDualPortrait && imageId != null,
            )
        }
    }

    // Use navigation rail when dual screen (more space), otherwise use bottom navigation
    ShowWithTopBar(
        title = stringResource(R.string.app_name),
        bottomBar = {
            if (!isDualScreen)
                BottomNav(navDestinations, navController, updateImageId, updateRoute)
        },
    ) { mod ->
        Row(mod) {
            if (isDualScreen)
                NavRail(navDestinations, navController, updateImageId, updateRoute)
            navHost()
        }
    }

    // Check that nav controller is at correct current route
    // If not, try to navigate to the current route (unless nav graph hasn't been created yet)
    if (navController.currentDestination?.route != currentRoute) {
        try {
            navController.graph
            navController.navigate(currentRoute)
        } catch (e: IllegalStateException) {
            // Graph may be null if this is the first run through
            Log.i("Navigation Rail Sample", "Caught the following exception: ${e.message}")
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
