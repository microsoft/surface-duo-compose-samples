/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.window

import android.content.res.Configuration
import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

data class WindowState(
    val hasFold: Boolean,
    val isFoldHorizontal: Boolean,
    val foldBounds: Rect,
    val widthSizeClass: WindowSizeClass,
    val heightSizeClass: WindowSizeClass,
) {
    // REVISIT: should we keep getters or just use public vals to access info?
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

    fun isDualScreen(): Boolean {
        return hasFold || isLargeScreen()
    }

    // REVISIT: should width/height ratio be used instead of orientation?
    @Composable
    private fun isPortrait(): Boolean {
        return LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
        // NOTE: refers to orientation of entire device, not just the current window
        // So, for large screens, a portrait large screen device would be in dual landscape mode
    }

    val windowMode: WindowMode
        @Composable get() = when (isDualScreen()) {
            true -> {
                // REVISIT: get backend internal error when calling isLargeScreen and isPortrait on the same line
                val isPortrait = isPortrait()
                if ((hasFold && isFoldHorizontal) || (isLargeScreen() && isPortrait))
                    WindowMode.DUAL_LANDSCAPE
                else
                    WindowMode.DUAL_PORTRAIT
            }
            false -> {
                if (isPortrait())
                    WindowMode.SINGLE_PORTRAIT
                else
                    WindowMode.SINGLE_LANDSCAPE
            }
        }

    @Composable
    fun isDualPortrait(): Boolean {
        return windowMode == WindowMode.DUAL_PORTRAIT
    }

    @Composable
    fun isDualLandscape(): Boolean {
        return windowMode == WindowMode.DUAL_LANDSCAPE
    }

    @Composable
    fun isSinglePortrait(): Boolean {
        return windowMode == WindowMode.SINGLE_PORTRAIT
    }

    @Composable
    fun isSingleLandscape(): Boolean {
        return windowMode == WindowMode.SINGLE_LANDSCAPE
    }
}
