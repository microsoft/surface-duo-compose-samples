/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.Image
import kotlin.math.roundToInt

private val ItemDetailsShape = RoundedCornerShape(
    topStartPercent = 7,
    topEndPercent = 7,
    bottomStartPercent = 0,
    bottomEndPercent = 0
)
private val ItemConditionsShape = RoundedCornerShape(percent = 20)

private enum class DetailsDrawerState {
    Collapsed, Expanded
}

/**
 * Show the image and details for the selected gallery item. If no item is selected, show
 * a message explaining how to open the detail view.
 */
@ExperimentalMaterialApi
@Composable
fun ItemDetailView(isDualPortrait: Boolean, selectedImage: Image? = null, currentRoute: String) {
    // If no images are selected, show "select image" message or navigate back to gallery view
    if (selectedImage == null) {
        if (isDualPortrait)
            PlaceholderImageMessage(currentRoute)
        else
            navigateToPane1()
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ItemImage(Modifier.align(Alignment.TopCenter), selectedImage)
        ItemDetailsDrawer(Modifier.align(Alignment.BottomCenter), selectedImage)
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
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(top = 50.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h3,
    )
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

@ExperimentalMaterialApi
@Composable
private fun ItemDetailsDrawer(modifier: Modifier, image: Image) {
    val swipeableState = rememberSwipeableState(initialValue = DetailsDrawerState.Expanded)
    val collapsedPx = with(LocalDensity.current) { 210.dp.toPx() }
    val anchors =
        mapOf(collapsedPx to DetailsDrawerState.Collapsed, 0f to DetailsDrawerState.Expanded)

    Box(
        modifier = modifier
            .padding(top = 300.dp)
            .fillMaxSize()
            .swipeable(swipeableState, anchors, Orientation.Vertical),
        contentAlignment = Alignment.CenterStart,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                .clip(ItemDetailsShape)
                .background(MaterialTheme.colors.surface),
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 35.dp)
                    .fillMaxWidth(),
            ) {
                DrawerPill()
                ItemTitle(title = image.description)
                ItemLocation() // REVISIT: add fields to data provider
                ItemConditions()
                ItemDetailsLong()
            }
        }
    }
}

@Composable
private fun ColumnScope.DrawerPill() {
    Icon(
        painter = painterResource(R.drawable.drawer_pill),
        contentDescription = stringResource(R.string.drawer_pill),
        tint = if (MaterialTheme.colors.isLight)
            MaterialTheme.colors.primary
        else
            MaterialTheme.colors.secondaryVariant,
        modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
    )
}

@Composable
private fun ItemTitle(title: String) {
    Text(
        modifier = Modifier.padding(top = 8.dp), // REVISIT (check padding from other elements too)
        text = title,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ItemLocation(location: String = "Southern Asia") {
    Row(
        modifier = Modifier.padding(top = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location_icon),
            contentDescription = stringResource(R.string.location),
            tint = MaterialTheme.colors.onSurface
        )
        Text(
            text = location,
            modifier = Modifier.padding(start = 5.dp),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ItemConditions(
    first: String = "medium indirect filtered light",
    second: String = "1.3 metres high"
) {
    val endSpacing = 15.dp
    val betweenSpacing = 5.dp

    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .height(60.dp)
            .background(MaterialTheme.colors.secondaryVariant, ItemConditionsShape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        Spacer(Modifier.padding(start = endSpacing))
        Icon(
            painter = painterResource(id = R.drawable.sun_icon),
            contentDescription = stringResource(R.string.location),
            tint = MaterialTheme.colors.onSurface
        )
        Text(
            text = first,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.padding(betweenSpacing))
        Icon(
            painter = painterResource(id = R.drawable.plant_height_icon),
            contentDescription = stringResource(R.string.location),
            tint = MaterialTheme.colors.onSurface
        )
        Text(
            text = second,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.padding(end = endSpacing))
    }
}

@Composable
private fun ItemDetailsLong(details: String = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters. Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters. ") {
    val scrollState = rememberScrollState()

    Text(
        modifier = Modifier
            .padding(top = 35.dp, bottom = 10.dp)
            .verticalScroll(scrollState),
        text = details,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Visible
    )
}
