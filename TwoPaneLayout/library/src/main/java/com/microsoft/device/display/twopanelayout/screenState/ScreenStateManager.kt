package com.microsoft.device.display.twopanelayout.screenState

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.core.util.Consumer
import androidx.window.FoldingFeature
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import java.util.concurrent.Executor

const val SmallestTabletScreenWidthDp = 585

@Composable
fun ConfigScreenState(viewModel: ScreenStateViewModel) {
    val context = LocalContext.current
    val windowManager = WindowManager(context)
    val handler = Handler(Looper.getMainLooper())
    val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SmallestTabletScreenWidthDp
    var deviceType = if (isTablet) DeviceType.Big else DeviceType.Single
    var layoutState = if (isTablet) LayoutState.Open else LayoutState.Fold
    val screenHeight = LocalConfiguration.current.screenHeightDp * LocalDensity.current.density
    val screenWidth = LocalConfiguration.current.screenWidthDp * LocalDensity.current.density
    var orientation = orientationMappingFromScreen(LocalConfiguration.current.orientation)

    if (!LocalView.current.isAttachedToWindow) {
        return
    }

    val layoutChangeCallback = remember {
        Consumer<WindowLayoutInfo> { newLayoutInfo ->
            var featureBounds = Rect()
            if (newLayoutInfo.displayFeatures.isNotEmpty()) {
                val foldingFeature = newLayoutInfo.displayFeatures.first() as FoldingFeature
                featureBounds = foldingFeature.bounds
                layoutState = if (foldingFeature.isSeparating) LayoutState.Open else LayoutState.Fold
                orientation = orientationMappingFromFoldingFeature(foldingFeature.orientation)
                deviceType = DeviceType.Multiple
            }

            val screenState = ScreenState(
                deviceType = deviceType,
                screenSize = Size(width = screenWidth, height = screenHeight),
                hingeBounds = featureBounds,
                orientation = orientation,
                layoutState = layoutState
            )
            viewModel.setScreenState(screenState)
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

private fun orientationMappingFromFoldingFeature(original: Int): LayoutOrientation {
    return if (original == FoldingFeature.ORIENTATION_VERTICAL) {
        LayoutOrientation.Vertical
    } else {
        LayoutOrientation.Horizontal
    }
}

// For tablet or single-screen device
// Portrait orientation will dual-landscape mode, which is treated as using horizontal hinge
// Landscape orientation will be dual-portrait mode, which is treated as using vertical hinge
private fun orientationMappingFromScreen(original: Int): LayoutOrientation {
    return if (original == ORIENTATION_PORTRAIT) {
        LayoutOrientation.Horizontal
    } else {
        LayoutOrientation.Vertical
    }
}
