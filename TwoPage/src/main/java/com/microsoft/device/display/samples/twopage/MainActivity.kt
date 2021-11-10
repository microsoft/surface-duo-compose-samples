/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.microsoft.device.display.samples.twopage.ui.home.SetupUI
import com.microsoft.device.display.samples.twopage.ui.theme.TwoPageComposeSamplesTheme

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()

        setContent {
            TwoPageComposeSamplesTheme {
                SetupUI(windowInfoRep)
            }
        }
    }
}
