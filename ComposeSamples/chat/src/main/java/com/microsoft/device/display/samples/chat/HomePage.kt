package com.microsoft.device.display.samples.chat

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value

    if (isDualMode) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }
}

@Composable
fun DualScreenUI() {
    Text("Hello")
}

@Composable
fun SingleScreenUI() {
    Text("Hi")
}