/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.sourceeditorcompose

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.device.display.samples.sourceeditorcompose.ui.theme.TwoPaneExampleTheme
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoPaneExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ControllerLayer()
                }
            }
        }
    }
}

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

/**
 * For handling window navigation for Surface Duo, this acts as a main Controller for both the Composable
 */
@Composable
fun ControllerLayer() {
    val context = LocalContext.current
    val fileName = "Source.html"
    var text by rememberSaveable { mutableStateOf(readFile(fileName, context)) }
    val updateText: (String) -> Unit = { newText -> text = newText }

    TwoPaneLayout(
        pane1 = { EditorPage(text = text, updateText = updateText) },
        pane2 = { PreviewPage(text = text) }
    )
}

/**
 * To read a file from assets folder, which currently contains the local Source.html file
 */
private fun readFile(file: String, context: Context?): String {
    return BufferedReader(InputStreamReader(context?.assets?.open(file))).useLines { lines ->
        val results = StringBuilder()
        lines.forEach { results.append(it + System.getProperty("line.separator")) }
        results.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TwoPaneExampleTheme {
        Text("Test")
    }
}
