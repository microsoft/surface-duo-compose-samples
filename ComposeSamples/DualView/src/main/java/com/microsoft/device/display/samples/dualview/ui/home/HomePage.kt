/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

private lateinit var appStateViewModel: AppStateViewModel
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isDualScreenLiveData = appStateViewModel.isDualScreenLiveData
    val isDualScreen = isDualScreenLiveData.observeAsState(initial = false).value
    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP

    if (isDualScreen || isTablet) {
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
            RestaurantViewWithTopBar(
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
        composable("map") {
            MapViewWithTopBar(
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
    }
}

@Composable
fun DualScreenUI() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                }
            )
        },
        content = {
            TwoPaneLayout(
                pane1 = {
                    RestaurantsView(
                        modifier = Modifier.fillMaxSize(),
                        navController = null,
                        appStateViewModel = appStateViewModel
                    )
                },
                pane2 = {
                    MapView(
                        modifier = Modifier.fillMaxSize(),
                        appStateViewModel = appStateViewModel
                    )
                }
            )
        }
    )
}

@Composable
fun RestaurantViewWithTopBar(navController: NavController, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("map")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_map),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                }
            )
        },
        content = {
            RestaurantsView(
                modifier = Modifier.wrapContentSize(),
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
    )
}

@Composable
fun MapViewWithTopBar(navController: NavController, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_list),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = {
            MapView(
                modifier = Modifier.wrapContentSize(),
                appStateViewModel = appStateViewModel
            )
        }
    )
}

@Composable
fun ImageView(imageId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.FillWidth
    )
}
