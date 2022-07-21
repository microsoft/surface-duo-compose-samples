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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.ui.theme.lightGray
import com.microsoft.device.display.samples.draganddrop.ui.theme.mediumGray
import com.microsoft.device.dualscreen.draganddrop.DropContainer
import com.microsoft.device.dualscreen.draganddrop.MimeType

@Composable
fun DropPaneWithTopBar(
    dragText: String?,
    updateDragText: (String?) -> Unit,
    dragImage: Painter?,
    updateDragImage: (Painter?) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colors.primary) {}
        },
        floatingActionButton = {
            ResetFloatingActionButton(updateDragText, updateDragImage)
        }
    ) {
        DropPane(dragText, updateDragText, dragImage, updateDragImage)
    }
}

@Composable
fun DropPane(
    dragText: String?,
    updateDragText: (String?) -> Unit,
    dragImage: Painter?,
    updateDragImage: (Painter?) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDroppingText by remember { mutableStateOf(false) }
    var isDroppingImage by remember { mutableStateOf(false) }
    var isDroppingItem by remember { mutableStateOf(false) }

    DropContainer(
        modifier = modifier
            .testTag(stringResource(R.string.drop_pane)),
        onDrag = { inBounds, isDragging ->
            if (!inBounds || !isDragging) {
                isDroppingText = false
                isDroppingImage = false
            }
            isDroppingItem = isDragging
        },
    ) { dragData ->
        dragData?.let {
            if (dragData.type == MimeType.TEXT_PLAIN) {
                isDroppingText = isDroppingItem
                if (!isDroppingItem) {
                    updateDragText(dragData.data as String)
                }
            }
            if (dragData.type == MimeType.IMAGE_JPEG) {
                isDroppingImage = isDroppingItem
                if (!isDroppingItem) {
                    updateDragImage(dragData.data as Painter)
                }
            }
        }
        DropPaneContent(dragText, isDroppingText, dragImage, isDroppingImage)
    }
}

@Composable
fun DropPaneContent(dragText: String?, isDroppingText: Boolean, dragImage: Painter?, isDroppingImage: Boolean) {
    Row {
        DropImageBox(dragImage, isDroppingImage)
        Spacer(modifier = Modifier.width(10.dp))
        DropTextBox(dragText, isDroppingText)
    }
}

@Composable
fun RowScope.DropImageBox(dragImage: Painter?, isDroppingImage: Boolean) {
    val boxColor = if (isDroppingImage) mediumGray else lightGray
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(boxColor)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (dragImage != null) {
            Image(
                painter = dragImage,
                contentDescription = stringResource(id = R.string.drop_image_image),
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
fun RowScope.DropTextBox(text: String?, isDroppingText: Boolean) {
    val boxColor = if (isDroppingText) mediumGray else lightGray
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(boxColor)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (text != null) {
            Text(
                text = text,
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
