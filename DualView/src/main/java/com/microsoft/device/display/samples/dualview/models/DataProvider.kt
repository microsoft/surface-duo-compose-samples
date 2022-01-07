/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.models

import androidx.annotation.StringRes
import com.microsoft.device.display.samples.dualview.R

data class Restaurant(
    @StringRes val title: Int,
    val imageResourceId: Int = 0,
    val rating: Double = 0.0,
    val voteCount: Int = 0,
    val cuisine: CuisineType,
    val priceRange: Int = 0,
    @StringRes val description: Int,
    val mapImageResourceId: Int = 0
) {
    enum class CuisineType(@StringRes val label: Int) {
        American(R.string.american),
        Italian(R.string.italian),
        Thai(R.string.thai),
        Korean(R.string.korean),
        FineDine(R.string.fine_dining),
        Breakfast(R.string.breakfast)
    }
}

val restaurants: List<Restaurant> = listOf(
    Restaurant(
        R.string.pestle_rock,
        R.drawable.pestle_rock_image,
        4.4,
        2303,
        Restaurant.CuisineType.Thai,
        3,
        R.string.pestle_rock_des,
        R.drawable.first_map
    ),
    Restaurant(
        R.string.sams_pizza,
        R.drawable.sams_pizza_image,
        4.9,
        1343,
        Restaurant.CuisineType.American,
        2,
        R.string.sams_pizza_des,
        R.drawable.second_map
    ),
    Restaurant(
        R.string.sizzle_and_crunch,
        R.drawable.sizzle_crunch_image,
        3.9,
        966,
        Restaurant.CuisineType.Thai,
        2,
        R.string.sizzle_and_crunch_des,
        R.drawable.third_map
    ),
    Restaurant(
        R.string.cantinetta,
        R.drawable.cantinetta_image,
        4.6,
        1322,
        Restaurant.CuisineType.Italian,
        4,
        R.string.cantinetta_des,
        R.drawable.fourth_map
    ),
    Restaurant(
        R.string.arayas_place,
        R.drawable.arayas_place_image,
        4.6,
        1322,
        Restaurant.CuisineType.Thai,
        2,
        R.string.arayas_place_des,
        R.drawable.fifth_map
    ),
    Restaurant(
        R.string.kimchi_bistro,
        R.drawable.kimchi_bistro_image,
        3.6,
        4565,
        Restaurant.CuisineType.Korean,
        4,
        R.string.kimchi_bistro_des,
        R.drawable.sixth_map
    ),
    Restaurant(
        R.string.topolopompo,
        R.drawable.topolopompo_image,
        4.5,
        6001,
        Restaurant.CuisineType.FineDine,
        3,
        R.string.topolopompo_des,
        R.drawable.seventh_map
    ),
    Restaurant(
        R.string.morsel,
        R.drawable.morsel_image,
        4.7,
        787,
        Restaurant.CuisineType.Breakfast,
        3,
        R.string.morsel_des,
        R.drawable.eighth_map
    )
)
