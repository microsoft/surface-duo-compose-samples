/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.Restaurant
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.display.samples.dualview.ui.theme.selectedBody1
import com.microsoft.device.display.samples.dualview.ui.theme.selectedBody2
import com.microsoft.device.display.samples.dualview.ui.theme.typography
import com.microsoft.device.display.samples.dualview.utils.formatPriceRange
import com.microsoft.device.display.samples.dualview.utils.formatRating
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

const val outlinePadding = 25
const val narrowWidth = 1100
const val thumbnailWidth = 140
val TextStyleKey = SemanticsPropertyKey<TextStyle>("TextStyleKey")
var SemanticsPropertyReceiver.textStyle by TextStyleKey

@Composable
fun RestaurantViewWithTopBar(
    isDualScreen: Boolean,
    viewWidth: Int,
    selectedIndex: Int,
    onRestaurantClick: (Int) -> Unit
) {
    Scaffold(
        topBar = { RestaurantTopBar(isDualScreen) }
    ) {
        RestaurantView(viewWidth, selectedIndex, onRestaurantClick)
    }
}

@Composable
fun RestaurantTopBar(isDualScreen: Boolean) {
    TopAppBar(
        modifier = Modifier.testTag(stringResource(R.string.restaurant_top_bar)),
        actions = { if (!isDualScreen) RestaurantActionButton() },
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }
    )
}

@Composable
private fun RestaurantActionButton() {
    IconButton(onClick = { navigateToPane2() }) {
        Icon(
            painter = painterResource(R.drawable.ic_map),
            contentDescription = stringResource(R.string.switch_to_map),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun RestaurantView(viewWidth: Int, selectedIndex: Int, onRestaurantClick: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(top = outlinePadding.dp, start = outlinePadding.dp, end = outlinePadding.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = stringResource(R.string.list_title),
            style = typography.subtitle1
        )
        RestaurantListView(viewWidth, selectedIndex, onRestaurantClick)
    }
}

@Composable
fun RestaurantListView(viewWidth: Int, selectedIndex: Int, onRestaurantClick: (Int) -> Unit) {
    // REVISIT: use a WindowSizeClass check instead?
    val isSmallScreen = viewWidth < narrowWidth && viewWidth != 0

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(outlinePadding.dp),
        contentPadding = PaddingValues(bottom = outlinePadding.dp)
    ) {
        itemsIndexed(restaurants) { index, item ->
            val isSelected = index == selectedIndex
            RestaurantTile(
                restaurant = item,
                isSelected = isSelected,
                isSmallScreen = isSmallScreen,
                modifier = Modifier.selectable(
                    selected = isSelected,
                    onClick = { onRestaurantClick(index) }
                )
            )
        }
    }
}

@Composable
fun RestaurantTile(
    restaurant: Restaurant,
    isSelected: Boolean,
    isSmallScreen: Boolean,
    modifier: Modifier
) {
    val columnModifier = Modifier.fillMaxHeight()
    if (isSmallScreen)
        columnModifier.horizontalScroll(rememberScrollState())

    Row(
        modifier = modifier.wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RestaurantThumbnail(restaurant.imageResourceId, restaurant.title)
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            RestaurantTitle(restaurant.title)
            RestaurantStats(isSmallScreen, isSelected, restaurant)
            Spacer(modifier = Modifier.height(2.dp))
            RestaurantDescription(isSelected, restaurant.description)
        }
    }
}

@Composable
private fun RestaurantThumbnail(@DrawableRes imageId: Int, @StringRes contentDescription: Int) {
    Image(
        painter = painterResource(imageId),
        contentDescription = stringResource(contentDescription),
        modifier = Modifier
            .requiredWidth(thumbnailWidth.dp)
            .wrapContentHeight(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun RestaurantTitle(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        style = typography.subtitle2
    )
}

@Composable
private fun RestaurantStats(isSmallScreen: Boolean, isSelected: Boolean, restaurant: Restaurant) {
    val smallArrangement = if (isSmallScreen) Arrangement.spacedBy(5.dp) else Arrangement.SpaceBetween
    val textStyle = if (isSelected) selectedBody1 else typography.body1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { this.textStyle = textStyle },
        horizontalArrangement = smallArrangement
    ) {
        RestaurantRating(restaurant.rating, restaurant.voteCount, textStyle)
        RestaurantType(restaurant.cuisine, textStyle)
        RestaurantPriceRange(restaurant.priceRange, textStyle)
    }
}

@Composable
private fun RestaurantRating(rating: Double, votes: Int, textStyle: TextStyle) {
    Text(
        text = formatRating(rating, votes),
        style = textStyle
    )
}

@Composable
private fun RestaurantType(cuisineType: Restaurant.CuisineType, textStyle: TextStyle) {
    Text(
        text = stringResource(R.string.restaurant_type, stringResource(cuisineType.label)),
        style = textStyle
    )
}

@Composable
private fun RestaurantPriceRange(priceRange: Int, textStyle: TextStyle) {
    Text(
        text = formatPriceRange(priceRange),
        style = textStyle
    )
}

@Composable
private fun RestaurantDescription(isSelected: Boolean, @StringRes description: Int) {
    Text(
        text = stringResource(description),
        style = if (isSelected) selectedBody2 else typography.body2,
    )
}
