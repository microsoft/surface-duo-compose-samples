package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.IntrinsicMeasurable
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
                maxWeight = maxWeight.coerceAtMost(weight)
            }
        }

        var minWidth = constraints.minWidth
        var minHeight = constraints.minHeight
        var maxWidth = constraints.maxWidth
        var maxHeight = constraints.maxHeight
        // Measure children when there is no weight specified
        if (maxWeight == 0f) {
            val childConstraints = Constraints(
                minWidth = minWidth.coerceAtMost(paneWidth),
                minHeight = minHeight.coerceAtMost(paneHeight),
                maxWidth = maxWidth.coerceAtLeast(paneWidth),
                maxHeight = maxHeight.coerceAtLeast(paneHeight)
            )
            placeables = measurables.map { it.measure(childConstraints) }
        } else {
            val children = emptyList<Placeable>().toMutableList()
            for (i in measurables.indices) {
                val parentData = twoPaneParentData[i]
                val weight = parentData.weight
                var childConstraints: Constraints
                if (weight > 0f) {
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
                    parentData.paneRatio = ratio
                } else {
                    childConstraints = Constraints(
                        minWidth = minWidth.coerceAtMost(paneWidth),
                        minHeight = minHeight.coerceAtMost(paneHeight),
                        maxWidth = maxWidth.coerceAtLeast(paneWidth),
                        maxHeight = maxHeight.coerceAtLeast(paneHeight)
                    )
                }
                val placeable = measurables[i].measure(childConstraints)
                children.add(placeable)
            }
            placeables = children
        }

        when (layoutState) {
            LayoutState.Fold -> {
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
                            }
                        }
                    }
                }
            }

            // TODO: layout according to the weight
            LayoutState.Open -> {
                if (orientation == LayoutOrientation.Vertical) {
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var xPosition = 0
                        for (i in placeables.indices) {
                            var lastPaneWidth = paneWidth - paddingBounds.right
                            var firstPaneWidth = constraints.maxWidth - lastPaneWidth

                            placeables[i].placeRelative(x = xPosition, y = 0)
                            xPosition += if (i == 0) {
                                firstPaneWidth
                            } else { // for the second pane
                                paneHeight + arrangementSpacing
                            }
                        }
                    }
                } else {
                    // calculate the first pane differently, due to the potential status bar, top app bar and bottom navigation bar
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var yPosition = 0
                        for (i in placeables.indices) {
                            var lastPaneHeight = paneHeight - paddingBounds.bottom
                            var firstPaneHeight = constraints.maxHeight - lastPaneHeight

                            placeables[i].placeRelative(x = 0, y = yPosition)
                            yPosition += if (i == 0) {
                                firstPaneHeight
                            } else { // for the second pane
                                paneHeight + arrangementSpacing
                            }
                        }
                    }
                }
            }
        }
    }
}

private val IntrinsicMeasurable.data: TwoPaneParentData?
    get() = parentData as? TwoPaneParentData

private val TwoPaneParentData?.weight: Float
    get() = this?.weight ?: 0f

private var TwoPaneParentData?.paneRatio: Float
    get() = this?.paneRatio ?: 0f
    set(value) { value }

/**
 * Parent data associated with children.
 */
internal data class TwoPaneParentData(
    var weight: Float = 0f,
    var paneRatio: Float = 0f
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
