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

// TODO Animate Using Constraint Layouts

/**
Levi O. Notes:
Currently the keyboard toggle is not the best when it comes to the vertical fold dual alignment (it is meant to go from video on top and chat on bottom
to a single pane row with chat and video alongside and keyboard on bottom).
I have tried to work with shifting focuses as you can see a small example in the MainPage.kt at the bottom, but this does not work, at least with how
I implemented it. I believe a better way to do this is to embed the app's composable into views and use constraint layouts and a global keyboard listener,
however I believe this would take away from the idea of creating a pure compose application and thus I have not done it.
 */

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
