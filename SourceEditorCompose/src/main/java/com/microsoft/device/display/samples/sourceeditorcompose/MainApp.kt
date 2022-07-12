/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.sourceeditorcompose


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.microsoft.device.display.samples.sourceeditorcompose.ui.pages.EditorPage
import com.microsoft.device.display.samples.sourceeditorcompose.ui.pages.PreviewPage
import com.microsoft.device.display.samples.sourceeditorcompose.util.FileOperations
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

/**
 * For handling window navigation for Surface Duo, this acts as a main Controller for both the Composable
 */
@Composable
fun MainApp() {
    val fileOperations = FileOperations()
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf(fileOperations.readFile("Source.html", context)) }
    val updateText: (String) -> Unit = { newText -> text = newText }

    TwoPaneLayout(
        pane1 = { EditorPage(text = text, updateText = updateText) },
        pane2 = { PreviewPage(text = text) }
    )
}

