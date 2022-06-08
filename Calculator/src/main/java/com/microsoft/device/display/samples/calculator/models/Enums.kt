package com.microsoft.device.display.samples.calculator.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.microsoft.device.display.samples.calculator.R


import com.microsoft.device.display.samples.calculator.ui.pages.calculatorModel

// Equations that take multiple parameters
enum class Equation {
    ADD, SUB, MUL, DIV, POW, MOD, NONE,
}

// Actions and equations that only need a single parameter
enum class Action {
    DEL, CLR, EQL, DEG, INV, SIN, COS, TAN, LOG, LN, E, PI, SQRT, SQRD, SIGN, PRCT
}

@Composable
fun equationStringEquivalent(eq: Equation): String {
    return when (eq) {
        Equation.ADD -> stringResource(id = R.string.add)
        Equation.SUB -> stringResource(id = R.string.subtract)
        Equation.MUL -> stringResource(id = R.string.multiply)
        Equation.DIV -> stringResource(id = R.string.divide)
        Equation.POW -> stringResource(id = R.string.power)
        Equation.MOD -> stringResource(id = R.string.modulus)
        else -> {
            ""
        }
    }
}

@Composable
fun actionStringEquivalent(ac: Action): String {
    return when (ac) {
        Action.DEL -> stringResource(id = R.string.delete)
        Action.EQL -> stringResource(id = R.string.equals)
        Action.CLR -> stringResource(id = R.string.allclear)
        Action.INV -> stringResource(id = R.string.inverse)
        Action.SIN -> stringResource(id = R.string.sin)
        Action.COS -> stringResource(id = R.string.cosine)
        Action.TAN -> stringResource(id = R.string.tangent)
        Action.LOG -> stringResource(id = R.string.log)
        Action.LN -> stringResource(id = R.string.ln)
        Action.E -> stringResource(id = R.string.e)
        Action.PI -> stringResource(id = R.string.pi)
        Action.SQRT -> stringResource(id = R.string.squareroot)
        Action.SQRD -> stringResource(id = R.string.squared)
        Action.PRCT -> stringResource(id = R.string.percent)
        Action.SIGN -> stringResource(id = R.string.sign)
        Action.DEG -> if (calculatorModel.degrees) stringResource(id = R.string.degree) else stringResource(id = R.string.radian)
    }
}
