/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.companionpane.R
import com.microsoft.device.display.samples.companionpane.ui.theme.Gray
import kotlin.random.Random

@Composable
fun SliderControl(modifier: Modifier) {
    val defaultValue = Random.nextInt(0, 100)
    var sliderPosition by remember { mutableStateOf(defaultValue.toFloat()) }
    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 0f..100f,
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.White,
            inactiveTrackColor = Gray
        ),
        modifier = modifier
    )
}

@Composable
fun LeftAlignText(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(Modifier.width(30.dp))
        BasicText(
            text = title,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ImageWithText(id: Int, text: String, imageWidth: Dp, width: Dp) {
    Column(
        modifier = Modifier.width(width),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = id),
            modifier = Modifier.width(imageWidth),
            alignment = Alignment.Center,
            contentDescription = null
        )
        BasicText(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun ImagePanel(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.full_image),
        modifier = modifier,
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center,
        contentDescription = null
    )
}
