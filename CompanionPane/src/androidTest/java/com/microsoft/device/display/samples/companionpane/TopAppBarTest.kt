/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.companionpane.ui.theme.CompanionPaneAppTheme
import com.microsoft.device.dualscreen.testutils.getString
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class TopAppBarTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    @Test
    fun pane1_testTitleAppearsInSinglePortrait() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane1(ScreenState.SinglePortrait)
            }
        }

        // Check that app title appears in top bar
        composeTestRule.onNode(
            hasParent(
                hasTestTag(composeTestRule.getString(R.string.top_bar))
            )
        ).assertTextEquals(composeTestRule.getString(R.string.app_name))
    }

    @Test
    fun pane1_testTitleAppearsInSingleLandscape() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane1(ScreenState.SingleLandscape)
            }
        }

        // Check that app title appears in top bar
        composeTestRule.onNode(
            hasParent(
                hasTestTag(composeTestRule.getString(R.string.top_bar))
            )
        ).assertTextEquals(composeTestRule.getString(R.string.app_name))
    }

    @Test
    fun pane1_testTitleAppearsInDualPortrait() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane1(ScreenState.DualPortrait)
            }
        }

        // Check that app title appears in top bar
        composeTestRule.onNode(
            hasParent(
                hasTestTag(composeTestRule.getString(R.string.top_bar))
            )
        ).assertTextEquals(composeTestRule.getString(R.string.app_name))
    }

    @Test
    fun pane1_testTitleAppearsInDualLandscape() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane1(ScreenState.DualLandscape)
            }
        }

        // Check that app title appears in top bar
        composeTestRule.onNode(
            hasParent(
                hasTestTag(composeTestRule.getString(R.string.top_bar))
            )
        ).assertTextEquals(composeTestRule.getString(R.string.app_name))
    }

    @Test
    fun pane2_testTopBarDoesNotExistInSinglePortrait() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane2(ScreenState.SinglePortrait)
            }
        }

        // Check that top bar doesn't exist
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.top_bar))
            .assertDoesNotExist()
    }

    @Test
    fun pane2_testTopBarDoesNotExistInSingleLandscape() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane2(ScreenState.SingleLandscape)
            }
        }

        // Check that top bar doesn't exist
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.top_bar))
            .assertDoesNotExist()
    }

    @ExperimentalTestApi
    @Test
    fun pane2_testTitleIsBlankInDualPortrait() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane2(ScreenState.DualPortrait)
            }
        }

        // Check that app title appears blank in top bar
        composeTestRule.onNode(
            hasParent(
                hasTestTag(composeTestRule.getString(R.string.top_bar))
            )
        ).assertTextEquals("")
    }

    @Test
    fun pane2_testTopBarDoesNotExistInDualLandscape() {
        composeTestRule.setContent {
            CompanionPaneAppTheme {
                Pane2(ScreenState.DualLandscape)
            }
        }

        // Check that top bar doesn't exist
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.top_bar))
            .assertDoesNotExist()
    }
}
