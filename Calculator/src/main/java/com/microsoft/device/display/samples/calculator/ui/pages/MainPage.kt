/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.calculator.ui.pages

import androidx.compose.runtime.Composable
import com.microsoft.device.display.samples.calculator.models.CalculatorModel
import com.microsoft.device.display.samples.calculator.models.HistoryModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

val calculatorModel: CalculatorModel = CalculatorModel()
val historyModel: HistoryModel = HistoryModel()

@Composable
fun MainPage(windowState: WindowState) {
    TwoPaneLayout(
        pane1 = { BasicCalculatorPage(windowState = windowState) },
        pane2 = { AdvancedCalculatorPage(windowState = windowState) }
    )
}
