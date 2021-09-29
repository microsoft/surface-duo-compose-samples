/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.MutableLiveData
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.navigationrail.models.AppStateViewModel
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import kotlinx.coroutines.flow.collect

private lateinit var appStateViewModel: AppStateViewModel
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository, viewModel: AppStateViewModel) {
    var isAppSpanned by remember { mutableStateOf(false) }
    var isHingeVertical by remember { mutableStateOf(false) }
    appStateViewModel = viewModel

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
                if (isAppSpanned) {
                    val foldingFeature = displayFeatures.first() as FoldingFeature
                    isHingeVertical = foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
                }
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualScreen = (isAppSpanned || isTablet)
    // REVISIT: i think a good addition to twopanelayout would be the ability to check the state
    // of the layout? like in the "single" modes you would want the single screen view to be shown
    // when the layout is technically still dual screen (would also be useful to know which pane
    // is currently being shown, like if i navigate to pane2 inside pane1, I would want to know
    // so I could return to pane1 eventually)
    val isDualPortrait = isDualScreen && isHingeVertical

    DualScreenUI(isDualScreen, isDualPortrait)
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DualScreenUI(isDualScreen: Boolean, isDualPortrait: Boolean) { // , navController: NavHostController, navHost: @Composable (Modifier) -> Unit) {
    // Observe state of image selection
    val imageLiveData = appStateViewModel.imageSelectionLiveData
    val selectedImage = imageLiveData.observeAsState(initial = null).value

    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = { Pane1(isDualScreen, isDualPortrait) },
        pane2 = { Pane2(isDualPortrait, imageLiveData, selectedImage) },
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Pane1(isDualScreen: Boolean, isDualPortrait: Boolean) {
    ShowWithNavigation(isDualScreen, appStateViewModel.imageSelectionLiveData, isDualPortrait)
}

@Composable
fun Pane2(isDualPortrait: Boolean, imageLiveData: MutableLiveData<Image>, selectedImage: Image?) {
    if (!isDualPortrait) {
        ShowWithTopBar(
            title = selectedImage?.description ?: "",
            titleColor = MaterialTheme.colors.onSecondary,
            color = MaterialTheme.colors.secondary,
            navIcon = { TopBarNavIcon(imageLiveData) },
        ) {
            ItemView(selectedImage)
        }
    } else {
        ShowWithTopBar(
            title = selectedImage?.description ?: "",
            titleColor = MaterialTheme.colors.onSecondary,
            color = MaterialTheme.colors.secondary,
        ) {
            ItemView(selectedImage)
        }
    }
}
