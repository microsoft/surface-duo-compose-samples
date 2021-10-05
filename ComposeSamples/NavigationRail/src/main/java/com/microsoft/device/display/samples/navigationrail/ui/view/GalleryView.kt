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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

private val BORDER_SIZE = 7.dp
private val GALLERY_SPACING = 2.dp
private const val NUM_COLUMNS = 2

/**
 * Show the GalleryView when in dual portrait mode or when no item has been selected,
 * otherwise show the ItemDetailView of the selected item
 */
@ExperimentalFoundationApi
@Composable
fun GalleryOrItemView(galleryList: List<Image>, currentImageId: Int?, onImageSelected: (Int) -> Unit, showItemView: Boolean, horizontalPadding: Dp) {
    if (showItemView) {
        navigateToPane2()
    } else {
        GalleryView(galleryList, currentImageId, onImageSelected, horizontalPadding)
    }
}

@ExperimentalFoundationApi
@Composable
fun GalleryView(galleryList: List<Image>, currentImageId: Int?, onImageClick: (Int) -> Unit, horizontalPadding: Dp) {
    val lazyListState by remember { mutableStateOf(LazyListState()) }

    LazyVerticalGrid(
        cells = GridCells.Fixed(count = NUM_COLUMNS),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(GALLERY_SPACING, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(GALLERY_SPACING, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(start = horizontalPadding, end = horizontalPadding, bottom = GALLERY_SPACING)
    ) {
        items(galleryList) { item ->
            GalleryItem(item, currentImageId, onImageClick)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun GalleryItem(image: Image, currentImageId: Int?, onImageSelected: (Int) -> Unit) {
    Image(
        painterResource(id = image.image),
        contentDescription = image.description,
        modifier = Modifier
            .selectable(
                onClick = { onImageSelected(image.id) },
                selected = image.id == currentImageId,
            ).then(
                if (image.id == currentImageId)
                    Modifier.border(BORDER_SIZE, MaterialTheme.colors.error)
                else
                    Modifier
            ),
        contentScale = ContentScale.FillWidth
    )
}
