/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.text.TextStyle
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewAppTheme
import com.microsoft.device.display.samples.dualview.ui.theme.selectedBody1
import com.microsoft.device.display.samples.dualview.ui.theme.typography
import com.microsoft.device.display.samples.dualview.ui.view.RestaurantListView
import com.microsoft.device.display.samples.dualview.ui.view.TextStyleKey
import com.microsoft.device.display.samples.dualview.ui.view.narrowWidth
import com.microsoft.device.dualscreen.testing.compose.getString
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class RestaurantListTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Tests that clicking on each restaurant list item updates the item's text style
     */
    @ExperimentalTestApi
    @Test
    fun restaurantList_itemClickChangesTextStyle() {
        composeTestRule.setContent {
            DualViewAppTheme {
                RestaurantListViewWithState()
            }
        }

        checkRestaurantListTextStyle(true)
    }

    /**
     * Tests that no restaurant is selected  in the restaurant list on startup
     */
    @ExperimentalTestApi
    @Test
    fun restaurantList_noItemSelectedUponStart() {
        composeTestRule.setContent {
            DualViewAppTheme {
                RestaurantListViewWithState()
            }
        }

        checkRestaurantListTextStyle()
    }

    @Test
    fun restaurantList_narrowListIsHorizontallyScrollable() {
        composeTestRule.setContent {
            DualViewAppTheme {
                // Display restaurant list in a narrow view
                RestaurantListViewWithState(viewWidth = narrowWidth - 1)
            }
        }

        // Assert that the details for the first restaurant are horizontally scrollable
        composeTestRule.onNode(
            matcher = hasAnyChild(hasText(composeTestRule.getString(R.string.pestle_rock)))
                and SemanticsMatcher.keyIsDefined(SemanticsProperties.HorizontalScrollAxisRange),
            useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun restaurantList_normalListIsNotHorizontallyScrollable() {
        composeTestRule.setContent {
            DualViewAppTheme {
                // Display restaurant list in a normal view
                RestaurantListViewWithState(viewWidth = narrowWidth + 1)
            }
        }

        // Assert that the details for the first restaurant are not horizontally scrollable
        composeTestRule.onNode(
            matcher = hasAnyChild(hasText(composeTestRule.getString(R.string.pestle_rock)))
                and SemanticsMatcher.keyIsDefined(SemanticsProperties.HorizontalScrollAxisRange),
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    /**
     * Helper method to scroll to each restaurant item and make sure its text style is correct
     *
     * @param clickRestaurantItems: if true, each restaurant item will also be clicked, otherwise,
     * the list will just scroll to each restaurant item
     */
    @ExperimentalTestApi
    private fun checkRestaurantListTextStyle(clickRestaurantItems: Boolean = false) {
        for (index in restaurants.indices) {
            // Scroll to next restaurant
            composeTestRule.onNode(hasScrollToIndexAction()).performScrollToIndex(index)

            // Get semantics node for current restaurant
            val currentRestaurantTitle = composeTestRule.getString(restaurants[index].title)
            val currentRestaurant = composeTestRule.onNodeWithContentDescription(currentRestaurantTitle)

            // Check that the unselected text style is being used
            currentRestaurant.assertTextStyleEquals(typography.body1)

            if (clickRestaurantItems) {
                // Select current restaurant
                currentRestaurant.performClick()

                // Check that text style has been updated
                currentRestaurant.assertTextStyleEquals(selectedBody1)
            }
        }
    }

    /**
     * Composable for testing purposes, stores the necessary states to pass into the
     * RestaurantListView composable
     */
    @Composable
    private fun RestaurantListViewWithState(viewWidth: Int = 0) {
        var selectedIndex by remember { mutableStateOf(-1) }
        val updateIndex = { newIndex: Int -> selectedIndex = newIndex }

        RestaurantListView(viewWidth, selectedIndex, updateIndex, true)
    }

    /**
     * Asserts that the text style of the node matches the given text style
     */
    private fun SemanticsNodeInteraction.assertTextStyleEquals(textStyle: TextStyle) =
        assert(SemanticsMatcher.expectValue(TextStyleKey, textStyle))
}
