package com.example.navigationrail.ui.view

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.layout.WindowInfoRepository
import com.example.navigationrail.ui.theme.ComposeSamplesTheme
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import kotlinx.coroutines.flow.collect

const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository, window: Window) {
    var isAppSpanned by remember { mutableStateOf(false) }

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
    // TODO: change status bar text color to be onPrimary

    DualScreenUI(isDualScreen, isPortrait)
}

@Composable
fun DualScreenUI(isDualScreen: Boolean, isPortrait: Boolean) {
    TwoPaneLayout(
        paneMode = TwoPaneMode.HorizontalSingle,
        pane1 = { Pane1(isDualScreen, isPortrait) },
        pane2 = { Pane2() },
    )
}

@Composable
fun Pane1(isDualScreen: Boolean, isPortrait: Boolean) {
}

@Composable
fun Pane2() {
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPane1PortraitDarkMode() {
    ComposeSamplesTheme {
        Pane1(isDualScreen = false, isPortrait = false)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewPane1PortraitLightMode() {
    ComposeSamplesTheme {
        Pane1(isDualScreen = false, isPortrait = false)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPane2DarkMode() {
    ComposeSamplesTheme {
        Pane2()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewPane2LightMode() {
    ComposeSamplesTheme {
        Pane2()
    }
}
