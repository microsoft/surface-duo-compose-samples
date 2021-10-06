/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val CONTENT_HORIZ_PADDING_PERECENT = 0.02
private val DrawerShape = RoundedCornerShape(
    topStartPercent = 6,
    topEndPercent = 6,
    bottomStartPercent = 0,
    bottomEndPercent = 0
)

private enum class DrawerState { Collapsed, Expanded }

/**
 * Reference: BottomDrawer component code
 *
 * Custom drawer (bottom aligned) with a rounded corner shape that swipes between a collapsed
 * "peek" view and a more expanded view that displays all of the content
 *
 * @param modifier: optional Modifier to be applied to the layout
 * @param expandHeight: height of the drawer when expanded (in dp)
 * @param collapseHeight: height of the drawer when collpased (in dp)
 * @param hingeOccludes: optional param for foldable support, indicates whether there is a hinge
 * that occludes content in the current layout
 * @param hingeSize: optional param for foldable support, indicates the size of a hinge
 * @param pill: optional param to show a pill graphic on the drawer that tells the user the drawer
 * can be swiped up/down
 * @param hiddenContent: the content that will only be shown when the drawer is expanded
 * @param peekContent: the content that will be shown even when the drawer is collapsed
 */
@ExperimentalMaterialApi
@Composable
fun ContentDrawer(
    modifier: Modifier = Modifier,
    expandHeight: Dp,
    collapseHeight: Dp,
    hingeOccludes: Boolean = false,
    hingeSize: Dp = 0.dp,
    pill: @Composable ColumnScope.() -> Unit = {},
    hiddenContent: @Composable ColumnScope.() -> Unit,
    peekContent: @Composable ColumnScope.() -> Unit,
) {
    BoxWithConstraints(modifier) {
        // Calculate drawer y coordinates for the collapsed and expanded states
        val fullHeight = constraints.maxHeight.toFloat()
        val fullWidth = constraints.maxWidth.toFloat()
        val expandHeightPx = with(LocalDensity.current) { expandHeight.toPx() }
        val collapseHeightPx = with(LocalDensity.current) { collapseHeight.toPx() }
        val expandedY = fullHeight - expandHeightPx
        val collapsedY = fullHeight - collapseHeightPx

        // Set up swipeable modifier fields
        val swipeableState = rememberSwipeableState(initialValue = DrawerState.Collapsed)
        val anchors = mapOf(collapsedY to DrawerState.Collapsed, expandedY to DrawerState.Expanded)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(swipeableState, anchors, Orientation.Vertical),
            contentAlignment = Alignment.CenterStart,
        ) {
            val spacerSize =
                if (swipeableState.currentValue == DrawerState.Expanded && hingeOccludes)
                    hingeSize
                else
                    0.dp

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                    .clip(DrawerShape)
                    .background(MaterialTheme.colors.surface),
            ) {
                val horizontalPadding = CONTENT_HORIZ_PADDING_PERECENT * fullWidth
                Column(
                    modifier = modifier.padding(horizontal = horizontalPadding.dp),
                ) {
                    pill()
                    peekContent()
                    Spacer(
                        Modifier
                            .size(spacerSize)
                            .animateContentSize()
                    )
                    hiddenContent()
                }
            }
        }
    }
}
