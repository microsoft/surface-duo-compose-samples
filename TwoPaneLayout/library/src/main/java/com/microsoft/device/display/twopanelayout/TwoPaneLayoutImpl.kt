package com.microsoft.device.display.twopanelayout

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
import com.microsoft.device.display.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.LayoutState

@PublishedApi
@Composable
internal fun twoPaneMeasurePolicy(
    layoutState: LayoutState,
    orientation: LayoutOrientation,
    paneSize: Size,
    arrangementSpacing: Int,
    paddingBounds: Rect
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        val paneWidth = paneSize.width.toInt()
        val paneHeight = paneSize.height.toInt()
        val twoPaneParentData = Array(measurables.size) { measurables[it].data }
        var placeables: List<Placeable>
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

        if (maxWeight == 0f) {
            placeables = measureTwoPaneEqually(
                constraints = constraints,
                paneWidth = paneWidth,
                paneHeight = paneHeight,
                measurables = measurables)
        } else {
            placeables = measureTwoPaneProportionally(
                constraints = constraints,
                measurables = measurables,
                totalWeight = totalWeight,
                orientation = orientation,
                twoPaneParentData = twoPaneParentData
            )
        }

        when (layoutState) {
            LayoutState.Fold -> { // single pane(screen)
                layout(constraints.maxWidth, constraints.maxHeight) {
                    if (maxWeight == 0f) { // only layout the first pane if no weight specified
                        val placeable = placeables.first()
                        placeable.place(x = 0, y = 0)
                    } else { // layout the (first) pane with the highest weight
                        for (i in placeables.indices) {
                            val parentData = twoPaneParentData[i]
                            val weight = parentData.weight
                            if (weight == maxWeight) {
                                placeables[i].place(x = 0, y = 0)
                                return@layout
                            }
                        }
                    }
                }
            }

            LayoutState.Open -> {
                if (maxWeight == 0f || (maxWeight * 2 == totalWeight)) { // no weight will be layout equally by default
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        placeables.forEachIndexed { index, placeable ->
                            placeTwoPaneEqually(
                                orientation = orientation,
                                placeable = placeable,
                                index = index,
                                paneWidth = paneWidth,
                                paneHeight = paneHeight,
                                paddingBounds = paddingBounds,
                                constraints = constraints,
                                arrangementSpacing = arrangementSpacing
                            )
                        }
                    }
                } else {
                    if (maxWeight == totalWeight) { // only one pane with weight
                        layout(constraints.maxWidth, constraints.maxHeight) {
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
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            placeables.forEachIndexed { index, placeable ->
                                placeTwoPaneProportionally(
                                    orientation = orientation,
                                    placeable = placeable,
                                    index = index,
                                    twoPaneParentData = twoPaneParentData,
                                    constraints = constraints,
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

private fun measureTwoPaneEqually(
    constraints: Constraints,
    paneWidth: Int,
    paneHeight: Int,
    measurables: List<Measurable>
): List<Placeable> {
    var minWidth = constraints.minWidth
    var minHeight = constraints.minHeight
    var maxWidth = constraints.maxWidth
    var maxHeight = constraints.maxHeight
    val childConstraints = Constraints(
        minWidth = minWidth.coerceAtMost(paneWidth),
        minHeight = minHeight.coerceAtMost(paneHeight),
        maxWidth = maxWidth.coerceAtLeast(paneWidth),
        maxHeight = maxHeight.coerceAtLeast(paneHeight)
    )
    var placeables = measurables.map { it.measure(childConstraints) }
    return placeables
}

private fun measureTwoPaneProportionally(
    constraints: Constraints,
    measurables: List<Measurable>,
    totalWeight: Float,
    orientation: LayoutOrientation,
    twoPaneParentData: Array<TwoPaneParentData?>
): List<Placeable> {
    var minWidth = constraints.minWidth
    var minHeight = constraints.minHeight
    var maxWidth = constraints.maxWidth
    var maxHeight = constraints.maxHeight

    val placeables = emptyList<Placeable>().toMutableList()
    for (i in measurables.indices) {
        val parentData = twoPaneParentData[i]
        val weight = parentData.weight
        var childConstraints: Constraints
        val ratio = weight / totalWeight
        childConstraints = if (orientation == LayoutOrientation.Vertical) {
            Constraints(
                minWidth = (minWidth * ratio).toInt(),
                minHeight = minHeight,
                maxWidth = (maxWidth * ratio).toInt(),
                maxHeight = maxHeight
            )
        } else {
            Constraints(
                minWidth = minWidth,
                minHeight = (minHeight  * ratio).toInt(),
                maxWidth = maxWidth,
                maxHeight = (maxHeight  * ratio).toInt()
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
    paneWidth: Int,
    paneHeight: Int,
    paddingBounds: Rect,
    constraints: Constraints,
    arrangementSpacing: Int
) {
    if (orientation == LayoutOrientation.Vertical) {
        var xPosition = 0
        var lastPaneWidth = paneWidth - paddingBounds.right
        var firstPaneWidth = constraints.maxWidth - lastPaneWidth
        placeable.placeRelative(x = xPosition, y = 0)
        xPosition += if (index == 0) { // calculate the first pane differently, due to the potential status bar, top app bar and bottom navigation bar
            firstPaneWidth
        } else { // for the second pane
            paneWidth + arrangementSpacing
        }

    } else {
        var yPosition = 0
        var lastPaneHeight = paneHeight - paddingBounds.bottom
        var firstPaneHeight = constraints.maxHeight - lastPaneHeight

        placeable.placeRelative(x = 0, y = yPosition)
        yPosition += if (index == 0) {
            firstPaneHeight
        } else {
            paneHeight + arrangementSpacing
        }
    }
}

private fun Placeable.PlacementScope.placeTwoPaneProportionally(
    orientation: LayoutOrientation,
    placeable: Placeable,
    index: Int,
    twoPaneParentData: Array<TwoPaneParentData?>,
    constraints: Constraints,
    totalWeight: Float
){
    if (orientation == LayoutOrientation.Vertical) {
        var xPosition = 0
        val parentData = twoPaneParentData[index]
        val weight = parentData.weight
        val ratio = weight / totalWeight
        var firstPaneWidth = (constraints.maxWidth * ratio).toInt()
        var secondPaneWidth = constraints.maxWidth - firstPaneWidth

        placeable.placeRelative(x = xPosition, y = 0)
        xPosition += if (index == 0) {
            firstPaneWidth
        } else { // for the second pane
            secondPaneWidth
        }
    } else {
        var yPosition = 0
        val parentData = twoPaneParentData[index]
        val weight = parentData.weight
        val ratio = weight / totalWeight
        var firstPaneHeight = (constraints.maxHeight * ratio).toInt()
        var secondPaneHeight = constraints.maxWidth - firstPaneHeight

        placeable.placeRelative(x = 0, y = yPosition)
        yPosition += if (index == 0) {
            firstPaneHeight
        } else {
            secondPaneHeight
        }
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
