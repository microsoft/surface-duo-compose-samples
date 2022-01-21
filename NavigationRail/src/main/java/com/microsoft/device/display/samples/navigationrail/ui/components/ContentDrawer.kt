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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
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
 * @param expandHeight: height of the drawer when expanded (in dp)
 * @param collapseHeight: height of the drawer when collpased (in dp)
 * @param foldOccludes: optional param for foldable support, indicates whether there is a hinge
 * that occludes content in the current layout
 * @param foldBounds: optional param for foldable support, indicates the coordinates of the boundary
 * of a fold
 * @param windowHeight: optional param for foldable support, indicates the full height of the window
 * in which a fold and the content drawer are being displayed
 * @param hiddenContent: the content that will only be shown when the drawer is expanded
 * @param peekContent: the content that will be shown even when the drawer is collapsed
 */
@ExperimentalMaterialApi
@Composable
fun BoxWithConstraintsScope.ContentDrawer(
    modifier: Modifier = Modifier,
    expandHeight: Dp,
    collapseHeight: Dp,
    foldOccludes: Boolean = false,
    foldBounds: Rect = Rect(),
    windowHeight: Dp = 0.dp,
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

    // Calculate the height of each drawer component (top content, fold, bottom content)
    val foldSizePx = foldBounds.height()
    val foldSizeDp = with(LocalDensity.current) { foldSizePx.toDp() }
    val windowHeightPx = with(LocalDensity.current) { windowHeight.toPx() }
    val bottomContentMaxHeightPx = windowHeightPx - foldBounds.bottom
    val topContentMaxHeightPx = expandHeightPx - foldSizePx - bottomContentMaxHeightPx

    BoxWithConstraints(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .swipeable(swipeableState, anchors, Orientation.Vertical)
            .semantics { this.drawerState = swipeableState.currentValue }
            .testTag(stringResource(R.string.content_drawer)),
        contentAlignment = Alignment.TopStart,
    ) {
        // Check if a spacer needs to be included to render content around an occluding hinge
        val minSpacerHeight = calculateSpacerHeight(foldOccludes, swipeableState, foldSizeDp.value).toInt().dp

        // Calculate drawer height in dp based on swipe state
        val swipeOffsetDp = with(LocalDensity.current) { swipeableState.offset.value.toDp() }
        val drawerHeight = expandHeight - swipeOffsetDp

        Surface(
            modifier = Modifier
                .heightIn(collapseHeight, expandHeight)
                .height(drawerHeight)
                .clip(DrawerShape)
                .background(MaterialTheme.colors.surface),
            elevation = BottomSheetScaffoldDefaults.SheetElevation
        ) {
            // Calculate horizontal padding for drawer content
            val paddingPx = CONTENT_HORIZ_PADDING_PERECENT * constraints.maxWidth.toFloat()
            val paddingDp = with(LocalDensity.current) { paddingPx.toDp() }

//            Column(
//                modifier = Modifier.padding(horizontal = paddingDp),
//            ) {
//                Column(Modifier.fillMaxWidth()) { peekContent() }
//                Spacer(Modifier.heightIn(min = minSpacerHeight))
//                Column(Modifier.fillMaxSize()) { hiddenContent() }
//            }
            SeparatedColumn(
                modifier = Modifier.padding(horizontal = paddingDp),
                firstChildMaxHeightPx = topContentMaxHeightPx,
                foldSizePx = foldSizePx,
                calculateSpacerHeight = { fullHeight ->
                    calculateSpacerHeight(
                        foldOccludes,
                        swipeableState,
                        fullHeight
                    )
                }
            ) {
                Column(Modifier.fillMaxWidth()) { peekContent() }
                Spacer(Modifier.heightIn(min = minSpacerHeight))
                Column(Modifier.fillMaxSize()) { hiddenContent() }
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

/**
 * Custom layout component that displays three children in a column, where the first and third children
 * are placed at fixed coordinates and the second child is expanded to fill the remaining space
 *
 * @param modifier: Modifier for the layout
 * @param firstChildMaxHeightPx: the maximum height of the first child
 * @param foldSizePx: the minimum height of the second child
 * @param calculateSpacerHeight: a method to calculate the final height of the second child
 * @param content: content to display in the layout
 */
@Composable
private fun SeparatedColumn(
    modifier: Modifier,
    firstChildMaxHeightPx: Float,
    foldSizePx: Int,
    calculateSpacerHeight: (Float) -> Float,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Assert that the column has exactly 3 children
        if (measurables.size != 3) {
            throw IllegalArgumentException("Expected exactly 3 children for SeparatedColumn")
        }

        // Calculate the height of the children
        val firstChildHeightPx = measurables[0].minIntrinsicHeight(constraints.maxWidth)
        val thirdChildHeightPx = measurables[2].minIntrinsicHeight(constraints.maxWidth)
        val firstChildSpacePx = firstChildMaxHeightPx - firstChildHeightPx
        val secondChildHeightPx = calculateSpacerHeight(foldSizePx + firstChildSpacePx).toInt()
        val heights = listOf(firstChildHeightPx, secondChildHeightPx, thirdChildHeightPx)

        // Measure the children according to the custom layout constraints
        val placeables = measurables.mapIndexed { index, measurable ->
            measurable.measure(
                Constraints(0, constraints.maxWidth, heights[index], heights[index])
            )
        }

        // Layout the children vertically, like a column
        layout(constraints.maxWidth, constraints.minHeight) {
            var contentY = 0

            for (placeable in placeables) {
                placeable.placeRelative(x = 0, y = contentY)
                contentY += placeable.height
            }
        }
    }
}
