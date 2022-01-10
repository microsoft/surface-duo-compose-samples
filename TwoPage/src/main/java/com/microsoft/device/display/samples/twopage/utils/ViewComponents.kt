/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

const val PAGE_END_PADDING = 25

@Composable
fun AlignedCaption(modifier: Modifier = Modifier, text: String, textAlign: TextAlign) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.caption,
        textAlign = textAlign
    )
}

@Composable
fun PageLayout(modifier: Modifier, pageNumber: String, content: @Composable () -> Unit) {
    Scaffold(
        modifier = modifier,
        bottomBar = { PageNumber(pageNumber) },
    ) { innerPadding ->
        val layoutDir = LocalLayoutDirection.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(layoutDir) + 30.dp,
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(layoutDir) + PAGE_END_PADDING.dp,
                    bottom = innerPadding.calculateBottomPadding(),
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

@Composable
fun PageNumber(pageNumber: String) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    ) {
        AlignedCaption(
            modifier = Modifier.padding(end = PAGE_END_PADDING.dp),
            text = pageNumber,
            textAlign = TextAlign.End
        )
    }
}
