package com.example.navigationrail.ui.view

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Color
import android.view.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowInfoRepository
import com.example.navigationrail.R
import com.example.navigationrail.models.AppStateViewModel
import com.example.navigationrail.models.DataProvider
import com.example.navigationrail.models.Image
import com.example.navigationrail.ui.theme.ComposeSamplesTheme
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import kotlinx.coroutines.flow.collect

private lateinit var appStateViewModel: AppStateViewModel
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@ExperimentalFoundationApi
@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository, window: Window, viewModel: AppStateViewModel) {
    var isAppSpanned by remember { mutableStateOf(false) }
    appStateViewModel = viewModel

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
            }
    }

    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualScreen = (isAppSpanned || isTablet) && !isPortrait

    window.statusBarColor = Color.TRANSPARENT

    DualScreenUI(isDualScreen, isPortrait)
}

@ExperimentalFoundationApi
@Composable
fun DualScreenUI(isDualScreen: Boolean, isPortrait: Boolean) {
    val selectedImage = appStateViewModel.imageSelectionLiveData.observeAsState(initial = null).value

    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = { Pane1() },
        pane2 = { Pane2(selectedImage) },
    )
}

@ExperimentalFoundationApi
@Composable
fun Pane1() {
    ShowWithTopBar(
        title = stringResource(id = R.string.app_name),
        content = { Gallery(DataProvider.floraList, appStateViewModel.imageSelectionLiveData) }
    )
}

@Composable
fun Pane2(selectedImage: Image?) {
    ShowWithTopBar {
        ItemView(selectedImage)
    }
}

@Composable
fun ShowWithTopBar(title: String? = null, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicText(
                        text = title ?: "",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { content() }
    )
}

@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPane1PortraitDarkMode() {
    ComposeSamplesTheme {
        Pane1()
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewPane1PortraitLightMode() {
    ComposeSamplesTheme {
        Pane1()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPane2DarkMode() {
    ComposeSamplesTheme {
        Pane2(DataProvider.floraList[0])
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewPane2LightMode() {
    ComposeSamplesTheme {
        Pane2(DataProvider.floraList[0])
    }
}
