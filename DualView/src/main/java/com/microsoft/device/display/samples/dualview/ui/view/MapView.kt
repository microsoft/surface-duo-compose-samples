/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1
import kotlin.math.roundToInt

private const val nonSelection = -1

@Composable
fun MapViewWithTopBar(isDualScreen: Boolean, isHorizontalFold: Boolean, selectedIndex: Int) {
    Scaffold(
        topBar = { if (isDualScreen && !isHorizontalFold) MapTopBar(isDualScreen) }
    ) {
        MapView(selectedIndex)
    }
}

@Composable
fun MapTopBar(isDualScreen: Boolean) {
    TopAppBar(
        modifier = Modifier.testTag(stringResource(R.string.map_top_bar)),
        title = {
            Text(
                text = if (!isDualScreen) stringResource(R.string.app_name) else "",
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
        },
        actions = {
            if (!isDualScreen) {
                IconButton(onClick = { navigateToPane1() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_list),
                        contentDescription = stringResource(R.string.switch_to_rest),
                        tint = Color.White
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun MapView(selectedIndex: Int) {
    var selectedMapId = R.drawable.unselected_map
    var selectedTitleId = R.string.map_description
    if (selectedIndex > nonSelection) {
        selectedMapId = restaurants[selectedIndex].mapImageResourceId
        selectedTitleId = restaurants[selectedIndex].description
    }
    Box(
        modifier = Modifier
            .clipToBounds()
            .testTag(
                stringResource(R.string.map_image)
            )
    ) {
        ScalableImageView(
            imageId = selectedMapId, selectedTitleId
        )
    }
}

@Composable
fun ScalableImageView(@DrawableRes imageId: Int, @StringRes descriptionId: Int) {
    val minScale = 1f
    val maxScale = 8f
    val defaultScale = 2f

    var scale by remember { mutableStateOf(defaultScale) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    Image(
        painter = painterResource(id = imageId),
        contentDescription = stringResource(id = descriptionId),
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
