/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.window_info

import android.content.res.Configuration
import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

data class WindowInfo(
    val hasFold: Boolean,
    val isFoldHorizontal: Boolean,
    val foldBounds: Rect,
    val widthSizeClass: WindowSizeClass,
    val heightSizeClass: WindowSizeClass,
) {
    fun getFoldSize(): Int {
        if (!hasFold)
            return 0

        return when (isFoldHorizontal) {
            true -> foldBounds.height()
            false -> foldBounds.width()
        }
    }

    // REVISIT: should height also be considered?
    fun isLargeScreen(): Boolean {
        return !hasFold && widthSizeClass != WindowSizeClass.COMPACT
    }

    @Composable
    fun isDualPortrait(): Boolean {
        return when (hasFold) {
            true -> !isFoldHorizontal
            false -> {
                val isLandscape =
                    LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
                return isLargeScreen() && isLandscape
            }
        }
    }

    @Composable
    fun isDualLandscape(): Boolean {
        return when (hasFold) {
            true -> isFoldHorizontal
            false -> {
                val isPortrait =
                    LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
                return isLargeScreen() && isPortrait
            }
        }
    }

    fun isDualScreen(): Boolean {
        return hasFold || isLargeScreen()
    }
}
