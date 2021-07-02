/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.models.Restaurant
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.display.samples.dualview.ui.theme.selectedBody1
import com.microsoft.device.display.samples.dualview.ui.theme.selectedBody2
import com.microsoft.device.display.samples.dualview.ui.theme.typography
import com.microsoft.device.display.samples.dualview.utils.formatPriceRange
import com.microsoft.device.display.samples.dualview.utils.formatRating

const val outlinePadding = 25
const val narrowWidth = 1100

@Composable
fun RestaurantsView(modifier: Modifier, navController: NavController?, appStateViewModel: AppStateViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = outlinePadding.dp,
                start = outlinePadding.dp,
                end = outlinePadding.dp
            )
            .then(modifier)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.list_title),
                style = typography.subtitle1
            )
            RestaurantListView(navController, appStateViewModel)
        }
    }
}

@Composable
fun RestaurantListView(navController: NavController?, appStateViewModel: AppStateViewModel) {
    val selectionLiveData = appStateViewModel.selectionLiveData
    val selectedIndex = selectionLiveData.observeAsState(initial = -1).value

    val viewWidth = appStateViewModel.viewWidth
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
                    enabled = true,
                    onClick = {
                        appStateViewModel.selectionLiveData.value = index
                        navController?.navigate("map")
                    }
                )
            )
        }
    }
}

@Composable
fun RestaurantTile(restaurant: Restaurant, isSelected: Boolean, isSmallScreen: Boolean, modifier: Modifier) {
    val columnModifier = if (isSmallScreen) Modifier.fillMaxHeight().horizontalScroll(rememberScrollState()) else Modifier.fillMaxHeight()
    val smallArrangement = if (isSmallScreen) Arrangement.spacedBy(5.dp) else Arrangement.SpaceBetween

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageView(
            imageId = restaurant.imageResourceId,
            modifier = Modifier
                .requiredWidth(140.dp)
                .wrapContentHeight()
        )
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            restaurant.title?.let {
                Text(
                    text = it,
                    style = typography.subtitle2
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = smallArrangement
            ) {
                Text(
                    text = formatRating(restaurant.rating, restaurant.voteCount),
                    style = if (isSelected) { selectedBody1 } else { typography.body1 }
                )
                Text(
                    text = stringResource(R.string.restaurant_type, restaurant.cuisine.toString()),
                    style = if (isSelected) { selectedBody1 } else { typography.body1 }
                )
                Text(
                    text = formatPriceRange(restaurant.priceRange),
                    style = if (isSelected) { selectedBody1 } else { typography.body1 }
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            restaurant.description?.let {
                Text(
                    text = it,
                    style = if (isSelected) { selectedBody2 } else { typography.body2 }
                )
            }
        }
    }
}
