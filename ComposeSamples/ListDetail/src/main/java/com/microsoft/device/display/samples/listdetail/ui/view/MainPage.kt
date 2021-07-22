/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.display.samples.listdetail.R
import com.microsoft.device.display.samples.listdetail.models.AppStateViewModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isAppSpannedLiveData = appStateViewModel.isAppSpannedLiveData
    val isAppSpanned = isAppSpannedLiveData.observeAsState(initial = false).value

    if (isAppSpanned) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }
}

@Composable
fun SingleScreenUI() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            ListViewUnspanned(navController = navController, appStateViewModel = appStateViewModel)
        }
        composable("detail") {
            DetailViewUnspanned(navController = navController, appStateViewModel = appStateViewModel)
        }
    }
}

@Composable
fun DualScreenUI() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicText(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            )
        },
        content = {
            TwoPaneLayout(paneMode = TwoPaneMode.HorizontalSingle) {
                ListViewSpanned(appStateViewModel = appStateViewModel)
                DetailView(appStateViewModel = appStateViewModel)
            }
        }
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
