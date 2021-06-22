/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.twopanelayout

import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import com.microsoft.device.dualscreen.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.dualscreen.twopanelayout.screenState.LayoutState
import kotlin.math.roundToInt

@Composable
internal fun twoPaneMeasurePolicy(
    layoutState: LayoutState,
    orientation: LayoutOrientation,
    paneSize: Size,
    paddingBounds: Rect,
    mockConstraints: Constraints = Constraints(0, 0, 0, 0)
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        val childrenConstraints =
            if (mockConstraints == Constraints(0, 0, 0, 0))
                constraints
            else
                mockConstraints

        val twoPaneParentData = Array(measurables.size) { measurables[it].data }
        val placeables: List<Placeable>
        var totalWeight = 0f
        var maxWeight = 0f

        val childrenCount = measurables.count()
        require(childrenCount in 1..2) { "TwoPaneLayout requires at least 1 or 2 child elements" }

        for (i in measurables.indices) {
            val parentData = twoPaneParentData[i]
            val weight = parentData.weight
            if (weight > 0f) {
                totalWeight += weight
                maxWeight = weight.coerceAtLeast(maxWeight)
            }
        }

        if (layoutState == LayoutState.Fold) {
            var measurable = measurables.first() // only measure the first pane if no weight specified

            if (maxWeight != 0f) { // layout the (first) pane with the highest weight
                for (i in measurables.indices) {
                    val parentData = twoPaneParentData[i]
                    val weight = parentData.weight
                    if (weight == maxWeight) {
                        measurable = measurables[i]
                    }
                }
            }
            placeables = listOf(measurable.measure(childrenConstraints))
        } else if (maxWeight == 0f || maxWeight * 2 == totalWeight) {
            placeables = measureTwoPaneEqually(
                constraints = childrenConstraints,
                paneSize = paneSize,
                measurables = measurables
            )
        } else {
            placeables = measureTwoPaneProportionally(
                constraints = childrenConstraints,
                measurables = measurables,
                totalWeight = totalWeight,
                orientation = orientation,
                twoPaneParentData = twoPaneParentData
            )
        }

        when (layoutState) {
            LayoutState.Fold -> { // single pane(screen), only one placeable for Fold
                layout(childrenConstraints.maxWidth, childrenConstraints.maxHeight) {
                    val placeable = placeables.first()
                    placeable.place(x = 0, y = 0)
                }
            }

            LayoutState.Open -> {
                if (maxWeight == 0f || (maxWeight * 2 == totalWeight)) { // no weight will be layout equally by default
                    layout(childrenConstraints.maxWidth, childrenConstraints.maxHeight) {
                        placeables.forEachIndexed { index, placeable ->
                            placeTwoPaneEqually(
                                orientation = orientation,
                                placeable = placeable,
                                index = index,
                                paneSize = paneSize,
                                paddingBounds = paddingBounds,
                                constraints = childrenConstraints
                            )
                        }
                    }
                } else {
                    if (maxWeight == totalWeight) { // only one pane with weight
                        layout(childrenConstraints.maxWidth, childrenConstraints.maxHeight) {
                            for (i in placeables.indices) {
                                val parentData = twoPaneParentData[i]
                                val weight = parentData.weight
                                if (weight == maxWeight) {
                                    placeables[i].place(x = 0, y = 0)
                                    return@layout
                                }
                            }
                        }
                    } else { // two panes with different weight
                        layout(childrenConstraints.maxWidth, childrenConstraints.maxHeight) {
                            placeables.forEachIndexed { index, placeable ->
                                placeTwoPaneProportionally(
                                    orientation = orientation,
                                    placeable = placeable,
                                    index = index,
                                    twoPaneParentData = twoPaneParentData,
                                    constraints = childrenConstraints,
                                    totalWeight = totalWeight
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
 * to measure the pane for dual-screen without weight,
 * or dual-screen with two equal weight
 */
private fun measureTwoPaneEqually(
    constraints: Constraints,
    paneSize: Size,
    measurables: List<Measurable>
): List<Placeable> {
    var paneWidth = paneSize.width.toInt()
    var paneHeight = paneSize.height.toInt()
    val childConstraints = Constraints(
        minWidth = constraints.minWidth.coerceAtMost(paneWidth),
        minHeight = constraints.minHeight.coerceAtMost(paneHeight),
        maxWidth = constraints.maxWidth.coerceAtMost(paneWidth),
        maxHeight = constraints.maxHeight.coerceAtMost(paneHeight)
    )
    var placeables = measurables.map { it.measure(childConstraints) }
    return placeables
}

/*
 * to measure the pane for dual-screen with two non-equal weight
 */
private fun measureTwoPaneProportionally(
    constraints: Constraints,
    measurables: List<Measurable>,
    totalWeight: Float,
    orientation: LayoutOrientation,
    twoPaneParentData: Array<TwoPaneParentData?>
): List<Placeable> {
    val minWidth = constraints.minWidth
    val minHeight = constraints.minHeight
    val maxWidth = constraints.maxWidth
    val maxHeight = constraints.maxHeight

    val placeables = emptyList<Placeable>().toMutableList()
    for (i in measurables.indices) {
        val parentData = twoPaneParentData[i]
        val weight = parentData.weight
        var childConstraints: Constraints
        val ratio = weight / totalWeight
        childConstraints = if (orientation == LayoutOrientation.Vertical) {
            Constraints(
                minWidth = (minWidth * ratio).roundToInt(),
                minHeight = minHeight,
                maxWidth = (maxWidth * ratio).roundToInt(),
                maxHeight = maxHeight
            )
        } else {
            Constraints(
                minWidth = minWidth,
                minHeight = (minHeight * ratio).roundToInt(),
                maxWidth = maxWidth,
                maxHeight = (maxHeight * ratio).roundToInt()
            )
        }

        val placeable = measurables[i].measure(childConstraints)
        placeables.add(placeable)
    }
    return placeables
}

private fun Placeable.PlacementScope.placeTwoPaneEqually(
    orientation: LayoutOrientation,
    placeable: Placeable,
    index: Int,
    paneSize: Size,
    paddingBounds: Rect,
    constraints: Constraints
) {
    if (orientation == LayoutOrientation.Vertical) {
        var xPosition = 0 // for the first pane
        if (index != 0) { // for the second pane
            val lastPaneWidth = paneSize.width.toInt() - paddingBounds.right
            val firstPaneWidth = constraints.maxWidth - lastPaneWidth
            xPosition += firstPaneWidth
        }
        placeable.place(x = xPosition, y = 0)
    } else {
        var yPosition = 0
        if (index != 0) {
            val lastPaneHeight = paneSize.height.toInt() - paddingBounds.bottom
            val firstPaneHeight = constraints.maxHeight - lastPaneHeight
            yPosition += firstPaneHeight
        }
        placeable.place(x = 0, y = yPosition)
    }
}

private fun Placeable.PlacementScope.placeTwoPaneProportionally(
    orientation: LayoutOrientation,
    placeable: Placeable,
    index: Int,
    twoPaneParentData: Array<TwoPaneParentData?>,
    constraints: Constraints,
    totalWeight: Float
) {
    if (orientation == LayoutOrientation.Vertical) {
        var xPosition = 0 // for the first pane
        if (index != 0) { // for the second pane
            val parentData = twoPaneParentData[index]
            val weight = parentData.weight
            val ratio = 1f - (weight / totalWeight)
            val firstPaneWidth = (constraints.maxWidth * ratio).roundToInt()
            xPosition += firstPaneWidth
        }
        placeable.place(x = xPosition, y = 0)
    } else {
        var yPosition = 0
        if (index != 0) {
            val parentData = twoPaneParentData[index]
            val weight = parentData.weight
            val ratio = 1f - (weight / totalWeight)
            val firstPaneHeight = (constraints.maxHeight * ratio).roundToInt()
            yPosition += firstPaneHeight
        }
        placeable.place(x = 0, y = yPosition)
    }
}

private val IntrinsicMeasurable.data: TwoPaneParentData?
    get() = parentData as? TwoPaneParentData

private val TwoPaneParentData?.weight: Float
    get() = this?.weight ?: 0f

/**
 * Parent data associated with children.
 */
internal data class TwoPaneParentData(
    var weight: Float = 0f,
)

internal class LayoutWeightImpl(
    val weight: Float,
    inspectorInfo: InspectorInfo.() -> Unit
) : ParentDataModifier, InspectorValueInfo(inspectorInfo) {
    override fun Density.modifyParentData(parentData: Any?) =
        ((parentData as? TwoPaneParentData) ?: TwoPaneParentData()).also {
            it.weight = weight
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? LayoutWeightImpl ?: return false
        return weight != otherModifier.weight
    }

    override fun hashCode(): Int {
        var result = weight.hashCode()
        result *= 31
        return result
    }

    override fun toString(): String =
        "LayoutWeightImpl(weight=$weight)"
}
