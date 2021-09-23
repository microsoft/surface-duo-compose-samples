/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.navigationrail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.example.navigationrail.models.AppStateViewModel
import com.example.navigationrail.ui.theme.ComposeSamplesTheme
import com.example.navigationrail.ui.view.SetupUI

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()
        window.statusBarColor = Color.Transparent.toArgb()
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            ComposeSamplesTheme {
                SetupUI(windowInfoRep, appStateViewModel)
            }
        }
    }
}
