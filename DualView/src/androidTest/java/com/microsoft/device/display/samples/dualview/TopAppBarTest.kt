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
import com.microsoft.device.display.samples.dualview.ui.view.MapTopBar
import com.microsoft.device.display.samples.dualview.ui.view.RestaurantTopBar
import com.microsoft.device.dualscreen.testutils.getString
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
//    private val publisherRule = WindowLayoutInfoPublisherRule()
//
//    @get: Rule
//    val testRule: TestRule
//
//    init {
//        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
//        RuleChain.outerRule(composeTestRule)
//    }

    @Test
    fun restaurantBar_iconShowsInSingleScreenMode() {
        composeTestRule.setContent {
            RestaurantTopBar(isDualScreen = false)
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_map))
            .assertIsDisplayed()
    }

    @Test
    fun restaurantBar_iconHiddenInDualScreenMode() {
        composeTestRule.setContent {
            RestaurantTopBar(isDualScreen = true)
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_map))
            .assertDoesNotExist()
    }

    @Test
    fun mapBar_iconShowsInSingleScreenMode() {
        composeTestRule.setContent {
            MapTopBar(isDualScreen = false)
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_rest))
            .assertIsDisplayed()
    }

    @Test
    fun mapBar_iconHiddenInDualScreenMode() {
        composeTestRule.setContent {
            MapTopBar(isDualScreen = true)
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_rest))
            .assertDoesNotExist()
    }

    @Test
    fun restaurantBar_showsAppTitleInSingleScreenMode() {
        composeTestRule.setContent {
            RestaurantTopBar(isDualScreen = false)
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.restaurant_top_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    @Test
    fun restaurantBar_showsAppTitleInDualScreenMode() {
        composeTestRule.setContent {
            RestaurantTopBar(isDualScreen = true)
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.restaurant_top_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    @Test
    fun mapBar_showsAppTitleInSingleScreenMode() {
        composeTestRule.setContent {
            MapTopBar(isDualScreen = false)
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.map_top_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    @Test
    fun mapBar_isBlankInDualScreenMode() {
        composeTestRule.setContent {
            MapTopBar(isDualScreen = true)
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.map_top_bar)))
                and hasText("")
        ).assertExists()
    }
}
