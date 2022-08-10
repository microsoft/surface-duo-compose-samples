/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.ui.theme.lightGray
import com.microsoft.device.dualscreen.draganddrop.DragData
import com.microsoft.device.dualscreen.draganddrop.DragTarget
import com.microsoft.device.dualscreen.draganddrop.MimeType

@Composable
fun DragPaneWithTopBar() {
    Scaffold(
        topBar = { TopBarWithTitle() }
    ) {
        DragPane(paddingValues = it)
    }
}

@Composable
fun DragPane(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(lightGray)
            .testTag(stringResource(R.string.drag_pane))
    ) {
        DragImageBox()
        Spacer(modifier = Modifier.width(30.dp))
        DragTextBox()
    }
}

@Composable
fun RowScope.DragImageBox() {
    val dragImage = painterResource(id = R.drawable.drag_and_drop_image)
    val dragData = DragData(type = MimeType.IMAGE_JPEG, data = dragImage)

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        DragTarget(dragData = dragData) {
            Image(
                painter = dragImage,
                contentDescription = stringResource(R.string.image_contentDescription),
                // to get a fix frame of the shadow
                modifier = Modifier.size(width = 300.dp, height = 120.dp)
            )
        }
    }
}

@Composable
fun RowScope.DragTextBox() {
    val dragText = stringResource(R.string.drag_and_drop_plain_text)
    val dragData = DragData(type = MimeType.TEXT_PLAIN, data = dragText)

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        DragTarget(dragData = dragData) {
            Text(
                text = dragText,
                style = typography.body1,
                // to get a fix frame of the shadow
                modifier = Modifier.size(width = 250.dp, height = 300.dp)
            )
        }
    }
}
