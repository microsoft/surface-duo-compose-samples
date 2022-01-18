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
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.twopage.ui.theme.TwoPageAppTheme
import com.microsoft.device.display.samples.twopage.ui.view.TwoPageAppContent
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.testutils.simulateHorizontalFold
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class PageSwipeTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Tests that the pages swipe only between 1 and 4 in single screen mode
     */
    @Test
    fun app_singlescreen_pagesSwipeWithinLimits() {
        composeTestRule.setContent {
            TwoPageAppTheme {
                TwoPageAppContent(viewWidth = 0, isDualScreen = false, foldSize = 0)
            }
        }

        swipeOnePageAtATime()
    }

    /**
     * Tests that the pages swipe only between 1 and 4 when a horizontal fold is present
     */
    @Test
    fun app_horizontalFold_pagesSwipeWithinLimits() {
        composeTestRule.setContent {
            TwoPageAppTheme {
                TwoPageAppContent(viewWidth = 0, isDualScreen = false, foldSize = 0)
            }
        }

        // Simulate horizontal fold
        publisherRule.simulateHorizontalFold(composeTestRule)

        swipeOnePageAtATime()
    }

    /**
     * Swipes forward through all four pages and asserts that each page is visible at the correct time,
     * then swipes backwards through all the pages again and asserts that each page is visible at the correct time
     */
    private fun swipeOnePageAtATime() {
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
