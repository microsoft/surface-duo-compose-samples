/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.microsoft.device.display.samples.twopage.ui.theme.TwoPageAppTheme
import com.microsoft.device.display.samples.twopage.ui.view.TwoPageAppContent
import com.microsoft.device.dualscreen.testutils.getString
import org.junit.Rule
import org.junit.Test

class PageSwipeTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_singlescreen_testPagesSwipesWithinLimits() {
        composeTestRule.setContent {
            TwoPageAppTheme {
                TwoPageAppContent(viewWidth = 0, isDualScreen = false, foldSize = 0)
            }
        }

        val pageTags = listOf(R.string.page1_tag, R.string.page2_tag, R.string.page3_tag, R.string.page4_tag)

        // Swipe forwards
        for (page in 0..3) {
            // Assert current page is visible
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()

            // Swipe to next page
            composeTestRule.onRoot().performGesture { swipeLeft() }

            // Assert current page is no longer visible (unless on the last page)
            when (page) {
                3 ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
                else ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsNotDisplayed()
            }
        }

        // Swipe backwards
        for (page in 3 downTo 0) {
            // Assert current page is visible
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()

            // Swipe to previous page
            composeTestRule.onRoot().performGesture { swipeRight() }

            // Assert current page is no longer visible (unless on the first page)
            when (page) {
                0 ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
                else ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsNotDisplayed()
            }
        }
    }
}
