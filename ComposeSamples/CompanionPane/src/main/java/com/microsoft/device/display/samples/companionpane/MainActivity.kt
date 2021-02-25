/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  *
 *
 */

package com.microsoft.device.display.samples.companionpane

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.util.Consumer
import androidx.lifecycle.ViewModelProvider
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import com.microsoft.device.display.samples.companionpane.ui.CompanionPaneAppsTheme
import com.microsoft.device.display.samples.companionpane.viewModels.AppStateViewModel
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }
    private val layoutStateChangeCallback = LayoutStateChangeCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)

        super.onCreate(savedInstanceState)
        setContent {
            CompanionPaneAppsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                BasicText(
                                    text = stringResource(R.string.app_name),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            },
                        )
                    },
                    content = {
                        SetupUI(viewModel = appStateViewModel)
                    }
                )
            }
        }
    }

    // Checks the orientation of the screen
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val isPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        windowManager.registerLayoutChangeCallback(mainThreadExecutor, layoutStateChangeCallback)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback(layoutStateChangeCallback)
    }

    inner class LayoutStateChangeCallback : Consumer<WindowLayoutInfo> {
        override fun accept(newLayoutInfo: WindowLayoutInfo) {
            val isScreenSpanned = newLayoutInfo.displayFeatures.size > 0
            appStateViewModel.setIsScreenSpannedLiveData(isScreenSpanned)
        }
    }
}
