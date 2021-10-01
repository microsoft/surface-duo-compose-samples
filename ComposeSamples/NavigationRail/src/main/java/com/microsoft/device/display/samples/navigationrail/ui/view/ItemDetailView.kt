/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.Image

/**
 * Show the image and details for the selected gallery item. If no item is selected, show
 * a message explaining how to open the detail view.
 */
@Composable
fun ItemDetailView(selectedImage: Image? = null, currentRoute: String) {
    // If no images are selected, show "select image" message
    if (selectedImage == null) {
        PlaceholderImageMessage(currentRoute)
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
    ) {
        ItemImage(selectedImage)
        ItemDetails(selectedImage)
    }
}

@Composable
private fun PlaceholderImageMessage(currentRoute: String) {
    // Find current gallery
    val gallerySection = navDestinations.find { it.route == currentRoute }

    gallerySection?.let { gallery ->
        // Get placeholder image and message for current gallery
        val drawable = gallery.placeholderImage
        val message = stringResource(R.string.placeholder_msg, gallery.route)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            PlaceholderImage(drawable, message)
            PlaceholderMessage(message)
        }
    }
}

@Composable
private fun PlaceholderImage(drawable: Int, description: String) {
    Image(
        painter = painterResource(id = drawable),
        contentDescription = description,
    )
}

@Composable
private fun PlaceholderMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth(0.85f).padding(top = 50.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h3,
    )
}

@Composable
private fun ItemImage(image: Image) {
    Image(
        painter = painterResource(id = image.image),
        contentDescription = (image.description),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f) // REVISIT: put logic here for keeping image on top screen
            .border(5.dp, Color.Magenta),
    )
}

@Composable
private fun ItemDetails(image: Image) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        contentPadding = PaddingValues(bottom = 20.dp),
    ) {
        this.item {
            Text(
                text = stringResource(id = R.string.date_found, image.date),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Visible,
            )
            Text(
                text = stringResource(id = R.string.observation_details, image.details),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxSize(),
                overflow = TextOverflow.Visible,
            )
        }
    }
}
