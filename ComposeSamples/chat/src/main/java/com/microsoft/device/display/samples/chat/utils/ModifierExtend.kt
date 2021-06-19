package com.microsoft.device.display.samples.chat.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import kotlin.math.roundToInt

fun Modifier.percentOffsetX(percent: Float): Modifier =
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            val offset = (percent * placeable.width).roundToInt()
            placeable.placeRelative(offset, 0)
        }
    }
