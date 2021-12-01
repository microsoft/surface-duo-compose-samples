/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.testutils

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.window.layout.FoldingFeature
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule

/**
 * COMPOSE HELPER
 * -----------------------------------------------------------------------------------------------
 * These functions can be used in Compose UI tests to simplify testing code. Included are functions
 * for easily retrieving string resources and Compose versions of the functions from FoldHelper.kt.
 */

/**
 * Get resource string inside Compose test with resource id
 *
 * @param id: string resource id
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.getString(@StringRes id: Int): String {
    return activity.getString(id)
}

/**
 * Simulate a vertical fold in a Compose test
 *
 * @param composeTestRule: Compose android test rule
 * @param center: location of center of fold
 * @param size: size of fold
 * @param state: state of fold
 */
fun <A : ComponentActivity> WindowLayoutInfoPublisherRule.simulateVerticalFold(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
    center: Int = -1,
    size: Int = 0,
    state: FoldingFeature.State = FoldingFeature.State.HALF_OPENED
) {
    simulateVerticalFold(composeTestRule.activityRule, center, size, state)
}

/**
 * Simulate a horizontal fold in a Compose test
 *
 * @param composeTestRule: Compose android test rule
 * @param center: location of center of fold
 * @param size: size of fold
 * @param state: state of fold
 */
fun <A : ComponentActivity> WindowLayoutInfoPublisherRule.simulateHorizontalFold(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
    center: Int = -1,
    size: Int = 0,
    state: FoldingFeature.State = FoldingFeature.State.HALF_OPENED
) {
    simulateHorizontalFold(composeTestRule.activityRule, center, size, state)
}

/**
 * Simulate a fold with the given properties in a Compose test
 *
 * @param composeTestRule: Compose android test rule
 * @param center: location of center of fold
 * @param size: size of fold
 * @param state: state of fold
 * @param orientation: orientation of fold
 */
fun <A : ComponentActivity> WindowLayoutInfoPublisherRule.simulateFold(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
    center: Int,
    size: Int,
    state: FoldingFeature.State,
    orientation: FoldingFeature.Orientation,
) {
    simulateFold(composeTestRule.activityRule, center, size, state, orientation)
}
