/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.microsoft.device.display.samples.navigationrail.ui.theme.ComposeSamplesTheme
import com.microsoft.device.display.samples.navigationrail.ui.view.SetupUI

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()

        setContent {
            ComposeSamplesTheme {
                // Set up app UI
                SetupUI(windowInfoRep)
            }
        }
    }
}
