/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewAppTheme
import com.microsoft.device.display.samples.dualview.ui.view.DualViewApp
import com.microsoft.device.display.samples.dualview.ui.view.MapTopBar
import com.microsoft.device.display.samples.dualview.ui.view.RestaurantTopBar
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.twopanelayout.twopanelayout.TwoPaneScopeTest
import com.microsoft.device.dualscreen.windowstate.WindowState
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Tests that the map icon shows in the restaurant top bar when single screen
     */
    @Test
    fun restaurantBar_iconShowsInSingleScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest().RestaurantTopBar()
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_map))
            .assertIsDisplayed()
    }

    /**
     * Tests that the map icon is hidden in the restaurant top bar when dual-screen
     */
    @Test
    fun restaurantBar_iconHiddenInDualScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest(isSinglePane = false).RestaurantTopBar()
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_map))
            .assertDoesNotExist()
    }

    /**
     * Tests that the restaurant icon shows in the map top bar when single screen
     */
    @Test
    fun mapBar_iconShowsInSingleScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest().MapTopBar()
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_rest))
            .assertIsDisplayed()
    }

    /**
     * Tests that the restaurant icon is hidden in the map top bar when dual-screen
     */
    @Test
    fun mapBar_iconHiddenInDualScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest(isSinglePane = false).MapTopBar()
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_rest))
            .assertDoesNotExist()
    }

    /**
     * Tests that app title shows in the restaurant top bar when single screen
     */
    @Test
    fun restaurantBar_showsAppTitleInSingleScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest().RestaurantTopBar()
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.restaurant_top_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    /**
     * Tests that app title shows in the restaurant top bar when dual-screen
     */
    @Test
    fun restaurantBar_showsAppTitleInDualScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest().RestaurantTopBar()
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.restaurant_top_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    /**
     * Tests that app title shows in the map top bar when single screen
     */
    @Test
    fun mapBar_showsAppTitleInSingleScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest().MapTopBar()
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.map_top_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    /**
     * Tests that the map top bar is blank when dual-screen
     */
    @Test
    fun mapBar_isBlankInDualScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                TwoPaneScopeTest(isSinglePane = false).MapTopBar()
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.map_top_bar)))
                and hasText("")
        ).assertExists()
    }

    /**
     * Tests that the top bar icons switch between the restaurant and map views in single screen mode
     */
    @Test
    fun app_iconsSwitchViewsInSingleScreenMode() {
        composeTestRule.setContent {
            DualViewAppTheme {
                DualViewApp(WindowState())
            }
        }

        // Assert restaurant view is shown first
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.restaurant_top_bar)).assertExists()

        // Click map icon to switch views
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_map)).performClick()

        // Assert map view is now shown
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_top_bar)).assertExists()

        // Click restaurant icon to switch views
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_rest)).performClick()

        // Assert restaurant view is shown again
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.restaurant_top_bar)).assertExists()
    }
}
