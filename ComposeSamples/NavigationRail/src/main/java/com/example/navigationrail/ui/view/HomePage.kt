package com.example.navigationrail.ui.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.layout.WindowInfoRepository
import com.example.navigationrail.models.AppStateViewModel
import com.example.navigationrail.models.DataProvider
import com.example.navigationrail.models.Image
import com.example.navigationrail.ui.theme.ComposeSamplesTheme
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
    appStateViewModel = viewModel

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualScreen = (isAppSpanned || isTablet)

    DualScreenUI(isDualScreen)
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DualScreenUI(isDualScreen: Boolean) {
    val selectedImage =
        appStateViewModel.imageSelectionLiveData.observeAsState(initial = null).value

    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = { Pane1(isDualScreen) },
        pane2 = { Pane2(selectedImage) },
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Pane1(isDualScreen: Boolean) {
    ShowWithNavigation(isDualScreen, appStateViewModel.imageSelectionLiveData)
}

@Composable
fun Pane2(selectedImage: Image?) {
    ShowWithTopBar(
        title = selectedImage?.description ?: "",
        titleColor = MaterialTheme.colors.onSecondary,
        color = MaterialTheme.colors.secondary,
    ) {
        ItemView(selectedImage)
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPane1DarkMode() {
    ComposeSamplesTheme {
        Pane1(false)
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewPane1LightMode() {
    ComposeSamplesTheme {
        Pane1(false)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPane2DarkMode() {
    ComposeSamplesTheme {
        Pane2(DataProvider.plantList[0])
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewPane2LightMode() {
    ComposeSamplesTheme {
        Pane2(DataProvider.plantList[0])
    }
}
