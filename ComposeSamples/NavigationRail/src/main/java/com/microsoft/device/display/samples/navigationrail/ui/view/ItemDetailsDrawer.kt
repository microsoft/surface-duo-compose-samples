/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.display.samples.navigationrail.ui.components.ContentDrawer
import com.microsoft.device.display.samples.navigationrail.ui.components.InfoBox

private lateinit var BodyTextStyle: TextStyle
private lateinit var SubtitleTextStyle: TextStyle
private const val EXPANDED_HEIGHT_2PANE = 0.7f
private const val EXPANDED_HEIGHT_1PANE = 0.55f
private const val COLLAPSED_HEIGHT_2PANE = 0.4f
private const val COLLAPSED_HEIGHT_1PANE = 0.28f
private val PILL_TOP_PADDING = 8.dp
private val NAME_TOP_PADDING = 8.dp
private val LOCATION_TOP_PADDING = 10.dp
private val LOCATION_BETWEEN_PADDING = 5.dp
private val CONDITIONS_TOP_PADDING = 15.dp
private val LONG_DETAILS_TOP_PADDING = 35.dp
private val LONG_DETAILS_BOTTOM_PADDING = 10.dp
private const val LONG_DETAILS_LINE_HEIGHT = 32f

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun BoxWithConstraintsScope.ItemDetailsDrawer(
    modifier: Modifier,
    image: Image,
    isDualLandscape: Boolean,
    hingeSize: Dp,
    gallerySection: GallerySections?,
) {
    // Set max/min height for drawer based on orientation
    val expandedHeightPct = if (isDualLandscape) EXPANDED_HEIGHT_2PANE else EXPANDED_HEIGHT_1PANE
    val collapsedHeightPct = if (isDualLandscape) COLLAPSED_HEIGHT_2PANE else COLLAPSED_HEIGHT_1PANE
    val fullHeight = constraints.maxHeight.toFloat()
    val expandedHeight = with(LocalDensity.current) { (expandedHeightPct * fullHeight).toDp() }
    val collapsedHeight = with(LocalDensity.current) { (collapsedHeightPct * fullHeight).toDp() }

    // Set text size for drawer based on orientation
    if (isDualLandscape) {
        BodyTextStyle = MaterialTheme.typography.body2
        SubtitleTextStyle = MaterialTheme.typography.subtitle2
    } else {
        BodyTextStyle = MaterialTheme.typography.body1
        SubtitleTextStyle = MaterialTheme.typography.subtitle1
    }

    ContentDrawer(
        modifier = modifier,
        expandHeight = expandedHeight,
        collapseHeight = collapsedHeight,
        hingeOccludes = isDualLandscape,
        hingeSize = hingeSize,
        hiddenContent = { ItemDetailsLong(image.details) }
    ) {
        DrawerPill()
        ItemName(image.name)
        ItemLocation(image.location)
        ItemConditions(gallerySection, image.condition1, image.condition2)
    }
}

@Composable
private fun ColumnScope.DrawerPill() {
    Spacer(Modifier.height(PILL_TOP_PADDING))
    Icon(
        painter = painterResource(R.drawable.drawer_pill),
        contentDescription = stringResource(R.string.drawer_pill),
        tint = if (MaterialTheme.colors.isLight)
            MaterialTheme.colors.primary
        else
            MaterialTheme.colors.secondaryVariant,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}

@Composable
private fun ColumnScope.ItemName(name: String) {
    Spacer(Modifier.height(NAME_TOP_PADDING))
    Text(
        text = name,
        color = MaterialTheme.colors.onSurface,
        style = BodyTextStyle,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ColumnScope.ItemLocation(location: String) {
    Spacer(Modifier.height(LOCATION_TOP_PADDING))
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location_icon),
            contentDescription = stringResource(R.string.location),
            tint = MaterialTheme.colors.onSurface
        )
        Spacer(Modifier.width(LOCATION_BETWEEN_PADDING))
        Text(
            text = location,
            color = MaterialTheme.colors.onSurface,
            style = SubtitleTextStyle,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ColumnScope.ItemConditions(
    gallerySection: GallerySections?,
    first: String,
    second: String,
) {
    Spacer(Modifier.height(CONDITIONS_TOP_PADDING))
    gallerySection?.let {
        // TODO: choose icon/content description based on gallery
        InfoBox(
            icon1 = R.drawable.sun_icon,
            info1 = if (first == "") null else first,
            description1 = stringResource(R.string.sun),
            icon2 = R.drawable.plant_height_icon,
            info2 = if (second == "") null else second,
            description2 = stringResource(R.string.height),
            textStyle = SubtitleTextStyle
        )
    }
}

@ExperimentalUnitApi
@Composable
private fun ItemDetailsLong(details: String) {
    val scrollState = rememberScrollState()

    Spacer(Modifier.height(LONG_DETAILS_TOP_PADDING))
    Text(
        modifier = Modifier
            .padding(bottom = LONG_DETAILS_BOTTOM_PADDING)
            .verticalScroll(scrollState),
        text = details,
        color = MaterialTheme.colors.onSurface,
        style = BodyTextStyle,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Visible,
        lineHeight = TextUnit(LONG_DETAILS_LINE_HEIGHT, TextUnitType.Sp)
    )
}
