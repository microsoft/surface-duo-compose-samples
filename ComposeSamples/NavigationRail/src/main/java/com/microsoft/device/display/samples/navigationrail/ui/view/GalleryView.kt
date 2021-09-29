/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

@ExperimentalFoundationApi
@Composable
fun GalleryOrItemView(galleryName: String, imageLiveData: MutableLiveData<Image>, isDualPortrait: Boolean) {
    if (!isDualPortrait && imageLiveData.value != null) {
        navigateToPane2()
    } else {
        GalleryView(
            galleryName = galleryName,
            imageLiveData = imageLiveData,
            isDualPortrait = isDualPortrait,
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun GalleryView(galleryName: String, imageLiveData: MutableLiveData<Image>, isDualPortrait: Boolean) {
    val list = when (galleryName) {
        stringResource(R.string.plants) -> DataProvider.plantList
        stringResource(R.string.birds) -> DataProvider.birdList
        stringResource(R.string.animals) -> DataProvider.animalList
        stringResource(R.string.rocks) -> DataProvider.rockList
        stringResource(R.string.lakes) -> DataProvider.lakeList
        else -> throw IllegalArgumentException("unknown gallery category")
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 200.dp), // TODO: change size when images are chosen
    ) {
        items(list) { item ->
            GalleryItem(item, imageLiveData, isDualPortrait)
        }
    }
}

@Composable
fun GalleryItem(image: Image, imageLiveData: MutableLiveData<Image>, isDualPortrait: Boolean) {
    Image(
        painterResource(id = image.image),
        contentDescription = image.description,
        modifier = Modifier.selectable(
            selected = imageLiveData.value?.id == image.id,
            onClick = {
                // REVISIT: does this need to be copied another way (data class copy?)
                imageLiveData.value = image

                // If single screen, navigate to item view in this pane
                if (!isDualPortrait) {
                    navigateToPane2()
                }
            }
        )
    )
}
