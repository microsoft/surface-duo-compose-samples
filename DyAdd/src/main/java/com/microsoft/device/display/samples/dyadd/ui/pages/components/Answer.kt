/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dyadd.ui.pages.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.dyadd.models.equationStringEquivalent
import com.microsoft.device.display.samples.dyadd.ui.pages.calculatorModel

@Composable
fun Answer(modifier: Modifier = Modifier) {
    val eq: String = if (!calculatorModel.isOnX) " " + equationStringEquivalent(calculatorModel.currentEquation) + " " else ""
    val y: String = if (!calculatorModel.isOnX) calculatorModel.y else ""
    LazyColumn(
        modifier = modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
            )
    ) {
        item {
            Text(
                text = (calculatorModel.x + eq + y),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
