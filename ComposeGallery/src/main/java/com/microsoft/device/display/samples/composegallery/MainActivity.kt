/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import com.microsoft.device.dualscreen.window_info.WindowInfo
import com.microsoft.device.dualscreen.window_info.rememberWindowInfo

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfo: WindowInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            windowInfo = rememberWindowInfo()

            ComposeGalleryTheme {
                ComposeGalleryApp(windowInfo)
            }
        }
    }
}
