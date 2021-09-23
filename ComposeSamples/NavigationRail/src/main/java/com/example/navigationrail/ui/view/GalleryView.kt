/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.navigationrail.models.DataProvider
import com.example.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

@ExperimentalFoundationApi
@Composable
fun Gallery(galleryName: String, imageLiveData: MutableLiveData<Image>, isDualScreen: Boolean) {
    val list = when (galleryName) {
        // REVISIT: create enum or something so strings aren't hard-coded here (or data structure)
        "plants" -> DataProvider.plantList
        "birds" -> DataProvider.birdList
        "animals" -> DataProvider.animalList
        "rocks" -> DataProvider.rockList
        "lakes" -> DataProvider.lakeList
        else -> throw IllegalArgumentException("unknown gallery category")
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 200.dp), // TODO: change size when images are chosen
    ) {
        items(list) { item ->
            GalleryItem(item, imageLiveData, isDualScreen)
        }
    }
}

@Composable
fun GalleryItem(image: Image, imageLiveData: MutableLiveData<Image>, isDualScreen: Boolean) {
    Image(
        painterResource(id = image.id),
        contentDescription = image.description,
        modifier = Modifier.selectable(
            selected = imageLiveData.value?.id == image.id,
            onClick = {
                // REVISIT: does this need to be copied another way (data class copy?)
                imageLiveData.value = image

                if (!isDualScreen) {
                    navigateToPane2()
                }
            }
        )
    )
}
