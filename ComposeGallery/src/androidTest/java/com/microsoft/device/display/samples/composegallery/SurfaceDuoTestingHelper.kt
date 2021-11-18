/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.uiautomator.UiDevice
import androidx.window.layout.FoldingFeature
import androidx.window.testing.layout.FoldingFeature
import androidx.window.testing.layout.TestWindowLayoutInfo
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.getString(@StringRes id: Int): String {
    return activity.getString(id)
}

/**
 * UIDEVICE HELPER METHODS - based on hardcoded coordinates and espresso swipe gestures
 */

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
    SurfaceDuo2(paneWidth = 1344, paneHeight = 1892, hingeThickness = 66, bottomY = 1870),
    Other(paneWidth = 0, paneHeight = 0, hingeThickness = 0, bottomY = 0);

    override fun toString(): String {
        return "leftX: $leftX rightX: $rightX middleX: $middleX middleY: $middleY bottomY: $bottomY"
    }
}

/**
 * Method to determine the model of a device
 */
fun UiDevice.getDeviceModel(): DeviceModel {
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
    Log.d(
        "SurfaceDuoTestingHelper",
        "Unknown dualscreen device dimensions $displayWidth $displayHeight"
    )
    return DeviceModel.Other
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
    switchToStart()
    closeStart()
}

/**
 * WINDOW MANAGER HELPER METHODS - based on TestWindowLayoutInfo and mocking FoldingFeatures
 */

fun <A : ComponentActivity> WindowLayoutInfoPublisherRule.simulateVerticalFold(
    composeAndroidTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
    center: Int = -1,
    size: Int = 0,
    state: FoldingFeature.State = FoldingFeature.State.HALF_OPENED
) {
    simulateFold(composeAndroidTestRule, center, size, state, FoldingFeature.Orientation.VERTICAL)
}

fun <A : ComponentActivity> WindowLayoutInfoPublisherRule.simulateHorizontalFold(
    composeAndroidTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
    center: Int = -1,
    size: Int = 0,
    state: FoldingFeature.State = FoldingFeature.State.HALF_OPENED
) {
    simulateFold(composeAndroidTestRule, center, size, state, FoldingFeature.Orientation.HORIZONTAL)
}

fun WindowLayoutInfoPublisherRule.simulateNoFold() {
    overrideWindowLayoutInfo(TestWindowLayoutInfo())
}

/**
 * Helper method to override the current window layout info with new information
 *
 * @param composeAndroidTestRule: compose test rule that's associated with an activity
 * @param center: center of the fold (defaults to middle of screen when -1)
 * @param size: size of the fold (default 0)
 * @param state: state of the fold (flat or half-opened)
 * @param orientation: orientation of the fold
 */
private fun <A : ComponentActivity> WindowLayoutInfoPublisherRule.simulateFold(
    composeAndroidTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
    center: Int,
    size: Int,
    state: FoldingFeature.State,
    orientation: FoldingFeature.Orientation,
) {
    composeAndroidTestRule.activityRule.scenario.onActivity { activity ->
        val fold = FoldingFeature(
            activity = activity,
            center = center,
            size = size,
            state = state,
            orientation = orientation
        )
        val windowLayoutInfo = TestWindowLayoutInfo(listOf(fold))
        overrideWindowLayoutInfo(windowLayoutInfo)
    }
}
