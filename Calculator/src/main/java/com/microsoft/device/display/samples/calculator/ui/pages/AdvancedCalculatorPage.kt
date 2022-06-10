/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.calculator.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.calculator.ui.pages.components.AdvancedEquationGrid
import com.microsoft.device.display.samples.calculator.ui.pages.components.History
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun AdvancedCalculatorPage(windowState: WindowState) {
    if (windowState.isDualLandscape()) {
        AdvancedCalculatorWithHistory()
    } else {
        AdvancedCalculator()
    }
}

@Composable
fun AdvancedCalculator() {
    Box(modifier = Modifier.fillMaxSize()) {
        AdvancedEquationGrid(columnCount = 4, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun AdvancedCalculatorWithHistory() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            AdvancedEquationGrid(
                columnCount = 3,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight()
            )
            History(addRecordToTop = true, modifier = Modifier.fillMaxWidth())
        }
    }
}
