/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewAppTheme
import com.microsoft.device.display.samples.dualview.ui.view.DualViewApp
import com.microsoft.device.dualscreen.testutils.assertScreenshotMatchesReference
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.testutils.saveScreenshotToDevice
import com.microsoft.device.dualscreen.testutils.simulateHorizontalFold
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

const val VIEW_SIZE = 400

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
        composeTestRule.setContent {
            DualViewAppTheme {
                DualViewApp(WindowState(hasFold = true, isFoldHorizontal = true), viewSize = VIEW_SIZE)
            }
        }

        // Simulate horizontal fold
        publisherRule.simulateHorizontalFold(composeTestRule)

        clickRestaurantsAndPerformAction()
    }

    /**
     * Scrolls to and clicks each item in the restaurant list, and also performs the specified action
     * on each restaurant item node/reference image pair. The default action asserts that a screenshot
     * of the node matches the associated reference image.
     *
     * @param action: action to perform with Semantics Node and reference image pair
     */
    @ExperimentalTestApi
    private fun clickRestaurantsAndPerformAction(action: (String, SemanticsNodeInteraction) -> Unit = ::assertScreenshotMatchesReference) {
        // Create list of reference images file names (from src/androidTest/assets folder)
        val referenceAssets =
            mutableListOf("unselected", "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth")
        referenceAssets.forEachIndexed { index, prefix ->
            referenceAssets[index] = prefix.plus("_map.png")
        }

        restaurants.forEachIndexed { index, rest ->
            // Perform specified action
            action(
                referenceAssets[index],
                composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image))
            )

            // Scroll to next restaurant item
            composeTestRule.onNode(hasScrollToIndexAction()).performScrollToIndex(index)

            // Click on restaurant item
            composeTestRule.onNode(
                hasContentDescription(composeTestRule.getString(rest.title)) and hasParent(
                    hasAnySibling(hasText(composeTestRule.getString(R.string.list_title)))
                )
            ).performClick()
        }
    }
}
