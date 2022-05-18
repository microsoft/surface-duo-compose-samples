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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.ui.theme.darkGray
import com.microsoft.device.display.samples.draganddrop.ui.theme.lightGray
import com.microsoft.device.display.samples.draganddrop.utils.DropContainer

@Composable
fun DropPane() {
    var textData = remember {
        mutableStateOf<String>("")
    }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colors.primary) {}
        }
    ) {
        DropContainer<String>(
            modifier = Modifier.fillMaxSize()
        ) { isDropping, data ->
            data?.let {
                textData.value = data
            }

            DropPaneContent(textData.value)
        }
    }
}

@Composable
fun DropPaneContent(text: String) {
    Row {
        DropImageBox()
        Spacer(modifier = Modifier.width(10.dp))
        DropTextBox(text)
    }
}

@Composable
fun RowScope.DropImageBox() {
    Box(modifier = Modifier
        .weight(1f)
        .fillMaxSize()
        .background(lightGray)
        .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
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
}

@Composable
fun RowScope.DropTextBox(text: String) {
    Box(modifier = Modifier
        .weight(1f)
        .fillMaxSize()
        .background(lightGray)
        .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (text.isNotEmpty()) {
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
