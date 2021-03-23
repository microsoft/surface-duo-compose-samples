/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.window.DisplayFeature
import androidx.window.WindowManager
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.models.ScreenState
import com.microsoft.device.display.samples.dualview.ui.home.SetupUI
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewComposeSampleTheme
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }
    private val initialSelection = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)
        appStateViewModel.setSelectionLiveData(initialSelection)

        super.onCreate(savedInstanceState)
        setContent {
            DualViewComposeSampleTheme {
                SetupUI(appStateViewModel)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                reserveScreenState(windowLayoutInfo.displayFeatures)
                windowLayoutInfo.displayFeatures
            }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback {}
    }

    private fun reserveScreenState(displayFeatures: List<DisplayFeature>) {
        var screenState = ScreenState.SingleScreen
        var viewWidth = 0
        val isScreenSpanned = displayFeatures.isNotEmpty()
        if (isScreenSpanned) {
            val vWidth = displayFeatures.first().bounds.left
            val isDualLandscape = vWidth == 0
            if (isDualLandscape) {
                viewWidth = displayFeatures.first().bounds.right
                screenState = ScreenState.DualLandscape
            } else {
                viewWidth = vWidth
                screenState = ScreenState.DualPortrait
            }
        }

        appStateViewModel.setScreenStateLiveData(screenState)
        appStateViewModel.viewWidth = viewWidth
    }
}
