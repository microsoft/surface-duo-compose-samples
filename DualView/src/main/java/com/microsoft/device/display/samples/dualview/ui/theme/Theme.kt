/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = lightBlue,
    primaryVariant = lightBlue,
    secondary = gray500,
    onPrimary = Color.White,
)

@Composable
fun DualViewAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
