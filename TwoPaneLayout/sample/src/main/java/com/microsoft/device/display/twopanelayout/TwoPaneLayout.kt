/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopanelayout

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.window.FoldingFeature

private lateinit var mFoldingFeature: FoldingFeature

enum class LayoutState {
    Open,
    Fold
}

enum class LayoutOrientation {
    Horizontal,
    Vertical
}

@Composable
fun TwoPaneLayout(
    context: Context,
    modifier: Modifier,
    layoutState: LayoutState,
    content: @Composable TwoPaneScope.() -> Unit
) {

    SideEffect {
        Log.d("Compose", "onactive with value: " )
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d("Compose", "onDispose because value=")
        }
    }

}

@LayoutScopeMarker
@Immutable
interface TwoPaneScope {

}

internal object TwoPaneScopeInstance : TwoPaneScope {

}

data class WindowState(val isFoldable: Boolean, val isSpanned: Boolean, val isTablet: Boolean, val hingeWidth: Int)
val LocalWindowState = staticCompositionLocalOf<WindowState> { error("LocalWindowState not set up, ProvideWindowLocals wasn't called") }

//@Composable
//fun ProvideWindowLocals(content: @Composable () -> Unit)
//{
//    LogFrequentCompositions(TAG)
//
//    val context = LocalContext.current
//    val isTablet = LocalConfiguration.current.smallestScreenWidthDp > 585
//    val windowManager = WindowManager(context)
//    var windowState by remember { mutableStateOf(WindowState(false, false, isTablet, 0))}
//    val layoutStateChangedCallback = remember {
//        object : Consumer<WindowLayoutInfo> {
//            override fun accept(windowLayoutInfo: WindowLayoutInfo?) {
//                val isFoldable = windowLayoutInfo?.displayFeatures?.any {
//                    it is FoldingFeature
//                } ?: false
//                val isSpanned = isFoldable && windowLayoutInfo?.displayFeatures?.any {
//                    it is FoldingFeature && it.isSeparating && it.occlusionMode == FoldingFeature.OCCLUSION_FULL
//                } ?: false
//                val hingeWidth = if (isFoldable) {
//                    (windowLayoutInfo!!.displayFeatures.first()).bounds.width()
//                } else {
//                    0
//                }
//
//                windowState = WindowState(
//                    isFoldable,
//                    isSpanned,
//                    isTablet,
//                    hingeWidth
//                )
//            }
//        }
//    }
//    var callbackRegistered by remember { mutableStateOf(false) }
//
//    // Note, we're not using SideEffect here because it runs after the function completes, which
//    // would trigger an immediate update.  This works well for first update, but when spanning,
//    // the 'old' window is getting an update and re-composing unnecessarily.
//    // TODO see if activity android:configChanges screenSize should be used to avoid recreates
//    if (!callbackRegistered) {
//        callbackRegistered = true
//        windowManager.registerLayoutChangeCallback(
//            // Inline the callback immediately rather than scheduled with an executor
//            object : Executor {
//                override fun execute(command: Runnable?) {
//                    command?.run()
//                }
//            }, layoutStateChangedCallback
//        )
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            if (callbackRegistered) {
//                callbackRegistered = false
//                windowManager.unregisterLayoutChangeCallback(layoutStateChangedCallback)
//            }
//        }
//    }
//
//    CompositionLocalProvider(
//        LocalWindowState provides windowState,
//        content = content
//    )
//}

//override fun onAttachedToWindow() {
//    println("########### onAttachedToWindow")
//
//    super.onAttachedToWindow()
//
//    windowManager.registerLayoutChangeCallback(
//        mainThreadExecutor,
//        { windowLayoutInfo ->
//            reserveScreenState(windowLayoutInfo.displayFeatures)
//            println("########### registerLayoutChangeCallback")
//
//
//        }
//    )
//}
//
//override fun onDetachedFromWindow() {
//    super.onDetachedFromWindow()
//    windowManager.unregisterLayoutChangeCallback {}
//}