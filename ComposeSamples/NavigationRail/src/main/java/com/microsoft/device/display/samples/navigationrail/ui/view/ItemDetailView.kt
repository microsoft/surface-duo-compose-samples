/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1

/**
 * Show the image and details for the selected gallery item. If no item is selected, show
 * a placeholder view explaining how to open the detail view.
 */
@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun ItemDetailView(
    isDualPortrait: Boolean,
    isDualLandscape: Boolean,
    hingeSize: Dp,
    selectedImage: Image? = null,
    currentRoute: String,
) {
    // Find current gallery
    val gallerySection = navDestinations.find { it.route == currentRoute }

    // If no images are selected, show "select image" message or navigate back to gallery view
    if (selectedImage == null) {
        if (isDualPortrait)
            PlaceholderView(gallerySection)
        else
            navigateToPane1()
        return
    }

    // Show the image at the top and the details drawer at the bottom
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ItemImage(Modifier.align(Alignment.TopCenter), selectedImage)
        ItemDetailsDrawer(
            modifier = Modifier.align(Alignment.BottomCenter),
            image = selectedImage,
            isDualLandscape = isDualLandscape,
            hingeSize = hingeSize,
            gallerySection = gallerySection,
        )
    }
}

@Composable
private fun ItemImage(modifier: Modifier, image: Image) {
    Image(
        painter = painterResource(id = image.image),
        contentDescription = (image.description),
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
    )
}
