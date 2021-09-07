/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            ComposeGalleryTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                BasicText(
                                    text = stringResource(R.string.app_name),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colors.onPrimary
                                    )
                                )
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            elevation = 10.dp
                        )
                    },
                    content = {
                        Home(appStateViewModel, windowInfoRep)
                    }
                )
            }
        }
    }
}
