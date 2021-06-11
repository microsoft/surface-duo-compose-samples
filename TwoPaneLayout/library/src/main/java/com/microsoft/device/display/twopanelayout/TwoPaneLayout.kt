/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.debugInspectorInfo
import com.microsoft.device.display.twopanelayout.screenState.ConfigScreenState
import com.microsoft.device.display.twopanelayout.screenState.DeviceType
import com.microsoft.device.display.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.LayoutState
import com.microsoft.device.display.twopanelayout.screenState.ScreenState

/**
 * A layout component that places its children in one or two panes vertically or horizontally to
 * support the layout on foldable or dual-screen form factors. One-pane can be used to layout on
 * the single-screen device, or single-screen mode on the foldable or dual-screen devices. Two-pane
 * can be used to layout left/right or top/bottom screens on the foldable or dual-screen devices.
 * The tablet or wide screen devices will display two-pane layout by default.
 *
 * The [TwoPaneLayout] layout is able to assign children widths or heights according to their weights
 * provided using the [TwoPaneScope.weight] modifier. If all the children have not provided a weight,
 * they will be layout equally, with the potential padding in-between based on the
 * physical hinge between two screens.
 *
 * @param modifier The modifier to be applied to the TwoPane.
 */

@Composable
inline fun TwoPaneLayout(
    modifier: Modifier = Modifier,
    content: @Composable TwoPaneScope.() -> Unit
) {
    val currentView = LocalView.current
    val windowInsets by remember {
        mutableStateOf(
            currentView.rootWindowInsets
        )
    }
    val paddingBounds = Rect()
    paddingBounds.left = windowInsets.systemWindowInsetLeft
    paddingBounds.right = windowInsets.systemWindowInsetRight
    paddingBounds.top = windowInsets.systemWindowInsetTop
    paddingBounds.bottom = windowInsets.systemWindowInsetBottom

    var screenState by remember {
        mutableStateOf(
            ScreenState(
                deviceType = DeviceType.Single,
                screenSize = Size.Zero,
                hingeBounds = Rect(),
                orientation = LayoutOrientation.Horizontal,
                layoutState = LayoutState.Fold
            )
        )
    }

    ConfigScreenState(onStateChange = { screenState = it })

    val measurePolicy = twoPaneMeasurePolicy(
        layoutState = screenState.layoutState,
        orientation = screenState.orientation,
        paneSize = screenState.paneSize,
        paddingBounds = paddingBounds
    )
    Layout(
        content = { TwoPaneScopeInstance.content() },
        measurePolicy = measurePolicy,
        modifier = modifier
    )
}

@LayoutScopeMarker
@Immutable
interface TwoPaneScope {
    @Stable
    fun Modifier.weight(
        weight: Float,
    ): Modifier
}

internal object TwoPaneScopeInstance : TwoPaneScope {
    @Stable
    override fun Modifier.weight(weight: Float): Modifier {
        require(weight > 0.0) { "invalid weight $weight; must be greater than zero" }
        return this.then(
            LayoutWeightImpl(
                weight = weight,
                inspectorInfo = debugInspectorInfo {
                    name = "weight"
                    value = weight
                    properties["weight"] = weight
                }
            )
        )
    }
}
