/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dyadd.models

import androidx.compose.runtime.mutableStateListOf

class HistoryModel {
    var records = mutableStateListOf<Record>()
    fun addToRecords(record: Record) {
        records.add(record)
    }

    fun clearRecords() {
        records.clear()
    }
}

data class Record(val x: String, val y: String, val equation: Equation, val answer: String)
