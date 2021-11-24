/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.composegallery.R
import com.microsoft.device.display.samples.composegallery.models.ImageModel
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1

@Composable
fun DetailPane(models: List<ImageModel>, isDualMode: Boolean, selectedIndex: Int) {
    Scaffold(
        topBar = {
            ComposeGalleryTopAppBar(
                actions = { if (!isDualMode) DetailActions() },
                title = if (!isDualMode) stringResource(R.string.app_name) else ""
            )
        }
    ) {
        GalleryItemDetail(models = models, selectedIndex = selectedIndex)
    }
}

@Composable
private fun DetailActions() {
    IconButton(onClick = { navigateToPane1() }) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_view_list_24),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(R.string.switch_to_list),
        )
    }
}

@Composable
private fun GalleryItemDetail(models: List<ImageModel>, selectedIndex: Int) {
    val selectedImageModel = models[selectedIndex]

    Crossfade(
        targetState = selectedImageModel,
        animationSpec = tween(600)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            ItemTitle(item = it)
            ItemImage(item = it)
        }
    }
}

@Composable
private fun ItemTitle(item: ImageModel) {
    Text(
        text = item.id,
        style = TextStyle(
            fontSize = 50.sp,
            color = MaterialTheme.colors.onSurface,
        )
    )
}

@Composable
private fun ItemImage(item: ImageModel) {
    Image(
        painter = painterResource(id = item.image),
        contentDescription = item.contentDescription,
    )
}
