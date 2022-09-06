/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope
import kotlin.math.roundToInt

private const val nonSelection = -1

@Composable
fun TwoPaneScope.MapViewWithTopBar(isDualLandscape: Boolean, selectedIndex: Int) {
    Scaffold(
        topBar = { if (!isDualLandscape) MapTopBar() }
    ) {
        MapView(selectedIndex, it)
    }
}

@Composable
fun TwoPaneScope.MapTopBar() {
    TopAppBar(
        modifier = Modifier.testTag(stringResource(R.string.map_top_bar)),
        title = {
            Text(
                text = if (isSinglePane) stringResource(R.string.app_name) else "",
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
        },
        actions = {
            if (this@MapTopBar.isSinglePane) {
                IconButton(onClick = { this@MapTopBar.navigateToPane1() }) {
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
fun MapView(selectedIndex: Int, paddingValues: PaddingValues) {
    var selectedMapId = R.drawable.unselected_map
    var selectedMapDescription = stringResource(id = R.string.map_description)

    if (selectedIndex > nonSelection) {
        selectedMapId = restaurants[selectedIndex].mapImageResourceId
        selectedMapDescription = stringResource(
            id = R.string.map_description_selected,
            stringResource(restaurants[selectedIndex].title)
        )
    }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .clipToBounds()
            .testTag(
                stringResource(R.string.map_image)
            )
    ) {
        ScalableImageView(imageId = selectedMapId, mapDescription = selectedMapDescription)
    }
}

@Composable
fun ScalableImageView(@DrawableRes imageId: Int, mapDescription: String) {
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
        contentDescription = mapDescription,
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
