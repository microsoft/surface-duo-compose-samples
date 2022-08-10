/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import android.app.UiAutomation
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.microsoft.device.dualscreen.testing.compose.foldableRuleChain
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.filters.DualScreenTest
import com.microsoft.device.dualscreen.testing.filters.SingleScreenTest
import com.microsoft.device.dualscreen.testing.rules.FoldableTestRule
import com.microsoft.device.dualscreen.testing.runner.FoldableJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(FoldableJUnit4ClassRunner::class)
class LayoutTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val foldableTestRule = FoldableTestRule()

    @get:Rule
    val testRule: TestRule = foldableRuleChain(composeTestRule, foldableTestRule)

    /**
     * Tests that the single portrait layout appears when one pane is shown and the device is in
     * the portrait orientation
     */
    @Test
    @SingleScreenTest(orientation = UiAutomation.ROTATION_FREEZE_0)
    fun app_testSinglePortraitLayout() {
        // Check that single portrait layout is shown
        composeTestRule.onNodeWithTag(getString(R.string.single_port))
            .assertIsDisplayed()
    }

    /**
     * Tests that the single landscape layout appears when one pane is shown and the device is in
     * the landscape orientation
     */
    @Test
    @SingleScreenTest(orientation = UiAutomation.ROTATION_FREEZE_90)
    fun app_testSingleLandscapeLayout() {
        // Check that single landscape layout is shown
        composeTestRule.onNodeWithTag(getString(R.string.single_land))
            .assertIsDisplayed()
    }

    /**
     * Tests that the dual portrait layout appears when a vertical fold is simulated
     */
    @ExperimentalTestApi
    @Test
    @DualScreenTest(orientation = UiAutomation.ROTATION_FREEZE_0)
    fun app_testDualPortraitLayout() {
        // Check that dual portrait panes are shown
        composeTestRule.onNodeWithTag(getString(R.string.dual_port_pane1))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.dual_port_pane2))
            .assertIsDisplayed()
    }

    /**
     * Tests that the dual landscape layout appears when a horizontal fold is simulated
     */
    @ExperimentalTestApi
    @Test
    @DualScreenTest(orientation = UiAutomation.ROTATION_FREEZE_90)
    fun app_testDualLandscapeLayout() {
        // Check that dual landscape panes are shown
        composeTestRule.onNodeWithTag(getString(R.string.dual_land_pane1))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.dual_land_pane2))
            .assertIsDisplayed()
    }
}
