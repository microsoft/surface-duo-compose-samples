/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = lightBlue,
    primaryVariant = lightBlue,
    secondary = teal200,
    background = Color.Black,
    onBackground = Color.White,
    onPrimary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = lightBlue,
    primaryVariant = lightBlue,
    secondary = teal200,
    background = Color.Black,
    onBackground = Color.White,
    onPrimary = Color.White,
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
