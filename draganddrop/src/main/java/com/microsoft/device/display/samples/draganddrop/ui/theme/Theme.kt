package com.microsoft.device.display.samples.draganddrop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = mainBlue,
    primaryVariant = mainBlue,
    onPrimary = Color.White,
    background = Color.White
)

private val LightColorPalette = lightColors(
    primary = mainBlue,
    primaryVariant = mainBlue,
    onPrimary = Color.White,
    background = Color.White
)

@Composable
fun ComposeSamplesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}