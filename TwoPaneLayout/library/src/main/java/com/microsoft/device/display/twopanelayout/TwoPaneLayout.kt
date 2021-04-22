/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

@Composable
inline fun TwoPaneLayout(
    modifier: Modifier,
    noinline content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val viewModelOwner = context as ViewModelStoreOwner
    val viewModel = ViewModelProvider(viewModelOwner).get(ScreenStateViewModel::class.java)
    SetupScreenState(context = context, viewModel = viewModel)

    val defaultScreenState = ScreenState(
            featureBounds = Rect(),
            isSpanned = false,
            isTablet = false,
            orientation = LayoutOrientation.Vertical
    )
    val screenStateLiveData = viewModel.getScreenStateLiveData()
    val screenState = screenStateLiveData.observeAsState(initial = defaultScreenState).value

    CompositionLocalProvider(LocalScreenState provides screenState) {
        val screenFeatureBounds = LocalScreenState.current.featureBounds
        val orientation = LocalScreenState.current.orientation
        val isVerticalOrientation = LocalScreenState.current.orientation == LayoutOrientation.Vertical
        val screenWidth = if (isVerticalOrientation) screenFeatureBounds.left else screenFeatureBounds.width()
        val screenHeight = if (isVerticalOrientation) screenFeatureBounds.height() else screenFeatureBounds.top
        val isAppSpanned = LocalScreenState.current.isSpanned
        val arrangementSpacing = if (isVerticalOrientation) screenFeatureBounds.width() else screenFeatureBounds.height()

        println("########## screenWidth: $screenWidth, screenHeight: $screenHeight, orientation: $orientation, arrangementSpacing: $arrangementSpacing")
        Layout(
            content = {
                content()
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            // TODO: limit the number of pane to 2 for now
            val childrenCount = placeables.count()
            println("########## childrenCount: $childrenCount")
            if (childrenCount > 2 || childrenCount < 0) {
                error("TwoPaneLayout requires 1 or 2 child elements")
            }

            if (!isAppSpanned) {
                layout(constraints.maxWidth, constraints.maxHeight) {
                    val placeable = placeables.first()
                    placeable.place(x = 0, y = 0)
                    println("########## app not spanned yet")
                }
            } else {
                if (orientation == LayoutOrientation.Vertical) {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var xPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = xPosition, y = 0)
                            xPosition += screenWidth + arrangementSpacing
                            println("########## orientation $orientation, xPosition: $xPosition")
                        }
                    }
                } else {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var yPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = 0, y = yPosition)
                            yPosition += screenHeight + arrangementSpacing
                            println("########## orientation $orientation, yPosition: $yPosition")
                        }
                    }
                }
            }


        }
    }
}

@PublishedApi
@Composable
internal fun TwoPaneLayout(
    modifier: Modifier,
    layoutOrientation: LayoutOrientation,
    content: @Composable TwoPaneScope.() -> Unit
) {
//    val measurePolicy = twoPaneMeasurePolicy()
//    Layout(
//        content = { TwoPaneScopeInstance.content() },
//        measurePolicy = measurePolicy,
//        modifier = modifier
//    )
}

@Composable
fun PaneContainer(width: Int, height: Int, child: @Composable () -> Unit) {
    println("########## PaneContainer: width: $width, height: $height")

    Layout(
        content = child
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        println("########## PaneContainer: placeables: $placeables, constraints: $constraints")

        layout(width, height) {
            placeables.forEach { placeable ->
                println("########## PaneContainer: placeable: $placeable")

                placeable.place(x = 0, y = 0)
            }
        }
    }
}

@LayoutScopeMarker
@Immutable
interface TwoPaneScope {

}

internal object TwoPaneScopeInstance : TwoPaneScope {


}

internal fun twoPaneMeasurePolicy(
    orientation: LayoutOrientation,
//    arrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    arrangementSpacing: Dp
): MeasurePolicy {
    return object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            val layoutWidth = 0
            val layoutHeight = 0
            return layout(layoutWidth, layoutHeight) {

            }
        }
    }
}