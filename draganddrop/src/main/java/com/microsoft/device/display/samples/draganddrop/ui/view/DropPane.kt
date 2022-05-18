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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.ui.theme.lightGray
import com.microsoft.device.display.samples.draganddrop.utils.DropContainer
import com.microsoft.device.display.samples.draganddrop.utils.MimeType

@Composable
fun DropPane() {
    var dragText = remember() {
        mutableStateOf<String?>(null)
    }
    var dragImage = remember() {
        mutableStateOf<Painter?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colors.primary) {}
        }
    ) {
        DropContainer(
            modifier = Modifier.fillMaxSize()
        ) { dragData ->
            dragData?.let {
                if (dragData.type == MimeType.TEXT_PLAIN) dragText.value = dragData.data as String
                if (dragData.type == MimeType.IMAGE_JPEG) dragImage.value = dragData.data as Painter
            }
            DropPaneContent(dragText.value, dragImage.value)
        }
    }
}

@Composable
fun DropPaneContent(dragText: String?, dragImage: Painter?) {
    Row {
        DropImageBox(dragImage)
        Spacer(modifier = Modifier.width(10.dp))
        DropTextBox(dragText)
    }
}

@Composable
fun RowScope.DropImageBox(dragImage: Painter?) {
    Box(modifier = Modifier
        .weight(1f)
        .fillMaxSize()
        .background(lightGray)
        .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (dragImage != null) {
            Image(
                painter = dragImage,
                contentDescription = null
            )
        } else {
            DropImagePlaceholder()
        }
    }
}

@Composable
fun DropImagePlaceholder() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.drag_and_drop_ic_photo_black),
            contentDescription = stringResource(R.string.image_contentDescription)
        )
        Text(
            text = stringResource(R.string.drag_image_text),
            style = typography.caption
        )
    }
}

@Composable
fun RowScope.DropTextBox(text: String?) {
    Box(modifier = Modifier
        .weight(1f)
        .fillMaxSize()
        .background(lightGray)
        .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (text != null) {
            Text(text = text,
                style = typography.body1
            )
        } else {
            DropTextPlaceholder()
        }
    }
}

@Composable
fun DropTextPlaceholder() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.drag_and_drop_ic_text_fields_black),
            contentDescription = stringResource(R.string.image_contentDescription)
        )
        Text(
            text = stringResource(R.string.drag_text_text),
            style = typography.caption
        )
    }
}
