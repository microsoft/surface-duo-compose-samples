/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery.ui.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

private lateinit var appStateViewModel: AppStateViewModel
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun ComposeGalleryApp(viewModel: AppStateViewModel, isAppSpanned: Boolean) {
    appStateViewModel = viewModel
    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualMode = isAppSpanned || isTablet

    val models = DataProvider.imageModels
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value

    TwoPaneLayout(
        pane1 = { ListPane(models, isDualMode, imageSelectionLiveData) },
        pane2 = { DetailPane(models, isDualMode, selectedIndex) }
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
        actions = { actions() }
    )
}
