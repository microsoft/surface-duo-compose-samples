package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.window.FoldingFeature
import androidx.window.WindowManager
import java.util.concurrent.Executor

data class ScreenState(
    val isSpanned: Boolean,
    val isTablet: Boolean,
    val isVertical: Boolean,
    val featureBounds: Rect)

val LocalScreenState = compositionLocalOf<ScreenState> { error("ScreenState hasn't benn set up yet!") }

@Composable
fun SetupScreenState(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val windowManager = WindowManager(context)
    val handler = Handler(Looper.getMainLooper())
    val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > 585
    var screenState = ScreenState(
        featureBounds = Rect(),
        isSpanned = false,
        isTablet = isTablet,
        isVertical = false)

    if (!LocalView.current.isAttachedToWindow) {
        return
    }

    DisposableEffect(context) {
        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                if (windowLayoutInfo.displayFeatures.isNotEmpty()) {
                    val foldingFeature = windowLayoutInfo.displayFeatures.first() as FoldingFeature
                    val featureBounds = foldingFeature.bounds
                    val isSeparating = foldingFeature.isSeparating
                    val isVertical = foldingFeature.orientation == FoldingFeature.ORIENTATION_VERTICAL
                    screenState = ScreenState(
                        featureBounds = featureBounds,
                        isSpanned = isSeparating,
                        isTablet = isTablet,
                        isVertical = isVertical)
                }
            }
        )
        // When the effect leaves the Composition, remove the callback
        onDispose {
            windowManager.unregisterLayoutChangeCallback {}
        }
    }

    CompositionLocalProvider(LocalScreenState provides screenState) {
        content
    }
}