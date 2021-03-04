/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.window.WindowManager
import com.microsoft.device.display.samples.twopage.models.AppStateViewModel
import com.microsoft.device.display.samples.twopage.ui.home.SetupUI
import com.microsoft.device.display.samples.twopage.ui.theme.SurfaceduocomposesamplesTheme
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    override fun onCreate(savedInstanceState: Bundle?) {
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)

        super.onCreate(savedInstanceState)
        setContent {
            SurfaceduocomposesamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SetupUI(viewModel = appStateViewModel)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val isPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                appStateViewModel.setIsScreenSpannedLiveData(windowLayoutInfo.displayFeatures.size > 0)
            }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback {}
    }
}