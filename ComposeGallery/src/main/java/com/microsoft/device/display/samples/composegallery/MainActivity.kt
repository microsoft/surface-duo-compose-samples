/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import kotlinx.coroutines.flow.collect

enum class WindowSizeClass { Compact, Medium, Expanded }
data class FoldableState(val hasFold: Boolean, val isFoldHorizontal: Boolean)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val widthSizeClass = rememberWidthSizeClass()
            val foldableState = rememberFoldableState()

            ComposeGalleryTheme {
                ComposeGalleryApp(foldableState, widthSizeClass)
            }
        }
    }
}

@Composable
fun Activity.rememberFoldableState(): FoldableState {
    val windowInfoRepo = windowInfoRepository()
    var hasFold by remember { mutableStateOf(false) }
    var isFoldHorizontal by remember { mutableStateOf(false) }

    LaunchedEffect(windowInfoRepo) {
        windowInfoRepo.windowLayoutInfo.collect { newLayoutInfo ->
            hasFold = newLayoutInfo.displayFeatures.isNotEmpty()
            if (hasFold) {
                val fold = newLayoutInfo.displayFeatures.firstOrNull() as? FoldingFeature
                fold?.let {
                    isFoldHorizontal = it.orientation == FoldingFeature.Orientation.HORIZONTAL
                }
            }
        }
    }

    return FoldableState(hasFold, isFoldHorizontal)
}

/**
 * Implementation taken from JetNews sample
 * https://github.com/android/compose-samples/blob/main/JetNews/app/src/main/java/com/example/jetnews/utils/WindowSize.kt
 *
 * Remembers the [WindowSizeClass] class for the window corresponding to the current window metrics.
 */
@Composable
fun rememberWidthSizeClass(): WindowSizeClass {
    // Get the size (in pixels) of the window
    val windowSize = rememberWindowSize()

    // Calculate the width window size class
    return getWindowSizeClass(windowSize)
}

/**
 * Remembers the [Size] in pixels of the window corresponding to the current window metrics.
 */
@Composable
private fun rememberWindowSize(): Dp {
    val configuration = LocalConfiguration.current

    val windowMetrics = remember(configuration) {
        configuration.smallestScreenWidthDp.dp
    }
    return windowMetrics
}

/**
 * Partitions a [Dp] into a enumerated [WindowSizeClass] class.
 */
@VisibleForTesting
fun getWindowSizeClass(windowDpWidth: Dp): WindowSizeClass = when {
    windowDpWidth < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
    windowDpWidth < 600.dp -> WindowSizeClass.Compact
    windowDpWidth < 840.dp -> WindowSizeClass.Medium
    else -> WindowSizeClass.Expanded
}
