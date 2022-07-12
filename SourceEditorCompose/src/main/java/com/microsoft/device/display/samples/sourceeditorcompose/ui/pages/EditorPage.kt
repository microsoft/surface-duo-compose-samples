/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.sourceeditorcompose.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.microsoft.device.display.samples.sourceeditorcompose.R
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope

/**
 * EditorPage Composable contains the Editor for the HTML file
 */
@Composable
fun TwoPaneScope.EditorPage(text: String, updateText: (String) -> Unit) {
    val twoPaneScope = this
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                actions = {
                    if (twoPaneScope.isSinglePane) {
                        IconButton(onClick = { twoPaneScope.navigateToPane2() }) {
                            Text(text = stringResource(id = R.string.preview_button))
                        }
                    }
                }
            )
        }
    ) {
        TextField(
            value = text,
            onValueChange = { newText -> updateText(newText) },
            modifier = Modifier.fillMaxSize()
        )
    }
}