/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.twopage.ui.home.FirstPage
import com.microsoft.device.display.samples.twopage.ui.home.FirstPageContent
import com.microsoft.device.display.samples.twopage.ui.home.HorizontalPadding

@Composable
fun AlignedCaption(text: String, arrangement: Arrangement.Horizontal) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement){
        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun PageLayout(modifier: Modifier, pageContent: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .padding(
                start = HorizontalPadding,
                end = HorizontalPadding
            )
            .then(modifier),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        pageContent()
    }
}