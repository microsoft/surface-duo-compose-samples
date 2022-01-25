/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.extendedcanvas

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ExtendedCanvasApp() {
    Scaffold(
        topBar = { TitleTopBar() },
        content = { ScaleImage() }
    )
}

@Composable
fun TitleTopBar() {
    TopAppBar(
        modifier = Modifier.testTag(stringResource(R.string.top_bar)),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    )
}

@Composable
fun ScaleImage() {
    val minScale = 0.8f
    val maxScale = 6f
    var scale by remember { mutableStateOf(1.5f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }
    Image(
        painter = painterResource(id = R.drawable.mock_map),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .graphicsLayer(
                scaleX = maxOf(minScale, minOf(maxScale, scale)),
                scaleY = maxOf(minScale, minOf(maxScale, scale)),
            )
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .transformable(state = state)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        offset = offset.plus(dragAmount)
                    }
                )
            }
            .fillMaxSize(),
    )
}
