package com.microsoft.device.display.samples.listdetail.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = darkGray,
    primaryVariant = darkGray,
    onPrimary = darkGray,
    secondary = teal200,
    background = Color.Black
)

private val LightColorPalette = lightColors(
    primary = darkGray,
    primaryVariant = darkGray,
    onPrimary = darkGray,
    secondary = teal200,
    background = Color.Black
)

@Composable
fun ListDetailComposeSampleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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
