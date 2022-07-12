/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.sourceeditorcompose.ui.pages

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.device.display.samples.sourceeditorcompose.R
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope

/**
 * PreviewPage Composable displays the preview for the HTML code present in the editor and updates it if there are any changes in the editor file
 */
@Composable
fun TwoPaneScope.PreviewPage(text: String) {
    val twoPaneScope = this
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSinglePane) {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                },
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                actions = {
                    if (twoPaneScope.isSinglePane) {
                        IconButton(onClick = { twoPaneScope.navigateToPane1() }) {
                            Text(text = stringResource(R.string.editor_button))
                        }
                    }
                }
            )
        }
    ) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadData(text, "text/html", "UTF-8")
                }
            },
            update = {
                it.loadData(text, "text/html", "UTF-8")
            }
        )
    }
}