package com.microsoft.device.display.samples.dualview.models

import com.microsoft.device.display.samples.dualview.R

data class Restaurant (
    val title: String? = "",
    val imageResourceId: Int = 0,
    val rating: Double = 0.0,
    val voteCount: Int = 0,
    val cuisine: CuisineType,
    val priceRange: Int = 0,
    val description: String? = "",
    val mapImageResourceId: Int = 0
) {
    enum class CuisineType(private val label: String) {
        American("American"),
        Italian("Italian"),
        Thai("Thai"),
        Korean("Korean"),
        FineDine("Fine Dine"),
        Breakfast("Breakfast");

        override fun toString(): String {
            return label
        }
    }
}

val restaurants: List<Restaurant> = listOf(
    Restaurant(
        "Pestle Rock",
        R.drawable.pestle_rock_image,
        4.4,
        2303,
        Restaurant.CuisineType.Thai,
        3,
        "Wine bar with upscale small plates in a lofty modern space with a central wine tower & staircase.",
        R.drawable.first_map
    ),
    Restaurant(
        "Sam's Pizza",
        R.drawable.sams_pizza_image,
        4.9,
        1343,
        Restaurant.CuisineType.American,
        2,
        "Take-out/delivery chain offering classic & specialty pizzas, wings & breadsticks, plus desserts.",
        R.drawable.second_map
    ),
    Restaurant(
        "Sizzle and Crunch",
        R.drawable.sizzle_crunch_image,
        3.9,
        966,
        Restaurant.CuisineType.Thai,
        2,
        "Eatery with a wood-fired oven turning out European & NW dishes in a white-&-blue cottage-like room.",
        R.drawable.third_map
    ),
    Restaurant(
        "Cantinetta",
        R.drawable.cantinetta_image,
        4.6,
        1322,
        Restaurant.CuisineType.Italian,
        4,
        "Gourmet Neapolitan pies served in a lofty space with casual, industrial-chic decor.",
        R.drawable.fourth_map
    ),
    Restaurant(
        "Araya's Place",
        R.drawable.arayas_place_image,
        4.6,
        1322,
        Restaurant.CuisineType.Thai,
        2,
        "Araya's Place is the 1st vegan-Thai restaurant in the northwest while supporting local farms.",
        R.drawable.fifth_map
    ),
    Restaurant(
        "Kimchi Bistro",
        R.drawable.kimchi_bistro_image,
        3.6,
        4565,
        Restaurant.CuisineType.Korean,
        4,
        "Small, no frills Korean restaurant with an extensive menu served in simple digs inside a mall.",
        R.drawable.sixth_map
    ),
    Restaurant(
        "Topolopompo Restaurant",
        R.drawable.topolopompo_image,
        4.5,
        6001,
        Restaurant.CuisineType.FineDine,
        3,
        "Compact locale with counter service dishing up classic Mediterranean eats such as hummus & falafel.",
        R.drawable.seventh_map
    ),
    Restaurant(
        "Morsel",
        R.drawable.morsel_image,
        4.7,
        787,
        Restaurant.CuisineType.Breakfast,
        3,
        "Homey cafe with sofas, board games & quiet corners for gourmet coffee & craft biscuit sandwiches.",
        R.drawable.eighth_map
    )
)
