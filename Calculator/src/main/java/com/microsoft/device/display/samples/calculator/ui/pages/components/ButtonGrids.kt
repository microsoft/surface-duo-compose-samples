/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.calculator.ui.pages.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.calculator.models.Action
import com.microsoft.device.display.samples.calculator.models.Equation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvancedEquationGrid(columnCount: Int, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(columnCount),
        // content padding
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            item { ActionButton(ac = Action.DEG, color = MaterialTheme.colors.primaryVariant) }
            item { EquationButton(eq = Equation.POW, color = MaterialTheme.colors.primary) }
            item { ActionButton(ac = Action.SIGN, color = MaterialTheme.colors.primaryVariant) }
            item { ActionButton(ac = Action.PI, color = MaterialTheme.colors.primary) }
            item { EquationButton(eq = Equation.MOD, color = MaterialTheme.colors.primaryVariant) }
            item { ActionButton(ac = Action.E, color = MaterialTheme.colors.primary) }
            item { ActionButton(ac = Action.SIN, color = MaterialTheme.colors.primaryVariant) }
            item { ActionButton(ac = Action.COS, color = MaterialTheme.colors.primary) }
            item { ActionButton(ac = Action.TAN, color = MaterialTheme.colors.primaryVariant) }
            item { ActionButton(ac = Action.SQRD, color = MaterialTheme.colors.primary) }
            item { ActionButton(ac = Action.SQRT, color = MaterialTheme.colors.primaryVariant) }
            item { ActionButton(ac = Action.INV, color = MaterialTheme.colors.primary) }
            item { ActionButton(ac = Action.LN, color = MaterialTheme.colors.primaryVariant) }
            item { ActionButton(ac = Action.LOG, color = MaterialTheme.colors.primary) }
            item { ActionButton(ac = Action.PRCT, color = MaterialTheme.colors.primaryVariant) }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumericGrid(modifier: Modifier = Modifier) {
    val list = (0..10).map { it.toString() }
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = modifier,
        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 6.dp,
            bottom = 16.dp
        ),
        content = {
            items(list.size) { index ->
                if (index == list.size - 1) {
                    NumericButton(num = ".")
                } else {
                    NumericButton(num = list[list.size - 2 - index])
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicCalculationGrid() {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        // content padding
        contentPadding = PaddingValues(
            start = 6.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            item { EquationButton(eq = Equation.DIV, color = MaterialTheme.colors.secondary) }
            item { ActionButton(ac = Action.CLR, color = MaterialTheme.colors.secondaryVariant) }
            item { EquationButton(eq = Equation.MUL, color = MaterialTheme.colors.secondary) }
            item { ActionButton(ac = Action.DEL, color = MaterialTheme.colors.secondaryVariant) }
            item { EquationButton(eq = Equation.ADD, color = MaterialTheme.colors.secondary) }
            item { ActionButton(ac = Action.EQL, color = MaterialTheme.colors.secondaryVariant) }
            item { EquationButton(eq = Equation.SUB, color = MaterialTheme.colors.secondary) }
        }
    )
}
