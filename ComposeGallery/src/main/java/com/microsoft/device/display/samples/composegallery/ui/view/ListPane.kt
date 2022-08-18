/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.composegallery.R
import com.microsoft.device.display.samples.composegallery.models.ImageModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope

@Composable
fun TwoPaneScope.ListPane(
    models: List<ImageModel>,
    selectedImageIndex: Int,
    updateImageIndex: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            ComposeGalleryTopAppBar(
                actions = { if (isSinglePane) ListActions() },
                title = stringResource(R.string.app_name),
            )
        }
    ) {
        GalleryList(
            models = models,
            selectedImageIndex = selectedImageIndex,
            updateImageIndex = updateImageIndex,
            paddingValues = it
        )
    }
}

@Composable
private fun TwoPaneScope.ListActions() {
    IconButton(onClick = { navigateToPane2() }) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_photo_24),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(R.string.switch_to_detail),
        )
    }
}

@Composable
private fun TwoPaneScope.GalleryList(
    models: List<ImageModel>,
    selectedImageIndex: Int,
    updateImageIndex: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .testTag(stringResource(R.string.gallery_list)),
    ) {
        itemsIndexed(models) { index, item ->
            ListEntry(selectedImageIndex, updateImageIndex, index, item)
            Divider(color = MaterialTheme.colors.onSurface)
        }
    }
}

@Composable
fun TwoPaneScope.ListEntry(
    selectedImageIndex: Int,
    updateImageIndex: (Int) -> Unit,
    index: Int,
    item: ImageModel
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = (index == selectedImageIndex),
                onClick = {
                    updateImageIndex(index)
                    navigateToPane2()
                }
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListEntryImage(item)
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            ListEntryTitle(item.id)
            ListEntryDescription(item.description)
        }
    }
}

@Composable
private fun ListEntryImage(item: ImageModel) {
    Image(
        painter = painterResource(id = item.image),
        modifier = Modifier.size(width = 150.dp, height = 100.dp),
        contentDescription = item.contentDescription
    )
}

@Composable
private fun ListEntryTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentSize(Alignment.Center),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun ListEntryDescription(description: String) {
    Text(
        text = description,
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentSize(Alignment.Center)
    )
}
