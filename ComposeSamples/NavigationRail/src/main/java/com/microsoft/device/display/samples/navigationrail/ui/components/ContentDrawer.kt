/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp

private const val CONTENT_HORIZ_PADDING_PERECENT = 0.06f
private val DrawerShape = RoundedCornerShape(
    topStart = 25.dp,
    topEnd = 25.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
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
    // Calculate drawer y coordinates for the collapsed and expanded states
    val expandHeightPx = with(LocalDensity.current) { expandHeight.toPx() }
    val collapseHeightPx = with(LocalDensity.current) { collapseHeight.toPx() }
    val swipeHeightPx = expandHeightPx - collapseHeightPx

    // Set up swipeable modifier fields
    val swipeableState = rememberSwipeableState(initialValue = DrawerState.Collapsed)
    val anchors = mapOf(swipeHeightPx to DrawerState.Collapsed, 0f to DrawerState.Expanded)

    BoxWithConstraints(
        modifier = modifier.swipeable(swipeableState, anchors, Orientation.Vertical),
        contentAlignment = Alignment.TopStart,
    ) {
        // Check if a spacer needs to be included to render content around an occluding hinge
        val spacerHeight =
            if (swipeableState.currentValue == DrawerState.Expanded && hingeOccludes)
                hingeSize
            else
                0.dp

        // Calculate drawer height based on swipe state (height in dp)
        val swipeOffsetDp = with(LocalDensity.current) { swipeableState.offset.value.toDp() }
        val drawerHeight = expandHeight - swipeOffsetDp

        Surface(
            modifier = Modifier
                .heightIn(collapseHeight, expandHeight)
                .height(drawerHeight)
                .clip(DrawerShape)
                .background(MaterialTheme.colors.surface)
        ) {
            // Calculate horizontal padding for drawer content
            val paddingPx = CONTENT_HORIZ_PADDING_PERECENT * constraints.maxWidth.toFloat()
            val paddingDp = with(LocalDensity.current) { paddingPx.toDp() }

            Column(
                modifier = Modifier.padding(horizontal = paddingDp)
            ) {
                pill()
                peekContent()
                // REVISIT: animate size/appearance
                Spacer(
                    Modifier
                        .height(spacerHeight)
                        .animateContentSize()
                )
                hiddenContent()
            }
        }
    }
}
