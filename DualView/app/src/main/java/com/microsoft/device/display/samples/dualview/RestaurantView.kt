package com.microsoft.device.display.samples.dualview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.microsoft.device.display.samples.contentcontext.util.formatPriceRange
import com.microsoft.device.display.samples.contentcontext.util.formatRating
import com.microsoft.device.display.samples.dualview.ImageView
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.models.Restaurant
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.display.samples.dualview.ui.theme.typography

private val outlinePadding = 25.dp

@Composable
fun RestaurantView(navController: NavController, appStateViewModel: AppStateViewModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(
            top = outlinePadding,
            start = outlinePadding,
            end = outlinePadding
        )
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = stringResource(id = R.string.list_title),
                 style = typography.subtitle1
            )
            RestaurantListView(navController, appStateViewModel)
            Spacer(modifier = Modifier.preferredHeight(10.dp))
        }
    }
}

@Composable
fun RestaurantListView(navController: NavController, appStateViewModel: AppStateViewModel) {
    val restaurants = restaurants
    val selectionLiveData = appStateViewModel.getSelectionLiveData()
    val selectedIndex = selectionLiveData.observeAsState(initial = -1).value

    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
        itemsIndexed(restaurants) { index, item ->
            RestaurantTile(restaurant = item,
                           modifier = Modifier.selectable(
                               selected = (index == selectedIndex),
                               onClick = {
                                   appStateViewModel.setSelectionLiveData(index)
                                   navController.navigate("map")
                               }
                           )
            )
        }
    }
}

@Composable
fun RestaurantTile(restaurant: Restaurant, modifier: Modifier) {
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageView(
            imageId = restaurant.imageResourceId,
            modifier = Modifier
                .width(140.dp)
                .wrapContentHeight()
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            restaurant.title?.let { Text(
                text = it,
                style = typography.subtitle2
            ) }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = formatRating(restaurant.rating, restaurant.voteCount),
                     style = typography.body1
                )
                Text(text = stringResource(R.string.restaurant_type, restaurant.cuisine.toString()),
                     style = typography.body1
                )
                Text(text = formatPriceRange(restaurant.priceRange),
                     style = typography.body1
                )
            }
            Spacer(modifier = Modifier.preferredHeight(2.dp))
            restaurant.description?.let {
                Text(
                    text = it,
                    style = typography.body2
            )  }
        }
    }
}

