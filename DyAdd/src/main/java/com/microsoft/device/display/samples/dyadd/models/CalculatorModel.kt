/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dyadd.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.microsoft.device.display.samples.dyadd.ui.pages.calculatorModel
import com.microsoft.device.display.samples.dyadd.ui.pages.historyModel
import kotlin.math.roundToInt

class CalculatorModel() {
    var x: String by mutableStateOf("0")
    var y: String by mutableStateOf("0")

    // if we are editing x or y
    var isOnX: Boolean by (mutableStateOf(true))

    // if we are overwriting the current variable by typing, or adding to it by typing
    var overWrite: Boolean by (mutableStateOf(true))
    var degrees: Boolean by (mutableStateOf(true))
    var currentEquation: Equation by mutableStateOf(Equation.NONE)

    private fun assertEquation() {
        val prevX = this.x
        val prevY = this.y
        when (currentEquation) {
            Equation.ADD -> varToEquation(::add)
            Equation.SUB -> varToEquation(::sub)
            Equation.MUL -> varToEquation(::mul)
            Equation.DIV -> varToEquation(::div)
            Equation.POW -> varToEquation(::power)
            Equation.MOD -> varToEquation(::modulus)
            else -> {}
        }
        decimalReformat()
        if (currentEquation != Equation.NONE) historyModel.addToRecords(
            Record(
                prevX,
                prevY,
                currentEquation,
                this.x
            )
        )
        resetToX()
        overWrite = true
    }

    // we pass this in instead of storing it as we only want to execute an action once per click
    fun assertAction(ac: Action) {
        when (ac) {
            Action.DEL -> subValues()
            Action.EQL -> assertEquation()
            Action.CLR -> clearValues()
            Action.DEG -> toggleDegree()
            Action.SIGN -> toggleNegative()
            Action.INV -> varToAction(::inverse)
            Action.SIN -> varToAction(::sin)
            Action.COS -> varToAction(::cos)
            Action.TAN -> varToAction(::tan)
            Action.LOG -> varToAction(::log)
            Action.LN -> varToAction(::ln)
            Action.SQRT -> varToAction(::squareRoot)
            Action.SQRD -> varToAction(::squared)
            Action.PRCT -> varToAction(::percent)
            Action.E -> {
                calculatorModel.overWrite = true
                addValues(e().toString())
            }
            Action.PI -> {
                calculatorModel.overWrite = true
                addValues(pi().toString())
            }
        }
        decimalReformat()
    }

    private fun varToEquation(eq: (Float, Float) -> Float) {
        x = eq(x.toFloat(), y.toFloat()).toString()
    }

    private fun varToAction(ac: (Float) -> Float) {
        if (isOnX) {
            x = ac(x.toFloat()).toString()
        } else {
            y = ac(y.toFloat()).toString()
        }
    }

    private fun toggleDegree() {
        degrees = !degrees
    }

    private fun subValues() {
        if (isOnX) subX() else subY()
    }

    private fun toggleNegative() {
        if (isOnX) {
            x = (x.toFloat() * -1.0f).toString()
        } else {
            y = (y.toFloat() * -1.0f).toString()
        }
    }

    // remove hanging 0's and convert float to int when possible
    private fun decimalReformat() {
        val floatX = this.x.toFloat()
        val floatY = this.y.toFloat()

        if (floatX == 0.0f) {
            this.x = "0"
        } else if ((floatX - floatX.roundToInt()) == 0.0f) {
            this.x = (floatX.roundToInt()).toString()
        }

        if (floatY == 0.0f) {
            this.y = "0"
        } else if ((floatY - floatY.roundToInt()) == 0.0f) {
            this.y = (floatY.roundToInt()).toString()
        }
    }

    private fun clearValues() {
        // if the user clears when x is 0, we will do an all clear
        if (x == "0" && isOnX) {
            historyModel.clearRecords()
        }
        resetValues()
    }

    private fun resetToX() {
        y = "0"
        isOnX = true
        currentEquation = Equation.NONE
    }

    private fun resetValues() {
        x = "0"
        resetToX()
    }

    private fun addX(x: String) {
        if (x == ".") {
            if (!this.x.contains(".")) {
                this.x += x
            }
        } else if (this.x == "0") {
            this.x = x
        } else {
            this.x += x
        }
    }

    private fun subX() {
        if (x.length > 1) {
            x = x.substring(0, x.length - 1)
        } else if (x.length == 1) {
            resetValues()
        }
    }

    private fun addY(y: String) {
        if (y == ".") {
            if (!this.y.contains(".")) {
                this.y += y
            }
        } else if (this.y == "0") {
            this.y = y
        } else {
            this.y += y
        }
    }

    private fun subY() {
        if (y.length > 1) {
            y = y.substring(0, y.length - 1)
        } else if (y.length == 1) {
            if (y == "0") {
                resetToX()
            } else {
                y = "0"
            }
        }
    }

    // for setting equation and x/y from a record
    fun setValuesFromRecord(record: Record) {
        isOnX = false
        overWrite = false
        x = record.x
        y = record.y
        currentEquation = record.equation
    }

    // update current variable with a records answer
    fun setValueFromRecordAnswer(record: Record) {
        if (isOnX) {
            x = record.answer
        } else {
            y = record.answer
        }
    }

    fun updateEquation(eq: Equation) {
        isOnX = if (isOnX) {
            false
        } else {
            assertEquation()
            // assertEquation calls resetToX to reset the equation and sets isOnX to true, we set it back to false here so its possible to continually
            // create equations without re-entering values thus setting the previous equations result to x and allowing to edit y immediately
            false
        }
        currentEquation = eq
    }

    fun addValues(value: String) {
        if (overWrite) {
            if (isOnX) x = value else y = value
            overWrite = false
        } else {
            if (isOnX) addX(value) else addY(value)
        }
    }
}
