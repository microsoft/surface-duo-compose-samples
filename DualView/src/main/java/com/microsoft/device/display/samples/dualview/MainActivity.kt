/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.ui.view.DualViewApp
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewComposeSampleTheme
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.dualscreen.windowstate.rememberWindowState

class MainActivity : AppCompatActivity() {
    private lateinit var windowState: WindowState
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            windowState = rememberWindowState()

            DualViewComposeSampleTheme {
                DualViewApp(appStateViewModel, windowState)
            }
        }
    }
}
