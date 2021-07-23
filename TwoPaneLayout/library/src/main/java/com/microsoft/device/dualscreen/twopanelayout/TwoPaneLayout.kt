/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.twopanelayout

import android.graphics.Rect
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.dualscreen.twopanelayout.screenState.ConfigScreenState
import com.microsoft.device.dualscreen.twopanelayout.screenState.DeviceType
import com.microsoft.device.dualscreen.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.dualscreen.twopanelayout.screenState.LayoutState
import com.microsoft.device.dualscreen.twopanelayout.screenState.ScreenState

private lateinit var navController: NavHostController
private var isSinglePane: Boolean = true

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
fun TwoPaneLayout(
    modifier: Modifier = Modifier,
    paneMode: TwoPaneMode = TwoPaneMode.TwoPane,
    firstPane: @Composable TwoPaneScope.() -> Unit,
    secondPane: @Composable TwoPaneScope.() -> Unit
) {
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

    isSinglePane = isSinglePaneLayout(
        layoutState = screenState.layoutState,
        paneMode = paneMode,
        orientation = screenState.orientation
    )

    if (isSinglePane) {
        SinglePaneContainer(
            firstPane = firstPane,
            secondPane = secondPane
        )
    } else {
        TwoPaneContainer(
            screenState = screenState,
            paneMode = paneMode,
            modifier = modifier,
            firstPane = firstPane,
            secondPane = secondPane
        )
    }
}

fun navigateToSecondPane() {
    require(isSinglePane) { "Navigation can be activated only in the single pane mode" }

    navController.navigate("second")
}

fun navigationToFirstPane() {
    require(isSinglePane) { "Navigation can be activated only in the single pane mode" }

    navController.popBackStack()
}

@Composable
private fun TwoPaneContainer(
    screenState: ScreenState,
    paneMode: TwoPaneMode,
    modifier: Modifier,
    firstPane: @Composable TwoPaneScope.() -> Unit,
    secondPane: @Composable TwoPaneScope.() -> Unit
) {
    val windowInsets = LocalView.current.rootWindowInsets
    val paddingBounds by remember {
        mutableStateOf(
            Rect()
        )
    }
    paddingBounds.left = windowInsets.systemWindowInsetLeft
    paddingBounds.right = windowInsets.systemWindowInsetRight
    paddingBounds.top = windowInsets.systemWindowInsetTop
    paddingBounds.bottom = windowInsets.systemWindowInsetBottom

    val measurePolicy = twoPaneMeasurePolicy(
        layoutState = screenState.layoutState,
        isSinglePane = isSinglePane,
        orientation = screenState.orientation,
        paneSize = screenState.paneSize,
        paddingBounds = paddingBounds
    )
    Layout(
        content = {
            TwoPaneScopeInstance.firstPane()
            TwoPaneScopeInstance.secondPane() },
        measurePolicy = measurePolicy,
        modifier = modifier
    )
}

@Composable
private fun SinglePaneContainer(
    firstPane: @Composable TwoPaneScope.() -> Unit,
    secondPane: @Composable TwoPaneScope.() -> Unit
) {
    navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        composable("first") {
            TwoPaneScopeInstance.firstPane()
        }
        composable("second") {
            TwoPaneScopeInstance.secondPane()
        }
    }
}

private fun isSinglePaneLayout(layoutState: LayoutState, paneMode: TwoPaneMode, orientation: LayoutOrientation): Boolean {
    return layoutState == LayoutState.Fold ||
            paneMode == TwoPaneMode.VerticalSingle && orientation == LayoutOrientation.Vertical ||
            paneMode == TwoPaneMode.HorizontalSingle && orientation == LayoutOrientation.Horizontal
}

/**
 * TwoPaneMode
 * TwoPane,          always shows two panes, regardless the orientation, by default
 * HorizontalSingle  shows big single pane in horizontal orientation layout(top/bottom)
 * VerticalSingle    shows big single pane in vertical orientation layout(left/right)
 */
enum class TwoPaneMode {
    TwoPane,
    HorizontalSingle,
    VerticalSingle
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
