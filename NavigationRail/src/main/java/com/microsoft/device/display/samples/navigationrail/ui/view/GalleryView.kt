/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.display.samples.navigationrail.ui.components.GalleryBottomNav
import com.microsoft.device.display.samples.navigationrail.ui.components.GalleryNavRail
import com.microsoft.device.display.samples.navigationrail.ui.components.GalleryTopBar
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneNavScope

private val BORDER_SIZE = 7.dp
private val GALLERY_SPACING = 2.dp
private const val NUM_COLUMNS = 2

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun TwoPaneNavScope.GalleryViewWithTopBar(
    section: GallerySections,
    horizontalPadding: Dp,
    navController: NavHostController,
    isDualScreen: Boolean,
    imageId: Int?,
    updateImageId: (Int?) -> Unit,
    currentRoute: String,
    updateRoute: (String) -> Unit
) {
    // Make sure the current gallery section should be displayed
    DisposableEffect(isSinglePane) {
        if (isSinglePane && imageId != null) {
            navController.navigateTo(ITEM_DETAIL_ROUTE, Screen.Pane2, defaultNavOptions)
        } else if (section.route != currentRoute) {
            navController.navigateTo(currentRoute, screen = Screen.Pane1, defaultNavOptions)
        }

        // REVISIT: check if another effect would fit this use case better
        onDispose { }
    }

    val activity = (LocalContext.current as? Activity)
    BackHandler(enabled = isSinglePane) {
        // Update gallery route to previous back stack entry and navigate up
        navController.previousBackStackEntry?.destination?.route?.let { updateRoute(it) }
        val success = navController.navigateUp()

        // If navigate up fails, we're at the start destination of the graph, so exit the activity
        if (!success)
            activity?.finish()
    }

    // Use navigation rail when dual screen (more space), otherwise use bottom navigation
    Scaffold(
        bottomBar = {
            if (!isDualScreen)
                GalleryBottomNav(navController, navDestinations, updateImageId, updateRoute)
        },
    ) { paddingValues ->
        Row(Modifier.padding(paddingValues)) {
            if (isDualScreen)
                this@GalleryViewWithTopBar.GalleryNavRail(navController, navDestinations, updateImageId, updateRoute)
            Scaffold(
                topBar = { GalleryTopBar(section.route, horizontalPadding) }
            ) { paddingValues ->
                GalleryView(
                    galleryList = section.list,
                    currentImageId = imageId,
                    onImageSelected = { id -> this@GalleryViewWithTopBar.onImageSelected(id, updateImageId, navController) },
                    horizontalPadding = horizontalPadding,
                    paddingValues = paddingValues
                )
            }
        }
    }
}

/**
 * When an image in a gallery is selected, update the id of the currently selected image and
 * show the detail view of the item
 */
private fun TwoPaneNavScope.onImageSelected(
    id: Int,
    updateImageId: (Int?) -> Unit,
    navController: NavHostController
) {
    // Update image id
    updateImageId(id)

    // Navigate to ItemDetailView if not showing two panes
    if (isSinglePane)
        navController.navigateTo(ITEM_DETAIL_ROUTE, Screen.Pane2, defaultNavOptions)
}

/**
 * Show a grid with all of the items in the current gallery
 *
 * @param galleryList: list of the images to show in the gallery
 * @param currentImageId: id of the currently selected image
 * @param onImageSelected: action to perform when a gallery item/image is selected
 * @param horizontalPadding: amount of horizontal padding to put around the gallery grid
 */
@ExperimentalFoundationApi
@Composable
fun GalleryView(
    galleryList: List<Image>,
    currentImageId: Int?,
    onImageSelected: (Int) -> Unit,
    horizontalPadding: Dp,
    paddingValues: PaddingValues
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Fixed(count = NUM_COLUMNS),
        verticalArrangement = Arrangement.spacedBy(GALLERY_SPACING, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(GALLERY_SPACING, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(
            start = horizontalPadding,
            end = horizontalPadding,
            bottom = GALLERY_SPACING
        )
    ) {
        galleryList.forEach { item ->
            item {
                GalleryItem(item, currentImageId, onImageSelected)
            }
        }
    }
}

/**
 * Show the visual representation of a gallery item, and show a colored border around the item
 * if it is selected
 *
 * @param image: image associated with the item
 * @param currentImageId: id of the currently selected image
 * @param onImageSelected: action to perform when the item is selected
 */
@ExperimentalFoundationApi
@Composable
fun GalleryItem(image: Image, currentImageId: Int?, onImageSelected: (Int) -> Unit) {
    Image(
        painterResource(id = image.image),
        contentDescription = stringResource(R.string.image_description, image.name, image.id),
        modifier = Modifier
            .selectable(
                onClick = { onImageSelected(image.id) },
                selected = image.id == currentImageId,
                role = Role.Button
            )
            .then(
                if (image.id == currentImageId)
                    Modifier.border(BORDER_SIZE, MaterialTheme.colors.error)
                else
                    Modifier
            ),
        contentScale = ContentScale.FillWidth
    )
}
