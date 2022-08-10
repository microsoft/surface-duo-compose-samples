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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.dualscreen.testing.compose.foldableRuleChain
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.filters.MockFoldingFeature
import com.microsoft.device.dualscreen.testing.rules.FoldableTestRule
import com.microsoft.device.dualscreen.testing.runner.FoldableJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(FoldableJUnit4ClassRunner::class)
class PaneSynchronizationTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val foldableTestRule = FoldableTestRule()

    @get:Rule
    val testRule: TestRule = foldableRuleChain(composeTestRule, foldableTestRule)

    /**
     * Test that clicking an item in the list pane updates the image shown in the detail pane
     */
    @ExperimentalTestApi
    @Test
    @MockFoldingFeature(orientation = MockFoldingFeature.FoldingFeatureOrientation.VERTICAL)
    fun app_verticalFold_testListItemClickUpdatesDetailPane() {
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
     * Test that one pane is continuously shown on a non-large screen device, even when a horizontal fold is
     * introduced
     */
    @Test
    @MockFoldingFeature(orientation = MockFoldingFeature.FoldingFeatureOrientation.HORIZONTAL)
    fun app_testOnePaneShowsWithHorizontalFold() {
        // Check that the list view is displayed
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.gallery_list))
            .assertIsDisplayed()

        // Click to switch to the detail view
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_detail))
            .performClick()

        // Check that list view is no longer displayed (only one pane shown in HorizontalSingle mode)
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.gallery_list))
            .assertDoesNotExist()

        // REVISIT: return to list view so state isn't saved for other tests
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_list))
            .performClick()
    }
}
