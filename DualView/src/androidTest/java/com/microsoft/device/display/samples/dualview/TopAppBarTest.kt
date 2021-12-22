/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import androidx.compose.material.Scaffold
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewAppTheme
import com.microsoft.device.display.samples.dualview.ui.view.RestaurantTopBar
import com.microsoft.device.dualscreen.testutils.getString
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

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
}
