package com.microsoft.device.display.twopanelayout

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.window.DisplayFeature
import androidx.window.FoldingFeature
import androidx.window.WindowManager
import java.util.concurrent.Executor

@Composable
fun WindowManagerRegistration(viewModel: ScreenStateViewModel) {
    val context = LocalContext.current
    val windowManager = WindowManager(context)
    val handler = Handler(Looper.getMainLooper())
    val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }
    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp

    if (!LocalView.current.isAttachedToWindow) {
        return
    }

    DisposableEffect(true) {
        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                reserveScreenState(windowLayoutInfo.displayFeatures, smallestScreenWidthDp, viewModel)
            }
        )

        // When the effect leaves the Composition, remove the callback
        onDispose {
            windowManager.unregisterLayoutChangeCallback {}
        }
    }
}

private fun reserveScreenState(displayFeatures: List<DisplayFeature>, smallestScreenWidthDp: Int, viewModel: ScreenStateViewModel) {
    val isTablet = smallestScreenWidthDp > 585
    if (displayFeatures.isNotEmpty()) {
        val foldingFeature = displayFeatures.first() as FoldingFeature
        val hingeWidth = foldingFeature.bounds.width()
        val isSeparating = foldingFeature.isSeparating
        val isVertical = foldingFeature.orientation == FoldingFeature.ORIENTATION_VERTICAL
        val screenState = ScreenState(hingeWidth = hingeWidth, isSpanned = isSeparating, isTablet = isTablet, isVertical = isVertical)
        viewModel.setScreenStateLiveData(screenState = screenState)
    }
}