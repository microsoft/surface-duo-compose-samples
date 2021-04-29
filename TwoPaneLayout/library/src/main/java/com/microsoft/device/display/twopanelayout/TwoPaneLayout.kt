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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.device.display.twopanelayout.screenState.ConfigScreenState
import com.microsoft.device.display.twopanelayout.screenState.ScreenStateViewModel

@Composable
inline fun TwoPaneLayout(
    modifier: Modifier,
    content: @Composable TwoPaneScope.() -> Unit
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
            paneSize = state.paneSize,
            arrangementSpacing = state.hingeWidth,
            paddingBounds = paddingBounds
        )
        Layout(
            content = { TwoPaneScopeInstance.content() },
            measurePolicy = measurePolicy,
            modifier = modifier
        )
    }
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
