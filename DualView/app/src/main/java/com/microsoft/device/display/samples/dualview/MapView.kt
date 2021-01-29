package com.microsoft.device.display.samples.dualview.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.dualview.ImageView
import com.microsoft.device.display.samples.dualview.R

@Composable
fun MapView() {
    ImageView(R.drawable.unselected_map, modifier = Modifier.wrapContentSize())
}