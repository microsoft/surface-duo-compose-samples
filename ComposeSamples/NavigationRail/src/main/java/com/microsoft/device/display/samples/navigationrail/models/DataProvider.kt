/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.models

import com.example.navigationrail.R

object DataProvider {
    val images: List<Image> = listOf(
        Image(
            id = 1,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "9/23",
            details = "discovery details\ndiscovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\n" +
                "discovery details\nend"
        ),
        Image(
            id = 2,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/26",
            details = "discovery details"
        ),
        Image(
            id = 3,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 4,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "3/11",
            details = "discovery details"
        ),
        Image(
            id = 5,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/12",
            details = "discovery details"
        ),
        Image(
            id = 6,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "9/23",
            details = "discovery details"
        ),
        Image(
            id = 7,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/26",
            details = "discovery details"
        ),
        Image(
            id = 8,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 9,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "3/11",
            details = "discovery details"
        ),
        Image(
            id = 10,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/12",
            details = "discovery details"
        ),
        Image(
            id = 11,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "9/23",
            details = "discovery details"
        ),
        Image(
            id = 12,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/26",
            details = "discovery details"
        ),
        Image(
            id = 13,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 14,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "3/11",
            details = "discovery details"
        ),
        Image(
            id = 15,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/12",
            details = "discovery details"
        ),
        Image(
            id = 16,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 17,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 18,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 19,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "3/11",
            details = "discovery details"
        ),
        Image(
            id = 20,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "6/12",
            details = "discovery details"
        ),
        Image(
            id = 21,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 22,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 23,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 24,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
        Image(
            id = 25,
            image = R.drawable.ic_launcher_foreground,
            description = "Description",
            date = "4/24",
            details = "discovery details"
        ),
    )

    val plantList = images.subList(0, 15)
    val birdList = images.subList(15, 17)
    val animalList = images.subList(17, 20)
    val rockList = images.subList(20, 21)
    val lakeList = images.subList(21, 25)

    fun getImage(imageId: Int) = images.find { it.id == imageId }
}
