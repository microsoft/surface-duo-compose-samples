/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.Image

private val BORDER_SIZE = 7.dp
private val GALLERY_SPACING = 2.dp
private const val NUM_COLUMNS = 2

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
    horizontalPadding: Dp
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count = NUM_COLUMNS),
        verticalArrangement = Arrangement.spacedBy(GALLERY_SPACING, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(GALLERY_SPACING, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(
            start = horizontalPadding,
            end = horizontalPadding,
            bottom = GALLERY_SPACING
        )
    ) {
        items(galleryList) { item ->
            GalleryItem(item, currentImageId, onImageSelected)
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
