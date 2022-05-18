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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.ui.theme.darkGray
import com.microsoft.device.display.samples.draganddrop.ui.theme.lightGray

@Composable
fun DragPane() {
    Scaffold(
        topBar = {
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
    ) {
        DragPaneContent()
    }
}

@Composable
fun DragPaneContent() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.drag_and_drop_image),
                contentDescription = stringResource(R.string.image_contentDescription),
                modifier = Modifier
                    .fillMaxHeight()
            )
        }

        Spacer(modifier = Modifier.width(30.dp))

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
           Text(
               text = stringResource(R.string.drag_and_drop_plain_text),
               style = TextStyle(
                   fontSize = 19.sp,
                   color = darkGray
               )
           )
        }
    }
}