/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery.ui.view

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.composegallery.R
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun ComposeGalleryApp(viewModel: AppStateViewModel, windowLayoutInfo: Flow<WindowLayoutInfo>) {
    var isAppSpanned by remember { mutableStateOf(false) }

    LaunchedEffect(windowLayoutInfo) {
        windowLayoutInfo.collect { newLayoutInfo ->
            isAppSpanned = newLayoutInfo.displayFeatures.isNotEmpty()
            Log.d("ComposeGalleryApp", "isAppSpanned = $isAppSpanned")
        }
    }

    ComposeGalleryAppContent(viewModel, isAppSpanned)
}

@Composable
fun ComposeGalleryAppContent(viewModel: AppStateViewModel, isAppSpanned: Boolean) {
    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualMode = isAppSpanned || isTablet

    val models = DataProvider.imageModels
    val imageSelectionLiveData = viewModel.imageSelectionLiveData
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
        actions = { actions() },
        modifier = Modifier.testTag(stringResource(R.string.top_app_bar))
    )
}
