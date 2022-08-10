/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.compose.simulateHorizontalFoldingFeature
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

const val nonSelectionOption = -1

class MapImageTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Tests that the correct map image is displayed after a restaurant is clicked
     */
    @ExperimentalTestApi
    @Test
    fun app_horizontalFold_mapUpdatesAfterRestaurantClick() {
        // Simulate horizontal foldFeature
        publisherRule.simulateHorizontalFoldingFeature(composeTestRule)

        // Scrolls to and clicks each item in the restaurant list
        restaurants.forEachIndexed { index, rest ->
            // Scroll to next restaurant item
            composeTestRule.onNode(hasScrollToIndexAction()).performScrollToIndex(index)

            // Click on restaurant item
            composeTestRule.onNode(
                hasContentDescription(composeTestRule.getString(rest.title)) and hasParent(
                    hasAnySibling(hasText(composeTestRule.getString(R.string.list_title)))
                )
            ).performClick()

            if (index == nonSelectionOption) {
                // Assert the unselected image placeholder is shown
                composeTestRule.onNodeWithContentDescription(
                    composeTestRule.getString(R.string.map_description)
                ).assertExists()
            } else {
                // Assert the shown selected image matches the item clicked from the list
                composeTestRule.onNodeWithContentDescription(
                    composeTestRule.getString(rest.description)
                ).assertExists()
            }
        }
    }
}
