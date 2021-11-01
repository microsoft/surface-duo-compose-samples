/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            var isAppSpanned by remember { mutableStateOf(false) }
            CollectWindowLayoutInfo(windowInfoRep) { value -> isAppSpanned = value }

            ComposeGalleryTheme {
                ComposeGalleryApp(appStateViewModel, isAppSpanned)
            }
        }
    }
}

@Composable
fun CollectWindowLayoutInfo(
    windowInfoRep: WindowInfoRepository,
    updateAppSpanned: (Boolean) -> Unit
) {
    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                updateAppSpanned(newLayoutInfo.displayFeatures.isNotEmpty())
            }
    }
}
