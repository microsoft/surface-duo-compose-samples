/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

/**
 * Show the GalleryView when in dual portrait mode or when no item has been selected,
 * otherwise show the ItemDetailView of the selected item
 */
@ExperimentalFoundationApi
@Composable
fun GalleryOrItemView(galleryList: List<Image>, currentImageId: Int?, onImageSelected: (Int) -> Unit, showItemView: Boolean) {
    if (showItemView) {
        navigateToPane2()
    } else {
        GalleryView(galleryList, currentImageId, onImageSelected)
    }
}

@ExperimentalFoundationApi
@Composable
fun GalleryView(galleryList: List<Image>, currentImageId: Int?, onImageClick: (Int) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 200.dp), // TODO: change size when images are chosen
    ) {
        items(galleryList) { item ->
            GalleryItem(item, currentImageId, onImageClick)
        }
    }
}

@Composable
fun GalleryItem(image: Image, currentImageId: Int?, onImageSelected: (Int) -> Unit) {
    Image(
        painterResource(id = image.image),
        contentDescription = image.description,
        modifier = Modifier.selectable(
            onClick = { onImageSelected(image.id) },
            selected = image.id == currentImageId,
        ).then(if (image.id == currentImageId) Modifier.border(7.dp, MaterialTheme.colors.error) else Modifier)
    )
}
