/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dyadd.models

import com.microsoft.device.display.samples.dyadd.ui.pages.calculatorModel
import kotlin.math.cos
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

fun add(param1: Float, param2: Float): Float {
    return param1 + param2
}

fun sub(param1: Float, param2: Float): Float {
    return param1 - param2
}

fun mul(param1: Float, param2: Float): Float {
    return param1 * param2
}

fun div(param1: Float, param2: Float): Float {
    return if (param2 == 0.0f) param2 else param1 / param2
}

fun power(param1: Float, param2: Float): Float {
    return param1.pow(param2)
}

fun modulus(param1: Float, param2: Float): Float {
    return if (param2 == 0.0f) param1 else param1 % param2
}

fun pi(): Float {
    return Math.PI.toFloat()
}

fun e(): Float {
    return Math.E.toFloat()
}

fun sin(param1: Float): Float {
    return executeTrigFunction(::sin, param1)
}

fun cos(param1: Float): Float {
    return executeTrigFunction(::cos, param1)
}

fun tan(param1: Float): Float {
    return executeTrigFunction(::tan, param1)
}

fun squareRoot(param1: Float): Float {
    return if (param1 >= 0.0f) {
        sqrt(param1)
    } else {
        param1
    }
}

fun ln(param1: Float): Float {
    return if (param1 >= 0.0f) {
        kotlin.math.ln(param1)
    } else {
        param1
    }
}

fun log(param1: Float): Float {
    return if (param1 >= 0.0f) {
        log10(param1)
    } else {
        param1
    }
}

fun squared(param1: Float): Float {
    return param1 * param1
}

fun inverse(param1: Float): Float {
    return if (param1 != 0.0f) 1 / param1 else 0.0f
}

fun percent(param1: Float): Float {
    return param1 / 100.0f
}

private fun executeTrigFunction(trigFunction: (Double) -> Double, rawInput: Float): Float {
    val radiansInput =
        if (calculatorModel.degrees) Math.toRadians(rawInput.toDouble()) else rawInput.toDouble()
    return trigFunction(radiansInput).toFloat()
}
