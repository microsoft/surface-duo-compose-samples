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
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.companionpane.ui.theme.CompanionPaneAppTheme
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.compose.simulateHorizontalFoldingFeature
import com.microsoft.device.dualscreen.testing.compose.simulateVerticalFoldingFeature
import com.microsoft.device.dualscreen.testing.filters.DeviceOrientation
import com.microsoft.device.dualscreen.windowstate.WindowMode
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class LayoutTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Tests that the single portrait layout appears when one pane is shown and the device is in
     * the portrait orientation
     */
    @Test
    @DeviceOrientation(orientation = UiAutomation.ROTATION_FREEZE_0)
    fun app_testSinglePortraitLayout() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                CompanionPaneAppContent(WindowMode.SINGLE_PORTRAIT)
            }
        }

        // Check that single portrait layout is shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.single_port))
            .assertIsDisplayed()
    }

    /**
     * Tests that the single landscape layout appears when one pane is shown and the device is in
     * the landscape orientation
     */
    @Test
    @DeviceOrientation(orientation = UiAutomation.ROTATION_FREEZE_90)
    fun app_testSingleLandscapeLayout() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                CompanionPaneAppContent(WindowMode.SINGLE_LANDSCAPE)
            }
        }

        // Check that single landscape layout is shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.single_land))
            .assertIsDisplayed()
    }

    /**
     * Tests that the dual portrait layout appears when a vertical fold is simulated
     */
    @ExperimentalTestApi
    @Test
    fun app_testDualPortraitLayout() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                CompanionPaneAppContent(WindowMode.DUAL_PORTRAIT)
            }
        }

        // Simulate vertical foldingFeature
        publisherRule.simulateVerticalFoldingFeature(composeTestRule)

        // Check that dual portrait panes are shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_port_pane1))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_port_pane2))
            .assertIsDisplayed()
    }

    /**
     * Tests that the dual landscape layout appears when a horizontal fold is simulated
     */
    @ExperimentalTestApi
    @Test
    fun app_testDualLandscapeLayout() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                CompanionPaneAppContent(WindowMode.DUAL_LANDSCAPE)
            }
        }

        // Simulate horizontal foldingFeature
        publisherRule.simulateHorizontalFoldingFeature(composeTestRule)

        // Check that dual landscape panes are shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_land_pane1))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_land_pane2))
            .assertIsDisplayed()
    }

    /**
     * Test that app responds correctly when fold orientation switches from horizontal to vertical
     */
    @Test
    fun app_testDualLayoutRespondsToFoldOrientationChange() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                CompanionPaneApp(composeTestRule.activity.rememberWindowState())
            }
        }

        // Simulate horizontal foldingFeature
        publisherRule.simulateHorizontalFoldingFeature(composeTestRule)

        // Check that dual landscape panes are shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_land_pane1))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_land_pane2))
            .assertIsDisplayed()

        // Simulate vertical foldingFeature
        publisherRule.simulateVerticalFoldingFeature(composeTestRule)

        // Check that dual portrait panes are shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_port_pane1))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.dual_port_pane2))
            .assertIsDisplayed()
    }
}
