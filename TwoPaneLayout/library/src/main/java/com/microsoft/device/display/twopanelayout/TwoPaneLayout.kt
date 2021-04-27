/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.device.display.twopanelayout.screenState.ConfigScreenState
import com.microsoft.device.display.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.LayoutState
import com.microsoft.device.display.twopanelayout.screenState.ScreenStateViewModel

@RequiresApi(Build.VERSION_CODES.M)
@Composable
inline fun TwoPaneLayout(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val topSpacing = LocalView.current.rootWindowInsets.systemWindowInsetTop
    val viewModel:ScreenStateViewModel = viewModel()
    ConfigScreenState(context = context, viewModel = viewModel)
    val screenState by viewModel.screenStateLiveData.observeAsState()
    screenState?.let { state ->
        val orientation = state.orientation
        val layoutState = state.layoutState
        val paneSizes = state.paneSizes
        val arrangementSpacing = state.hingeWidth
        val measurePolicy = twoPaneMeasurePolicy(
            layoutState = layoutState,
            orientation = orientation,
            paneSizes = paneSizes,
            arrangementSpacing = arrangementSpacing,
            horizontalTopSpacing = topSpacing
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
    horizontalTopSpacing: Int
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        val paneWidth = paneSizes.first().width.toInt()
        val paneHeight = paneSizes.first().height.toInt()
        var minWidth = constraints.minWidth
        var minHeight = constraints.minHeight
        minWidth = minWidth.coerceAtMost(paneWidth)
        minHeight = minHeight.coerceAtMost(paneHeight)
        val newConstraints = Constraints(minWidth = minWidth, minHeight = minHeight, maxWidth = minWidth, maxHeight = minHeight)

        val placeables = measurables.map { measurable ->
            measurable.measure(newConstraints)
        }

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
                        placeables.forEach { placeable ->
                            placeable.place(x = xPosition, y = 0)
                            xPosition += paneWidth + arrangementSpacing
                        }
                    }
                } else {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var yPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = 0, y = yPosition)
                            yPosition += paneHeight + arrangementSpacing - horizontalTopSpacing
                        }
                    }
                }
            }
        }
    }
}
