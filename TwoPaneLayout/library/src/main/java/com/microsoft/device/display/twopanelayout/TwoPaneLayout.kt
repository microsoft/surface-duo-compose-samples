/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

enum class LayoutState {
    Open,
    Fold
}

enum class LayoutOrientation {
    Horizontal,
    Vertical
}

@Composable
inline fun TwoPaneLayout(
    modifier: Modifier,
    noinline content: @Composable TwoPaneScope.() -> Unit
) {
    SetupScreenState {
        val isVerticalOrientation = LocalScreenState.current.isVertical
        val screenFeatureBounds = LocalScreenState.current.featureBounds
        val orientation = if (isVerticalOrientation) LayoutOrientation.Vertical else LayoutOrientation.Horizontal
        val screenWidth = if (isVerticalOrientation) screenFeatureBounds.left else screenFeatureBounds.width()
        val screenHeight = if (isVerticalOrientation) screenFeatureBounds.height() else screenFeatureBounds.top
        val layoutState = if (LocalScreenState.current.isSpanned) LayoutState.Open else LayoutState.Fold
        val arrangementSpacing = screenFeatureBounds.width()

        Layout(
            content = {
                var count = when (layoutState) {
                    LayoutState.Fold -> 1
                    LayoutState.Open -> 2  // TODO: limit the number of pane to 2 for now
                }
                for (i in 1..count) {
                    PaneContainer(width = screenWidth, height = screenHeight) {
                        TwoPaneScopeInstance.content()
                    }
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            // TODO: limit the number of pane to 2 for now
            val childrenCount = placeables.count()
            if (childrenCount > 2 || childrenCount < 0) {
                error("TwoPaneLayout requires 1 or 2 child elements")
            }

            if (orientation == LayoutOrientation.Vertical) {
                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeables.forEach { placeable ->
                        placeable.place(x = 0, y = 0)
                        var xPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = xPosition, y = 0)
                            xPosition += placeable.width + arrangementSpacing
                        }
                    }
                }
            } else {
                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeables.forEach { placeable ->
                        var yPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = 0, y = yPosition)
                            yPosition += placeable.height + arrangementSpacing
                        }
                    }
                }
            }
        }

//        TwoPaneLayout(
//            modifier = modifier,
//            layoutState = layoutState,
//            layoutOrientation = orientation,
//            content = content)
    }
}

@PublishedApi
@Composable
internal fun TwoPaneLayout(
    modifier: Modifier,
    layoutState: LayoutState,
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
    Layout(
        content = child
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(width, height) {
            placeables.forEach { placeable ->
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
    layoutState: LayoutState,

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