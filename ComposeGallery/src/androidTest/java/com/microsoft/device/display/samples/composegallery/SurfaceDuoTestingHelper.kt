/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.app.Instrumentation
import android.util.Log
import androidx.test.uiautomator.UiDevice

enum class DeviceModel(
    val leftX: Int = 675,
    val rightX: Int = 2109,
    val middleX: Int = 1350,
    val middleY: Int = 900,
    val bottomY: Int = 1780,
    val spanSteps: Int = 400,
    val unspanSteps: Int = 200,
    val switchSteps: Int = 100,
    val closeSteps: Int = 50,
) {
    SurfaceDuo,
    SurfaceDuo2(bottomY = 1860),
}

fun UiDevice.spanFromLeft(model: DeviceModel) {
    swipe(model.leftX, model.bottomY, model.middleX, model.middleY, model.spanSteps)
}

fun UiDevice.spanFromRight(model: DeviceModel) {
    swipe(model.rightX, model.bottomY, model.middleX, model.middleY, model.spanSteps)
}

fun UiDevice.unspanToLeft(model: DeviceModel) {
    swipe(model.rightX, model.bottomY, model.leftX, model.middleY, model.unspanSteps)
}

fun UiDevice.unspanToRight(model: DeviceModel) {
    swipe(model.leftX, model.bottomY, model.rightX, model.middleY, model.unspanSteps)
}

fun UiDevice.switchToLeft(model: DeviceModel) {
    swipe(model.rightX, model.bottomY, model.leftX, model.middleY, model.switchSteps)
}

fun UiDevice.switchToRight(model: DeviceModel) {
    swipe(model.leftX, model.bottomY, model.rightX, model.middleY, model.switchSteps)
}

fun UiDevice.closeLeft(model: DeviceModel) {
    swipe(model.leftX, model.bottomY, model.leftX, model.middleY, model.closeSteps)
}

fun UiDevice.closeRight(model: DeviceModel) {
    swipe(model.rightX, model.bottomY, model.rightX, model.middleY, model.closeSteps)
}

class FoldableUiDevice(instrumentation: Instrumentation) {
    private val device = UiDevice.getInstance(instrumentation)
    private var model: DeviceModel = DeviceModel.SurfaceDuo

    fun determineModel() {
        model = when (device.displayWidth) {
            1344, 2688 -> DeviceModel.SurfaceDuo2
            1350, 2700 -> DeviceModel.SurfaceDuo
            else -> throw UnsupportedOperationException("Unknown foldable device dimensions")
        }
        Log.i("SurfaceDuoTestingHelper", "w: ${device.displayWidth} h: ${device.displayHeight} rotation: ${device.displayRotation}")
    }

    fun spanFromLeft() {
        device.spanFromLeft(model)
    }

    fun spanFromRight() {
        device.spanFromRight(model)
    }

    fun unspanToLeft() {
        device.unspanToLeft(model)
    }

    fun unspanToRight() {
        device.unspanToRight(model)
    }

    fun switchToLeft() {
        device.switchToLeft(model)
    }

    fun switchToRight() {
        device.switchToRight(model)
    }

    fun closeLeft() {
        device.closeLeft(model)
    }

    fun closeRight() {
        device.closeRight(model)
    }

    /**
     * Call this method to ensure that each method performs the desired visual effects
     * on your device/emulator
     */
    fun testSpanningMethods() {
        spanFromLeft()
        unspanToRight()
        spanFromRight()
        unspanToLeft()
        switchToRight()
        switchToLeft()
        closeLeft()
    }
}
