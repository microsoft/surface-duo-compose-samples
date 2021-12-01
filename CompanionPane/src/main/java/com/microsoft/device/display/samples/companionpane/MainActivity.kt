/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.companionpane.ui.CompanionPaneAppsTheme
import com.microsoft.device.dualscreen.window.WindowState
import com.microsoft.device.dualscreen.window.rememberWindowState

class MainActivity : AppCompatActivity() {
    private lateinit var windowState: WindowState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            windowState = rememberWindowState()

            CompanionPaneAppsTheme {
                CompanionPaneApp(windowState)
            }
        }
    }
}
