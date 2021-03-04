/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.microsoft.device.display.samples.twopage.models.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

    val isScreenPortraitLiveData = appStateViewModel.getIsScreenPortraitLiveData()
    val isScreenPortrait = isScreenPortraitLiveData.observeAsState(initial = true).value

    if (isScreenSpanned && !isScreenPortrait) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }
}

@Composable
fun SingleScreenUI() {
    FirstPage()
}

@Composable
fun DualScreenUI() {


}