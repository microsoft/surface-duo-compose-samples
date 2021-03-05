/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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