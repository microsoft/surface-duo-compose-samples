/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.window.DisplayFeature
import androidx.window.FoldingFeature
import androidx.window.WindowManager
import com.microsoft.device.display.samples.twopage.models.AppStateViewModel
import com.microsoft.device.display.samples.twopage.ui.home.SetupUI
import com.microsoft.device.display.samples.twopage.ui.theme.SurfaceDuoComposeSamplesTheme
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    override fun onCreate(savedInstanceState: Bundle?) {
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContent {
            SurfaceDuoComposeSamplesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SetupUI(viewModel = appStateViewModel)
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                reserveScreenState(windowLayoutInfo.displayFeatures)
            }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback {}
    }

    private fun reserveScreenState(displayFeatures: List<DisplayFeature>) {
        var isDualMode = false
        var viewWidth = 0
        var hingeWidth = 0

        val isScreenSpanned = displayFeatures.isNotEmpty()
        if (isScreenSpanned) {
            val foldingFeature = displayFeatures.first() as FoldingFeature
            isDualMode = foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
            val vWidth = foldingFeature.bounds.left
            viewWidth = (vWidth / resources.displayMetrics.density).toInt()
            hingeWidth = foldingFeature.bounds.width()
        }

        appStateViewModel.setIsDualModeLiveDataLiveData(isDualMode)
        appStateViewModel.screenWidth = viewWidth
        appStateViewModel.hingeWidth = hingeWidth
    }
}
