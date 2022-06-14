/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.calculator

import com.microsoft.device.display.samples.calculator.models.add
import com.microsoft.device.display.samples.calculator.models.div
import com.microsoft.device.display.samples.calculator.models.inverse
import com.microsoft.device.display.samples.calculator.models.ln
import com.microsoft.device.display.samples.calculator.models.log
import com.microsoft.device.display.samples.calculator.models.modulus
import com.microsoft.device.display.samples.calculator.models.mul
import com.microsoft.device.display.samples.calculator.models.percent
import com.microsoft.device.display.samples.calculator.models.power
import com.microsoft.device.display.samples.calculator.models.squareRoot
import com.microsoft.device.display.samples.calculator.models.squared
import com.microsoft.device.display.samples.calculator.models.sub
import junit.framework.Assert.assertEquals
import org.junit.Test

class CalculatorTest {

    @Test
    fun testAdd() {
        val expected = 3.0f
        val result = add(1.0f, 2.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testSub() {
        val expected = 0.0f
        val result = sub(2.0f, 2.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testMul() {
        val expected = 2.0f
        val result = mul(1.0f, 2.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testDiv() {
        val expected = 2.5f
        val result = div(5.0f, 2.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testMod() {
        val expected = 1.0f
        val result = modulus(10.0f, 3.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testPower() {
        val expected = 25.0f
        val result = power(5.0f, 2.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testSqrt() {
        val expected = 5.0f
        val result = squareRoot(25.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testSqrd() {
        val expected = 25.0f
        val result = squared(5.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testPrct() {
        val expected = 2.50f
        val result = percent(250.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testLn() {
        var expected = 0.0f
        var result = ln(1.0f)
        assertEquals(expected, result)

        expected = -1.0f
        result = ln(-1.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testLog() {
        var expected = 0.0f
        var result = log(1.0f)
        assertEquals(expected, result)

        expected = -1.0f
        result = log(-1.0f)
        assertEquals(expected, result)
    }

    @Test
    fun testInverse() {
        var expected = 0.5f
        var result = inverse(2.0f)
        assertEquals(expected, result)

        expected = 0.0f
        result = inverse(0.0f)
        assertEquals(expected, result)
    }
}
