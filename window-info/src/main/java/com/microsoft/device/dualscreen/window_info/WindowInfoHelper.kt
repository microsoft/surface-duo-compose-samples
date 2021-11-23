/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.window_info

import android.app.Activity
import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowMetricsCalculator
import kotlinx.coroutines.flow.collect

@Composable
fun Activity.rememberWindowInfo(): WindowInfo {
    val windowInfoRepo = windowInfoRepository()

    var hasFold by remember { mutableStateOf(false) }
    var isFoldHorizontal by remember { mutableStateOf(false) }
    var foldBounds by remember { mutableStateOf(Rect()) }

    LaunchedEffect(windowInfoRepo) {
        windowInfoRepo.windowLayoutInfo.collect { newLayoutInfo ->
            hasFold = newLayoutInfo.displayFeatures.isNotEmpty()
            if (hasFold) {
                val fold = newLayoutInfo.displayFeatures.firstOrNull() as? FoldingFeature
                fold?.let {
                    isFoldHorizontal = it.orientation == FoldingFeature.Orientation.HORIZONTAL
                    foldBounds = it.bounds
                }
            }
        }
    }

    val config = LocalConfiguration.current
    val windowMetrics = remember(config) {
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this).bounds
    }
    val windowWidth = config.smallestScreenWidthDp.dp
    // REVISIT: proper way to do it below, but need to figure out how to handle single landscape
    // mode on Surface Duo first (will think it's a large screen due to width)
//    val windowWidth = with(LocalDensity.current) { windowMetrics.width().toDp() }
    val windowHeight = with(LocalDensity.current) { windowMetrics.height().toDp() }
    val widthSizeClass = getWindowSizeClass(windowWidth)
    val heightSizeClass = getWindowSizeClass(windowHeight, Dimension.HEIGHT)

    return WindowInfo(hasFold, isFoldHorizontal, foldBounds, widthSizeClass, heightSizeClass)
}
