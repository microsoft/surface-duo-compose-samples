/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.components

import android.graphics.Rect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.R

private const val CONTENT_HORIZ_PADDING_PERECENT = 0.06f
private val DrawerShape = RoundedCornerShape(
    topStart = 25.dp,
    topEnd = 25.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)
val DrawerStateKey = SemanticsPropertyKey<DrawerState>("DrawerStateKey")
var SemanticsPropertyReceiver.drawerState by DrawerStateKey

enum class DrawerState { Collapsed, Expanded }

/**
 * Reference: BottomDrawer component code
 *
 * Custom drawer (bottom aligned) with a rounded corner shape that swipes between a collapsed
 * "peek" view and a more expanded view that displays all of the content
 *
 * Supports foldable displays by splitting the "peekContent" and "hiddenContent" around an occluding
 * fold
 *
 * @param modifier: optional Modifier to be applied to the layout
 * @param expandHeightDp: height of the drawer when expanded (in dp)
 * @param collapseHeightDp: height of the drawer when collpased (in dp)
 * @param foldOccludes: optional param for foldable support, indicates whether there is a hinge
 * that occludes content in the current layout
 * @param foldBoundsPx: optional param for foldable support, indicates the coordinates of the boundary
 * of a fold
 * @param windowHeightDp: optional param for foldable support, indicates the full height of the window
 * in which a fold and the content drawer are being displayed
 * @param foldBottomPaddingDp: optional param for foldable support, will be added as padding below the fold to
 * make content more accessible to users
 * @param hiddenContent: the content that will only be shown when the drawer is expanded
 * @param peekContent: the content that will be shown even when the drawer is collapsed
 */
@ExperimentalMaterialApi
@Composable
fun BoxWithConstraintsScope.ContentDrawer(
    modifier: Modifier = Modifier,
    expandHeightDp: Dp,
    collapseHeightDp: Dp,
    foldOccludes: Boolean = false,
    foldBoundsPx: Rect = Rect(),
    windowHeightDp: Dp = 0.dp,
    foldBottomPaddingDp: Dp = 0.dp,
    hiddenContent: @Composable ColumnScope.() -> Unit,
    peekContent: @Composable ColumnScope.() -> Unit,
) {
    // Calculate drawer y coordinates for the collapsed and expanded states
    val expandHeightPx = with(LocalDensity.current) { expandHeightDp.toPx() }
    val collapseHeightPx = with(LocalDensity.current) { collapseHeightDp.toPx() }
    val swipeHeightPx = expandHeightPx - collapseHeightPx

    // Set up swipeable modifier fields
    val swipeableState = rememberSwipeableState(initialValue = DrawerState.Collapsed)
    val anchors = mapOf(swipeHeightPx to DrawerState.Collapsed, 0f to DrawerState.Expanded)

    // Calculate the height of each drawer component (top content, fold, bottom content)
    val foldSizePx = foldBoundsPx.height()
    val foldSizeDp = with(LocalDensity.current) { foldSizePx.toDp() }
    val windowHeightPx = with(LocalDensity.current) { windowHeightDp.toPx() }
    val bottomContentMaxHeightPx = windowHeightPx - foldBoundsPx.bottom
    val topContentMaxHeightPx: Float = if (foldOccludes) {
        expandHeightPx - foldSizePx - bottomContentMaxHeightPx
    } else {
        collapseHeightPx
    }
    val topContentMaxHeightDp = with(LocalDensity.current) { topContentMaxHeightPx.toDp() }

    BoxWithConstraints(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .swipeable(swipeableState, anchors, Orientation.Vertical)
            .semantics { this.drawerState = swipeableState.currentValue }
            .testTag(stringResource(R.string.content_drawer)),
        contentAlignment = Alignment.TopStart,
    ) {
        // Check if a spacer needs to be included to render content around an occluding hinge
        val minSpacerHeight = calculateSpacerHeight(
            foldOccludes,
            swipeableState,
            foldSizeDp.value + foldBottomPaddingDp.value
        ).toInt().dp

        // Calculate drawer height in dp based on swipe state
        val swipeOffsetDp = with(LocalDensity.current) { swipeableState.offset.value.toDp() }
        val drawerHeight = expandHeightDp - swipeOffsetDp

        Surface(
            modifier = Modifier
                .heightIn(collapseHeightDp, expandHeightDp)
                .height(drawerHeight)
                .clip(DrawerShape)
                .background(MaterialTheme.colors.surface),
            elevation = BottomSheetScaffoldDefaults.SheetElevation
        ) {
            // Calculate horizontal padding for drawer content
            val paddingPx = CONTENT_HORIZ_PADDING_PERECENT * constraints.maxWidth.toFloat()
            val paddingDp = with(LocalDensity.current) { paddingPx.toDp() }

            val fillWidth = Modifier.fillMaxWidth()

            Column(
                modifier = Modifier.padding(horizontal = paddingDp),
            ) {
                Column(fillWidth.requiredHeight(topContentMaxHeightDp)) { peekContent() }
                Spacer(Modifier.requiredHeight(minSpacerHeight))
                hiddenContent()
            }
        }
    }
}

/**
 * Helper method to calculate the animated height of the spacer used for foldable support. Height
 * is progressively increased or decreased based on the swipe state.
 *
 * @param foldOccludes: whether or not a fold is present and occluding content
 * @param swipeableState: swipeable state of the component that contains a spacer
 * @param fullHeight: the desired full height of the spacer when the parent component has been swiped
 * to the expanded state
 *
 * @return the height of the spacer for the current swipe progress
 */
@ExperimentalMaterialApi
private fun calculateSpacerHeight(
    foldOccludes: Boolean,
    swipeableState: SwipeableState<DrawerState>,
    fullHeight: Float
): Float {
    if (!foldOccludes)
        return 0f

    val isExpanding = swipeableState.progress.to == DrawerState.Expanded
    val progressHeight = (fullHeight * swipeableState.progress.fraction)

    return if (isExpanding) progressHeight else fullHeight - progressHeight
}
