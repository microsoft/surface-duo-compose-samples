/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavOptionsBuilder
import com.microsoft.device.display.samples.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.models.Image

// Nav destinations for app
val navDestinations = GallerySections.values()
const val ITEM_DETAIL_ROUTE = "item detail"

val launchSingleTop: NavOptionsBuilder.() -> Unit = {
    launchSingleTop = true
}

enum class GallerySections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String,
    val list: List<Image>,
    @DrawableRes val placeholderImage: Int,
    @DrawableRes val fact1Icon: Int,
    @StringRes val fact1Description: Int,
    @DrawableRes val fact2Icon: Int? = null,
    @StringRes val fact2Description: Int? = null,
) {
    PLANTS(
        R.string.plants,
        R.drawable.plant_icon,
        "plants",
        DataProvider.plantList,
        R.drawable.plants_placeholder,
        R.drawable.sun_icon,
        R.string.sun,
        R.drawable.plant_height_icon,
        R.string.height
    ),
    BIRDS(
        R.string.birds,
        R.drawable.bird_icon,
        "birds",
        DataProvider.birdList,
        R.drawable.birds_placeholder,
        R.drawable.wingspan_icon,
        R.string.bird_size,
    ),
    ANIMALS(
        R.string.animals,
        R.drawable.animal_icon,
        "animals",
        DataProvider.animalList,
        R.drawable.animals_placeholder,
        R.drawable.animal_size_icon,
        R.string.animal_size,
    ),
    LAKES(
        R.string.lakes,
        R.drawable.lake_icon,
        "lakes",
        DataProvider.lakeList,
        R.drawable.lakes_placeholder,
        R.drawable.sea_level_icon,
        R.string.sea_level,
    ),
    ROCKS(
        R.string.rocks,
        R.drawable.rock_icon,
        "rocks",
        DataProvider.rockList,
        R.drawable.rocks_placeholder,
        R.drawable.chemical_constituents_icon,
        R.string.composition
    )
}
