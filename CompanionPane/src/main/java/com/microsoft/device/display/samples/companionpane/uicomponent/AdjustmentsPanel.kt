/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.uicomponent

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.R

val iconWidth = 25.dp
val controlWidth = 70.dp

@Composable
fun MagicWandPanel(modifier: Modifier) {
    Row() {
        ImageWithText(R.drawable.filter_icon, "Magic Wand", iconWidth, controlWidth)
        SliderControl(modifier)
    }
}

@Composable
fun DefinitionPanel(modifier: Modifier) {
    Row() {
        ImageWithText(R.drawable.hdr_icon, "Definition", iconWidth, controlWidth)
        SliderControl(modifier)
    }
}

@Composable
fun VignettePanel(modifier: Modifier) {
    Row() {
        ImageWithText(R.drawable.zoom_icon, "Vignette", iconWidth, controlWidth)
        SliderControl(modifier)
    }
}

@Composable
fun BrightnessPanel(modifier: Modifier) {
    Row() {
        ImageWithText(R.drawable.brightness_icon, "Brightness", iconWidth, controlWidth)
        SliderControl(modifier)
    }
}
