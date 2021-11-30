/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.window.layout.WindowInfoTracker
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.ui.home.SetupUI
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewComposeSampleTheme

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoTracker
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = WindowInfoTracker.getOrCreate(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            DualViewComposeSampleTheme {
                SetupUI(appStateViewModel, windowInfoRep)
            }
        }
    }
}
