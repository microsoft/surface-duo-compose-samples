/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.ui.theme.ComposeSamplesTheme

private const val MESSAGE_WIDTH_PERCENT = 0.85f
private val MESSAGE_TOP_PADDING = 50.dp

/**
 * Shows the placeholder image and message for the current gallery
 *
 * @param gallerySection: current gallery
 */
@Composable
fun PlaceholderView(gallerySection: GallerySections?) {
    gallerySection?.let { gallery ->
        // Get placeholder image and message for current gallery
        val drawable = gallery.placeholderImage
        val message = stringResource(R.string.placeholder_msg, gallery.route)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            PlaceholderImage(drawable, message)
            PlaceholderMessage(message)
        }
    }
}

@Composable
private fun PlaceholderImage(@DrawableRes drawable: Int, description: String) {
    Image(
        painter = painterResource(id = drawable),
        contentDescription = description,
    )
}

@Composable
private fun PlaceholderMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth(MESSAGE_WIDTH_PERCENT)
            .padding(top = MESSAGE_TOP_PADDING),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h3,
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Plants Dark Mode"
)
@Composable
private fun PreviewPlantsDarkMode() {
    ComposeSamplesTheme {
        PlaceholderView(gallerySection = navDestinations[0])
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Plants Light Mode"
)
@Composable
private fun PreviewPlantsLightMode() {
    ComposeSamplesTheme {
        PlaceholderView(gallerySection = navDestinations[0])
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Animals Dark Mode"
)
@Composable
private fun PreviewAnimalsDarkMode() {
    ComposeSamplesTheme {
        PlaceholderView(gallerySection = navDestinations[2])
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Animals Light Mode"
)
@Composable
private fun PreviewAnimalsLightMode() {
    ComposeSamplesTheme {
        PlaceholderView(gallerySection = navDestinations[2])
    }
}
