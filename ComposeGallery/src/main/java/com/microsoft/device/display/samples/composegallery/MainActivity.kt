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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp()
            }
        }
    }
}
