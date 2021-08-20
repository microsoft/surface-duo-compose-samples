/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.microsoft.device.display.samples.listdetail.models.AppStateViewModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel
    val isAppSpannedLiveData = appStateViewModel.isAppSpannedLiveData
    val isAppSpanned = isAppSpannedLiveData.observeAsState(initial = false).value
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val isDualScreen = isAppSpanned && !isPortrait
    DualScreenUI(isDualScreen)
}

@Composable
fun DualScreenUI(isDualScreen: Boolean) {
    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = { ListViewWithTopBar(appStateViewModel = appStateViewModel) },
        pane2 = { DetailViewWithTopBar(isAppSpanned = isDualScreen, appStateViewModel = appStateViewModel) }
    )
}

@Composable
fun ImageView(imageId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(imageId),
        contentDescription = null,
        modifier = modifier
    )
}
