/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.R

private val iconWidth = 25.dp
private val controlWidth = 70.dp

@Composable
fun MagicWandPanel(modifier: Modifier, sliderPosition: Float, onValueChange: (Float) -> Unit) {
    AdjustmentPanel(
        modifier,
        R.drawable.filter_icon,
        R.string.magic_wand,
        sliderPosition,
        onValueChange
    )
}

@Composable
fun DefinitionPanel(modifier: Modifier, sliderPosition: Float, onValueChange: (Float) -> Unit) {
    AdjustmentPanel(modifier, R.drawable.hdr_icon, R.string.definition, sliderPosition, onValueChange)
}

@Composable
fun VignettePanel(modifier: Modifier, sliderPosition: Float, onValueChange: (Float) -> Unit) {
    AdjustmentPanel(modifier, R.drawable.zoom_icon, R.string.vignette, sliderPosition, onValueChange)
}

@Composable
fun BrightnessPanel(modifier: Modifier, sliderPosition: Float, onValueChange: (Float) -> Unit) {
    AdjustmentPanel(modifier, R.drawable.brightness_icon, R.string.brightness, sliderPosition, onValueChange)
}

@Composable
private fun AdjustmentPanel(
    modifier: Modifier,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    sliderPosition: Float,
    onValueChange: (Float) -> Unit
) {
    Row {
        ImageWithText(icon, stringResource(label), iconWidth, controlWidth)
        SliderControl(modifier, sliderPosition, onValueChange)
    }
}
