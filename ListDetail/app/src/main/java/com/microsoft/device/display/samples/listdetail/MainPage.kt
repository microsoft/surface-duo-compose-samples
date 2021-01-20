package com.microsoft.device.display.samples.listdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.microsoft.device.display.samples.companionpane.viewModels.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel
    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

}

