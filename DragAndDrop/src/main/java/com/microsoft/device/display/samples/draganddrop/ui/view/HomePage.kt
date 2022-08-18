/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  
 */

package com.microsoft.device.display.samples.draganddrop.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.dualscreen.draganddrop.DragContainer
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope

@Composable
fun DragAndDropApp() {
    var dragText by remember { mutableStateOf<String?>(null) }
    var dragImage by remember { mutableStateOf<Painter?>(null) }
    val updateDragText: (String?) -> Unit = { newValue -> dragText = newValue }
    val updateDragImage: (Painter?) -> Unit = { newValue -> dragImage = newValue }

    DragContainer(modifier = Modifier.fillMaxSize()) {
        TwoPaneLayout(
            pane1 = { DragAndDropPane1(dragText, updateDragText, dragImage, updateDragImage) },
            pane2 = { DragAndDropPane2(dragText, updateDragText, dragImage, updateDragImage) }
        )
    }
}

@Composable
fun TwoPaneScope.DragAndDropPane1(
    dragText: String?,
    updateDragText: (String?) -> Unit,
    dragImage: Painter?,
    updateDragImage: (Painter?) -> Unit
) {
    if (isSinglePane) {
        DragAndDropSinglePane(dragText, updateDragText, dragImage, updateDragImage)
    } else {
        DragPaneWithTopBar()
    }
}

@Composable
fun DragAndDropPane2(
    dragText: String?,
    updateDragText: (String?) -> Unit,
    dragImage: Painter?,
    updateDragImage: (Painter?) -> Unit
) {
    DropPaneWithTopBar(dragText, updateDragText, dragImage, updateDragImage)
}

@Composable
fun DragAndDropSinglePane(
    dragText: String?,
    updateDragText: (String?) -> Unit,
    dragImage: Painter?,
    updateDragImage: (Painter?) -> Unit
) {
    Scaffold(
        topBar = {
            TopBarWithTitle()
        },
        floatingActionButton = {
            ResetFloatingActionButton(updateDragText, updateDragImage)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            DragPane(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(10.dp))
            DropPane(dragText, updateDragText, dragImage, updateDragImage, Modifier.weight(1f))
        }
    }
}

@Composable
fun TopBarWithTitle() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    )
}

@Composable
fun ResetFloatingActionButton(
    updateDragText: (String?) -> Unit,
    updateDragImage: (Painter?) -> Unit
) {
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.primary,
        onClick = {
            updateDragText(null)
            updateDragImage(null)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(id = R.string.reset_button_icon),
            tint = Color.White
        )
    }
}
