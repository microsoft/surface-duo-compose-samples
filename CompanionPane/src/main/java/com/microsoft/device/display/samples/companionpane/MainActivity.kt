/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.window.layout.WindowInfoTracker
import com.microsoft.device.display.samples.companionpane.ui.CompanionPaneAppsTheme

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = WindowInfoTracker.getOrCreate(this)

        setContent {
            CompanionPaneAppsTheme {
                SetupUI(windowInfoRep)
            }
        }
    }
}
