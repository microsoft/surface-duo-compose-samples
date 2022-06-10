/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.calculator.models

import com.microsoft.device.display.samples.calculator.ui.pages.calculatorModel
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.sin
import kotlin.math.tan
import kotlin.math.cos

fun add() {
    calculatorModel.x = (calculatorModel.x.toFloat() + calculatorModel.y.toFloat()).toString()
}

fun sub() {
    calculatorModel.x = (calculatorModel.x.toFloat() - calculatorModel.y.toFloat()).toString()
}

fun mul() {
    calculatorModel.x = (calculatorModel.x.toFloat() * calculatorModel.y.toFloat()).toString()
}

fun div() {
    calculatorModel.x =
        if (calculatorModel.y == "0") "0.0" else (calculatorModel.x.toFloat() / calculatorModel.y.toFloat()).toString()
}

fun pi() {
    calculatorModel.overWrite = true
    calculatorModel.addValues(Math.PI.toFloat().toString())
}

fun sin() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = executeTrigFunction(::sin, calculatorModel.x)
    } else {
        calculatorModel.y = executeTrigFunction(::sin, calculatorModel.y)
    }
}

fun cos() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = executeTrigFunction(::cos, calculatorModel.x)
    } else {
        calculatorModel.y = executeTrigFunction(::cos, calculatorModel.y)
    }
}

fun tan() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = executeTrigFunction(::tan, calculatorModel.x)
    } else {
        calculatorModel.y = executeTrigFunction(::tan, calculatorModel.y)
    }
}

private fun executeTrigFunction(trigFunction: (Double) -> Double, rawInput: String): String {
    val radiansInput =
        if (calculatorModel.degrees) Math.toRadians(rawInput.toDouble()) else rawInput.toDouble()
    return trigFunction(radiansInput).toFloat().toString()
}

fun squareRoot() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = if (calculatorModel.x.toFloat() >= 0.0f) {
            sqrt(calculatorModel.x.toDouble()).toFloat().toString()
        } else {
            calculatorModel.x
        }
    } else {
        calculatorModel.y = if (calculatorModel.y.toFloat() >= 0.0f) {
            sqrt(calculatorModel.y.toDouble()).toFloat().toString()
        } else {
            calculatorModel.y
        }
    }
}

fun squared() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = calculatorModel.x.toDouble().pow(2.0).toFloat().toString()
    } else {
        calculatorModel.y = calculatorModel.y.toDouble().pow(2.0).toFloat().toString()
    }
}

fun e() {
    calculatorModel.overWrite = true
    calculatorModel.addValues(Math.E.toFloat().toString())
}

fun ln() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = if (calculatorModel.x.toFloat() >= 0.0f) {
            kotlin.math.ln(
                calculatorModel.x.toDouble()
            ).toFloat().toString()
        } else {
            calculatorModel.x
        }
    } else {
        calculatorModel.y = if (calculatorModel.y.toFloat() >= 0.0f) {
            kotlin.math.ln(
                calculatorModel.y.toDouble()
            ).toFloat().toString()
        } else {
            calculatorModel.y
        }
    }
}

fun log() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = if (calculatorModel.x.toFloat() >= 0.0f) {
            log10(calculatorModel.x.toDouble()).toFloat().toString()
        } else {
            calculatorModel.x
        }
    } else {
        calculatorModel.y = if (calculatorModel.y.toFloat() >= 0.0f) {
            log10(calculatorModel.y.toDouble()).toFloat().toString()
        } else {
            calculatorModel.y
        }
    }
}

fun inverse() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = if (calculatorModel.x.toFloat() != 0.0f) {
            (1 / calculatorModel.x.toFloat()).toString()
        } else {
            calculatorModel.x
        }
    } else {
        calculatorModel.y = if (calculatorModel.y.toFloat() != 0.0f) {
            (1 / calculatorModel.y.toFloat()).toString()
        } else {
            calculatorModel.y
        }
    }
}

fun power() {
    calculatorModel.x =
        calculatorModel.x.toDouble().pow(calculatorModel.y.toDouble()).toFloat().toString()
}

fun percent() {
    if (calculatorModel.isOnX) {
        calculatorModel.x = (calculatorModel.x.toFloat() / 100).toString()
    } else {
        calculatorModel.y = (calculatorModel.y.toFloat() / 100).toString()
    }
}

fun modulus() {
    calculatorModel.x = (calculatorModel.x.toFloat() % calculatorModel.y.toFloat()).toString()
}
