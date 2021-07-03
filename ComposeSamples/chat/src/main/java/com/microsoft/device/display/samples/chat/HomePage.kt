package com.microsoft.device.display.samples.chat

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.microsoft.device.display.samples.chat.view.DualScreenUI
import com.microsoft.device.display.samples.chat.view.SingleScreenUI
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel

@Composable
fun SetupUI() {

    val appStateViewModel = hiltViewModel<AppStateViewModel>()
    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value

    if (isDualMode) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }

    BackHandler(appStateViewModel.displayChatDetails && !isDualMode) {
        appStateViewModel.displayChatDetails = false
    }
}
