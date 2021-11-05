/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.util.Log
import android.view.Surface
import androidx.test.uiautomator.UiDevice

/**
 * Coordinates taken from dual portrait point of view
 * Dimensions available here: https://docs.microsoft.com /dual-screen/android/surface-duo-dimensions
 */
enum class DeviceModel(
    val paneWidth: Int,
    val paneHeight: Int,
    val hingeThickness: Int,
    val leftX: Int = paneWidth / 2,
    val rightX: Int = leftX + paneWidth + hingeThickness,
    val middleX: Int = paneWidth + hingeThickness / 2,
    val middleY: Int = paneHeight / 2,
    val bottomY: Int,
    val spanSteps: Int = 400,
    val unspanSteps: Int = 200,
    val switchSteps: Int = 100,
    val closeSteps: Int = 50,
) {
    SurfaceDuo(paneWidth = 1350, paneHeight = 1800, hingeThickness = 84, bottomY = 1780),
    SurfaceDuo2(paneWidth = 1344, paneHeight = 1892, hingeThickness = 66, bottomY = 1870);

    override fun toString(): String {
        return "leftX: $leftX rightX: $rightX middleX: $middleX middleY: $middleY bottomY: $bottomY"
    }
}

/**
 * Helper method to determine the model of a device
 */
private fun UiDevice.getDeviceModel(): DeviceModel {
    Log.d(
        "SurfaceDuoTestingHelper",
        "w: $displayWidth h: $displayHeight rotation: $displayRotation"
    )

    return when (displayRotation) {
        Surface.ROTATION_0, Surface.ROTATION_180 -> getModelFromPaneWidth(displayWidth)
        Surface.ROTATION_90, Surface.ROTATION_270 -> getModelFromPaneWidth(displayHeight)
        else -> throw Error("Unknown rotation state $displayRotation")
    }
}

/**
 * Helper method to compare the pane width of a device to the pane widths of the defined device
 * models
 */
private fun UiDevice.getModelFromPaneWidth(paneWidth: Int): DeviceModel {
    for (model in DeviceModel.values()) {
        // pane width could be the width of a single pane, or the width of two panes + the width
        // of the hinge
        if (paneWidth == model.paneWidth || paneWidth == model.paneWidth * 2 + model.hingeThickness)
            return model
    }
    throw Error("Unknown dualscreen device dimensions $displayWidth $displayHeight")
}

/**
 * Helper method that sets up/cleans up a dualscreen swipe operation for automated testing
 * (freezes rotation, retrieves device model, performs swipe, unfreezes rotation)
 */
private fun UiDevice.dualscreenSwipeWrapper(swipe: (DeviceModel) -> Boolean) {
    freezeRotation()

    val model = getDeviceModel()
    swipe(model)

    unfreezeRotation()
}

fun UiDevice.spanFromStart() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.leftX, model.bottomY, model.middleX, model.middleY, model.spanSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.middleX, model.spanSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.middleX, model.spanSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.spanFromEnd() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.rightX, model.bottomY, model.middleX, model.middleY, model.spanSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.middleX, model.spanSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.middleX, model.spanSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.unspanToStart() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.rightX, model.bottomY, model.leftX, model.middleY, model.unspanSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.leftX, model.unspanSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.leftX, model.unspanSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.unspanToEnd() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.leftX, model.bottomY, model.rightX, model.middleY, model.unspanSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.rightX, model.unspanSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.rightX, model.unspanSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.switchToStart() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.rightX, model.bottomY, model.leftX, model.middleY, model.switchSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.leftX, model.switchSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.leftX, model.switchSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.switchToEnd() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.leftX, model.bottomY, model.rightX, model.middleY, model.switchSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.rightX, model.switchSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.rightX, model.switchSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.closeStart() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.leftX, model.bottomY, model.leftX, model.middleY, model.closeSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.leftX, model.closeSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.leftX, model.middleY, model.leftX, model.closeSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

fun UiDevice.closeEnd() {
    dualscreenSwipeWrapper { model ->
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                swipe(model.rightX, model.bottomY, model.rightX, model.middleY, model.closeSteps)
            Surface.ROTATION_270 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.rightX, model.closeSteps)
            Surface.ROTATION_90 ->
                swipe(model.bottomY, model.rightX, model.middleY, model.rightX, model.closeSteps)
            else -> throw Error("Unknown rotation state $displayRotation")
        }
    }
}

/**
 * Call this method to ensure that each method performs the desired visual effects
 * on your device/emulator
 */
fun UiDevice.testSpanningMethods() {
    spanFromStart()
    unspanToEnd()
    spanFromEnd()
    unspanToStart()
    switchToEnd()
//    closeEnd()
    switchToStart()
    closeStart()
}
