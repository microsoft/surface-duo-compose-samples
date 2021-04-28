/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.device.display.twopanelayout.screenState.ConfigScreenState
import com.microsoft.device.display.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.LayoutState
import com.microsoft.device.display.twopanelayout.screenState.ScreenStateViewModel

@Composable
inline fun TwoPaneLayout(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val windowInsets = LocalView.current.rootWindowInsets
    val paddingBounds = Rect()
    paddingBounds.left = windowInsets.systemWindowInsetLeft
    paddingBounds.right = windowInsets.systemWindowInsetRight
    paddingBounds.top = windowInsets.systemWindowInsetTop
    paddingBounds.bottom = windowInsets.systemWindowInsetBottom

    val viewModel:ScreenStateViewModel = viewModel()
    ConfigScreenState(viewModel = viewModel)
    val screenState by viewModel.screenStateLiveData.observeAsState()
    screenState?.let { state ->
            val measurePolicy = twoPaneMeasurePolicy(
            layoutState = state.layoutState,
            orientation = state.orientation,
            paneSizes = state.paneSizes,
            arrangementSpacing = state.hingeWidth,
            paddingBounds = paddingBounds
        )
        Layout(
            content = { content() },
            measurePolicy = measurePolicy,
            modifier = modifier
        )
    }
}

@LayoutScopeMarker
@Immutable
interface TwoPaneScope {

}

internal object TwoPaneScopeInstance : TwoPaneScope {

}

@PublishedApi
@Composable
internal fun twoPaneMeasurePolicy(
    layoutState: LayoutState,
    orientation: LayoutOrientation,
    paneSizes: List<Size>,
    arrangementSpacing: Int,
    paddingBounds: Rect
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        val paneWidth = paneSizes.first().width.toInt()
        val paneHeight = paneSizes.first().height.toInt()
        var minWidth = constraints.minWidth
        var minHeight = constraints.minHeight
        minWidth = minWidth.coerceAtMost(paneWidth)
        minHeight = minHeight.coerceAtMost(paneHeight)
        val childConstraints = Constraints(minWidth = minWidth, minHeight = minHeight, maxWidth = minWidth, maxHeight = minHeight)

        val placeables = measurables.map { it.measure(childConstraints) }

        // TODO: limit the number of pane to 2 for now
        val childrenCount = placeables.count()
        if (childrenCount > 2 || childrenCount < 0) {
            error("TwoPaneLayout requires 1 or 2 child elements")
        }

        when (layoutState) {
            LayoutState.Fold -> { // only layout the first pane
                layout(constraints.maxWidth, constraints.maxHeight) {
                    val placeable = placeables.first()
                    placeable.place(x = 0, y = 0)
                }
            }

            // TODO: layout according to the weight
            LayoutState.Open -> {
                if (orientation == LayoutOrientation.Vertical) {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var xPosition = 0
                        for (i in placeables.indices) {
                            var lastPaneWidth = paneWidth - paddingBounds.right
                            var firstPaneWidth = constraints.maxWidth - lastPaneWidth

                            placeables[i].placeRelative(x = xPosition, y = 0)
                            xPosition += if (i == 0) {
                                firstPaneWidth
                            } else { // for the second pane
                                paneHeight + arrangementSpacing
                            }
                        }
                    }
                } else {
                    // calculate the first pane differently, due to the potential status bar, top app bar and bottom navigation bar
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var yPosition = 0
                        for (i in placeables.indices) {
                            var lastPaneHeight = paneHeight - paddingBounds.bottom
                            var firstPaneHeight = constraints.maxHeight - lastPaneHeight

                            placeables[i].placeRelative(x = 0, y = yPosition)
                            yPosition += if (i == 0) {
                                firstPaneHeight
                            } else { // for the second pane
                                paneHeight + arrangementSpacing
                            }
                        }
                    }
                }
            }
        }
    }
}
