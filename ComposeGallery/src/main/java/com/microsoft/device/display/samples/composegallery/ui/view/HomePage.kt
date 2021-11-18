/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery.ui.view

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.composegallery.R
import com.microsoft.device.display.samples.composegallery.WindowSizeClass
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

@Composable
fun ComposeGalleryApp(isAppSpanned: Boolean, widthSizeClass: WindowSizeClass) {
    // Check if app should be in dual mode
    val isLargeScreen = widthSizeClass != WindowSizeClass.Compact
    val isDualMode = isAppSpanned || isLargeScreen

    // Get relevant image data for the panes
    val models = DataProvider.imageModels

    // Remember app state variables
    val lazyListState = rememberLazyListState()
    var selectedImageIndex by remember { mutableStateOf(0) }
    val updateImageIndex: (Int) -> Unit = { index -> selectedImageIndex = index }

    TwoPaneLayout(
        pane1 = {
            ListPane(models, isDualMode, selectedImageIndex, updateImageIndex, lazyListState)
        },
        pane2 = { DetailPane(models, isDualMode, selectedImageIndex) }
    )
}

@Composable
fun ComposeGalleryTopAppBar(actions: @Composable () -> Unit, title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp,
        actions = { actions() },
        modifier = Modifier.testTag(stringResource(R.string.top_app_bar))
    )
}