package com.microsoft.device.display.samples.extendedcanvas.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = teal700,
    primaryVariant = teal500,
    secondary = teal200
)

private val LightColorPalette = lightColors(
    primary = teal700,
    primaryVariant = teal500,
    secondary = teal200
)

@Composable
fun ExtendedCanvasAppsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
