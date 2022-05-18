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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.ui.theme.lightGray

@Composable
fun DropPane() {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.primary) {}
        }
    ) {
        DropPaneContent()
    }
}

@Composable
fun DropPaneContent() {
    Row {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(lightGray)
            .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.drag_and_drop_ic_text_fields_black),
                contentDescription = stringResource(R.string.image_contentDescription)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(lightGray)
            .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.drag_and_drop_ic_photo_black),
                contentDescription = stringResource(R.string.image_contentDescription),
            )
        }
    }
}