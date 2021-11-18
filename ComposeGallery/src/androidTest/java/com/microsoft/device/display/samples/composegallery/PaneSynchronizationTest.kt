/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class PaneSynchronizationTest {
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Test that clicking an item in the list pane updates the image shown in the detail pane
     */
    @ExperimentalTestApi
    @Test
    fun app_testListItemClickUpdatesDetailPane() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(isAppSpanned = true, widthSizeClass = WindowSizeClass.Medium)
            }
        }

        // Span app so two panes are visible
        publisherRule.simulateVerticalFold(composeTestRule)

        // Scroll to end of list
        val index = 7
        composeTestRule.onNode(
            hasScrollAction() and hasTestTag(composeTestRule.getString(R.string.gallery_list))
        ).performScrollToIndex(index)

        // Click on last list item
        val lastItem = DataProvider.imageModels.last()
        composeTestRule.onNode(
            hasAnyAncestor(hasTestTag(composeTestRule.getString(R.string.gallery_list)))
                and hasContentDescription(lastItem.contentDescription)
        ).performClick()

        // Check that detail pane has updated with the correct information
        composeTestRule.onNode(
            hasText(lastItem.id)
                and hasAnySibling(hasContentDescription(lastItem.contentDescription))
        ).assertIsDisplayed()
        composeTestRule.onNode(
            hasContentDescription(lastItem.contentDescription)
                and hasAnySibling(hasText(lastItem.id))
        ).assertIsDisplayed()
    }

    /**
     * Test that a selection made when unspanned is remembered when span state changes
     */
    @ExperimentalCoroutinesApi
    @Test
    fun app_testSelectionPersistenceAfterSpan() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(isAppSpanned = false, widthSizeClass = WindowSizeClass.Compact)
            }
        }

        // Click on third surface duo entry
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.duo3_content_des))
            .performClick()

        // Check that detail view of third surface duo is displayed
        composeTestRule.onNodeWithText(composeTestRule.getString(R.string.duo3_id))
            .assertIsDisplayed()

        // REVISIT: return to list view so state isn't saved for other tests
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_list))
            .performClick()

        // Span the app
        publisherRule.simulateVerticalFold(composeTestRule)

        // Check that third surface duo image is still displayed
        composeTestRule.onNode(
            hasContentDescription(composeTestRule.getString(R.string.duo3_content_des))
                and hasAnySibling(hasText(composeTestRule.getString(R.string.duo3_id)))
        ).assertIsDisplayed()
    }
}
