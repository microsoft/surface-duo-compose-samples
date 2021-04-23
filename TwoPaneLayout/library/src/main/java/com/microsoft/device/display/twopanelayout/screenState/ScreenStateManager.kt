package com.microsoft.device.display.twopanelayout

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.core.util.Consumer
import androidx.window.FoldingFeature
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import com.microsoft.device.display.twopanelayout.screenState.model.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.model.LayoutState
import com.microsoft.device.display.twopanelayout.screenState.model.ScreenStateViewModel
import java.util.concurrent.Executor

@Composable
fun SetupScreenState(context: Context, viewModel: ScreenStateViewModel) {
    val windowManager = WindowManager(context)
    val handler = Handler(Looper.getMainLooper())
    val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > 585

    if (!LocalView.current.isAttachedToWindow) {
        return
    }

    val layoutChangeCallback = remember {
        Consumer<WindowLayoutInfo> { newLayoutInfo ->
            var featureBounds = Rect()
            var layoutState = LayoutState.Fold
            var orientation = LayoutOrientation.Vertical
            if (newLayoutInfo.displayFeatures.isNotEmpty()) {
                val foldingFeature = newLayoutInfo.displayFeatures.first() as FoldingFeature
                featureBounds = foldingFeature.bounds
                layoutState = if(foldingFeature.isSeparating) LayoutState.Open else LayoutState.Fold
                orientation = orientationMapping(foldingFeature.orientation)
            }
            with(viewModel) {
                if (newLayoutInfo.displayFeatures.isNotEmpty()) {
                    val foldingFeature = newLayoutInfo.displayFeatures.first() as FoldingFeature
                    featureBounds = foldingFeature.bounds
                    layoutState = if(foldingFeature.isSeparating) LayoutState.Open else LayoutState.Fold
                    orientation = orientationMapping(foldingFeature.orientation)
                }
                setScreenState(
                    isTablet = isTablet,
                    featureBounds = featureBounds,
                    layoutState = layoutState,
                    orientation = orientation
                )
            }
        }
    }

    DisposableEffect(windowManager) {
        windowManager.registerLayoutChangeCallback(mainThreadExecutor, layoutChangeCallback)

        // When the effect leaves the Composition, remove the callback
        onDispose {
            windowManager.unregisterLayoutChangeCallback(layoutChangeCallback)
        }
    }
}

private fun orientationMapping(original: Int): LayoutOrientation {
    return if (original == FoldingFeature.ORIENTATION_VERTICAL) {
        LayoutOrientation.Vertical
    } else {
        LayoutOrientation.Horizontal
    }
}
