/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.ui.home

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun DualViewApp(viewModel: AppStateViewModel, windowState: WindowState) {
    TwoPaneLayout(
        pane1 = { RestaurantViewWithTopBar(windowState.isDualScreen(), viewModel) },
        pane2 = { MapViewWithTopBar(windowState.isDualScreen(), viewModel) }
    )
}

@Composable
fun ImageView(imageId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.FillWidth
    )
}
