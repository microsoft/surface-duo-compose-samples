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
import com.example.navigationrail.models.Image

@ExperimentalFoundationApi
@Composable
fun Gallery(list: List<Image>, imageLiveData: MutableLiveData<Image>) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 256.dp), // TODO: change size when images are chosen
    ) {
        items(list) { item ->
            GalleryItem(item, imageLiveData)
        }
    }
}

@Composable
fun GalleryItem(image: Image, imageLiveData: MutableLiveData<Image>) {
    Image(
        painterResource(id = image.id),
        contentDescription = image.description,
        modifier = Modifier.selectable(
            selected = imageLiveData.value?.id == image.id,
            onClick = {
                // REVISIT: does this need to be copied another way (data class copy?)
                imageLiveData.value = image
            }
        )
    )
}
