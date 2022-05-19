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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.utils.DragContainer
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope

@Composable
fun DragAndDropApp() {
    DragContainer(modifier = Modifier.fillMaxSize()) {
        TwoPaneLayout(
            pane1 = { DragAndDropPane1() },
            pane2 = { DragAndDropPane2() }
        )
    }
}

@Composable
fun TwoPaneScope.DragAndDropPane1() {
    if (isSinglePane) {
        DragAndDropSinglePane()
    } else {
        DragPaneWithTopBar()
    }
}

@Composable
fun DragAndDropPane2() {
    DropPaneWithTopBar()
}

@Composable
fun DragAndDropSinglePane() {
    Scaffold(
        topBar = {
            TopBarWithTitle()
        }
    ) {
        Column {
            DragPane(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(10.dp))
            DropPane(modifier = Modifier.weight(1f))
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
