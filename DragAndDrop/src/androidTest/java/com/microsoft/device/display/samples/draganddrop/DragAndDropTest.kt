/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.draganddrop.ui.theme.DragAndDropSamplesTheme
import com.microsoft.device.display.samples.draganddrop.ui.view.DragAndDropApp
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.compose.simulateHorizontalFoldingFeature
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class DragAndDropTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Tests that both drag pane and drop pane are shown
     * on both the single-screen mode and the horizontal fold(dual-landscape) mode
     */
    @Test
    fun app_showsBothPanes_before_and_after_horizontalFold() {
        composeTestRule.setContent {
            DragAndDropSamplesTheme {
                DragAndDropApp()
            }
        }

        // Assert the drag pane is now shown
        composeTestRule.onNodeWithTag(
            composeTestRule.getString(R.string.drag_pane)
        ).assertExists()

        // Assert the drop pane is now shown
        composeTestRule.onNodeWithTag(
            composeTestRule.getString(R.string.drop_pane)
        ).assertExists()

        // Assert the reset button is now shown
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.getString(R.string.reset_button_icon)
        ).assertExists()

        // Simulate horizontal foldFeature
        publisherRule.simulateHorizontalFoldingFeature(composeTestRule)

        // Assert the drag pane is still shown
        composeTestRule.onNodeWithTag(
            composeTestRule.getString(R.string.drag_pane)
        ).assertExists()

        // Assert the drop pane is still shown
        composeTestRule.onNodeWithTag(
            composeTestRule.getString(R.string.drop_pane)
        ).assertExists()

        // Assert the reset button is still shown
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.getString(R.string.reset_button_icon)
        ).assertExists()
    }
}
