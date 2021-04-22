/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
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
        layoutState = LayoutState.Fold,
        isTablet = false,
        orientation = LayoutOrientation.Vertical
    )
    val screenStateLiveData = viewModel.getScreenStateLiveData()
    val screenState = screenStateLiveData.observeAsState(initial = defaultScreenState).value

    val screenFeatureBounds = screenState.featureBounds
    val orientation = screenState.orientation
    val isVerticalOrientation = screenState.orientation == LayoutOrientation.Vertical
    val screenWidth = if (isVerticalOrientation) screenFeatureBounds.left else screenFeatureBounds.width()
    val screenHeight = if (isVerticalOrientation) screenFeatureBounds.height() else screenFeatureBounds.top
    val layoutState = screenState.layoutState
    val arrangementSpacing = if (isVerticalOrientation) screenFeatureBounds.width() else screenFeatureBounds.height()

    val measurePolicy = twoPaneMeasurePolicy(
        layoutState = layoutState,
        orientation = orientation,
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        arrangementSpacing = arrangementSpacing)
    Layout(
        content = { content() },
        measurePolicy = measurePolicy,
        modifier = modifier
    )
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
    screenWidth: Int,
    screenHeight: Int,
    arrangementSpacing: Int
): MeasurePolicy {
    return object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            // TODO: limit the number of pane to 2 for now
            val childrenCount = placeables.count()
            println("########## childrenCount: $childrenCount")
            if (childrenCount > 2 || childrenCount < 0) {
                error("TwoPaneLayout requires 1 or 2 child elements")
            }

            return if (layoutState == LayoutState.Fold) { // only layout the first pane
                layout(constraints.maxWidth, constraints.maxHeight) {
                    val placeable = placeables.first()
                    placeable.place(x = 0, y = 0)
                }
            } else {
                if (orientation == LayoutOrientation.Vertical) {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var xPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = xPosition, y = 0)
                            xPosition += screenWidth + arrangementSpacing
                        }
                    }
                } else {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var yPosition = 0
                        placeables.forEach { placeable ->
                            placeable.place(x = 0, y = yPosition)
                            yPosition += screenHeight + arrangementSpacing
                        }
                    }
                }
            }
        }
    }
}