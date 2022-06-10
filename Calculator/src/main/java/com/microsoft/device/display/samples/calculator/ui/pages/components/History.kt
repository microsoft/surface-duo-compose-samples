/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.calculator.ui.pages.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.calculator.models.Record
import com.microsoft.device.display.samples.calculator.models.equationStringEquivalent
import com.microsoft.device.display.samples.calculator.ui.pages.calculatorModel
import com.microsoft.device.display.samples.calculator.ui.pages.historyModel
import kotlinx.coroutines.launch

@Composable
fun History(addRecordToTop: Boolean, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 5.dp,
                top = 15.dp
            ),
        state = listState,
    ) {
        items(historyModel.records.size) { index ->
            Column {
                if (addRecordToTop) {
                    HistoryRecord(record = historyModel.records[historyModel.records.size - 1 - index])
                } else {
                    HistoryRecord(record = historyModel.records[index])
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        // following the bottom-most record if we are adding them to the bottom
        if (!addRecordToTop) {
            coroutineScope.launch {
                listState.animateScrollToItem(historyModel.records.size, 0)
            }
        } else {
            coroutineScope.launch {
                listState.animateScrollToItem(0, 0)
            }
        }
    }
}

@Composable
fun HistoryRecord(record: Record) {
    Column {
        Text(
            text = record.x + " " + equationStringEquivalent(record.equation) + " " + record.y + " =",
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .clickable { calculatorModel.setValuesFromRecord(record) }
                .alpha(0.6f)
        )
        Text(
            text = record.answer,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .clickable { calculatorModel.setValueFromRecordAnswer(record) }
                .alpha(0.9f)
        )
    }
}
