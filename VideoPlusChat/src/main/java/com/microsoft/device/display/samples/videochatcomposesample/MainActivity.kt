/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.videochatcomposesample.ui.theme.VideoChatComposeSampleTheme
import com.microsoft.device.display.samples.videochatcomposesample.ui.views.MainPage
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.dualscreen.windowstate.rememberWindowState

class MainActivity : ComponentActivity() {

    private lateinit var windowState: WindowState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            windowState = rememberWindowState()
            VideoChatComposeSampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainPage(windowState = windowState)
                }
            }
        }
    }
}
