package com.microsoft.device.display.samples.chat.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import java.nio.file.Files.size

class ChatBubbleLeftArrowShape(private val offsetY: Float = 0f) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            val arcWidth = 45f
            val arcHeight = 50f
            arcTo(
                Rect(0f, -arcHeight / 2 + offsetY, arcWidth, arcHeight / 2 + offsetY),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
            lineTo(x = arcWidth / 2, y = arcHeight + offsetY)
            arcTo(
                Rect(0f, -arcHeight / 2 + offsetY, arcWidth, arcHeight + offsetY),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }
        return Outline.Generic(path = trianglePath)
    }
}
class ChatBubbleRightArrowShape(private val offsetY: Float = 0f) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            val arcWidth = 45f
            val arcHeight = 50f
            arcTo(
                Rect(0f, -arcHeight / 2 + offsetY, arcWidth, arcHeight / 2 + offsetY),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(x = arcWidth / 2, y = arcHeight + offsetY)
            arcTo(
                Rect(0f, -arcHeight / 2 + offsetY, arcWidth, arcHeight + offsetY),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
        }
        return Outline.Generic(path = trianglePath)
    }
}
