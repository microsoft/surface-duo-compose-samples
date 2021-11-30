/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.window.layout.WindowInfoTracker
import com.microsoft.device.display.samples.navigationrail.ui.theme.ComposeSamplesTheme
import com.microsoft.device.display.samples.navigationrail.ui.view.SetupUI

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = WindowInfoTracker.getOrCreate(this)

        setContent {
            ComposeSamplesTheme {
                // Set up app UI
                SetupUI(windowInfoRep)
            }
        }
    }
}
