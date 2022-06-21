/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dyadd.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.dyadd.ui.pages.components.Answer
import com.microsoft.device.display.samples.dyadd.ui.pages.components.BasicCalculationGrid
import com.microsoft.device.display.samples.dyadd.ui.pages.components.History
import com.microsoft.device.display.samples.dyadd.ui.pages.components.NumericGrid
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun BasicCalculatorPage(windowState: WindowState) {
    if (windowState.isSinglePortrait() || windowState.isDualPortrait()) {
        BasicCalculatorWithHistory()
    } else {
        BasicCalculator()
    }
}

@Composable
fun BasicCalculatorWithHistory() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            History(
                addRecordToTop = false,
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .align(Alignment.Start)
            )

            Answer(modifier = Modifier.align(Alignment.End))

            Row {
                NumericGrid(modifier = Modifier.fillMaxWidth(0.6f))
                BasicCalculationGrid()
            }
        }
    }
}

@Composable
fun BasicCalculator() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Answer(modifier = Modifier.align(Alignment.End))
            Row {
                NumericGrid(modifier = Modifier.fillMaxWidth(0.6f))
                BasicCalculationGrid()
            }
        }
    }
}
