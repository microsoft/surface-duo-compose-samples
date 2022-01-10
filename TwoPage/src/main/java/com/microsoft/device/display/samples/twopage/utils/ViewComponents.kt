/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AlignedCaption(text: String, textAlign: TextAlign) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.caption,
        textAlign = textAlign
    )
}

@Composable
fun PageLayout(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    start = 30.dp,
                    end = 25.dp
                )
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            content()
        }
    }
}
