package com.microsoft.device.display.samples.chat

import androidx.activity.compose.BackHandler
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.microsoft.device.display.samples.chat.view.DualScreenUI
import com.microsoft.device.display.samples.chat.view.SingleScreenUI

@Composable
fun SetupUI() {

    val appStateViewModel = hiltViewModel<AppStateViewModel>()

    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                backgroundColor = Color.White
            )
        },
        backgroundColor = Color(0xFFF8F8F8),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { }
            ) {
                Icon(Icons.Filled.Add, null)
            }
        }
    ) {
        if (isDualMode) {
            DualScreenUI()
        } else {
            SingleScreenUI()
        }
    }

    BackHandler(appStateViewModel.displayChatDetails && !isDualMode) {
        appStateViewModel.displayChatDetails = false
    }
}
