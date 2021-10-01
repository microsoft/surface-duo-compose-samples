/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Ming,
    primaryVariant = MingTranslucent,
    onPrimary = OffWhite,
    secondary = MiddleBlueGreen,
    secondaryVariant = MiddleBlueGreenTranslucent,
    onSecondary = Ming,
    surface = AliceBlue,
    onSurface = Ming,
    background = AliceBlue,
    onBackground = Ming,
)

private val LightColorPalette = lightColors(
    primary = Ming,
    primaryVariant = MingTranslucent,
    onPrimary = OffWhite,
    secondary = MiddleBlueGreen,
    secondaryVariant = MiddleBlueGreenTranslucent,
    onSecondary = Ming,
    surface = AliceBlue,
    onSurface = Ming,
    background = AliceBlue,
    onBackground = Ming,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeSamplesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
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
