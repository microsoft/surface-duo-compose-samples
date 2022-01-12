/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.window.layout.WindowMetricsCalculator
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.twopage.ui.theme.TwoPageAppTheme
import com.microsoft.device.display.samples.twopage.ui.view.TwoPageAppContent
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.testutils.simulateHorizontalFold
import com.microsoft.device.dualscreen.testutils.simulateVerticalFold
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

    /**
     * Tests that the three dual-screen page pairs ([1,2], [2,3], and [3,4]) only swipe between each other when
     * a vertical fold is present
     */
    @Test
    fun app_verticalFold_pagesSwipeWithinLimits() {
        composeTestRule.setContent {
            val configuration = LocalConfiguration.current
            val windowMetrics = remember(configuration) {
                WindowMetricsCalculator.getOrCreate()
                    .computeCurrentWindowMetrics(composeTestRule.activity)
            }
            val windowWidthDp = with(LocalDensity.current) {
                windowMetrics.bounds.width().toDp().value
            }
            // REVISIT: look into "ProvideWindowInsets" from accompanist library so hardcoded value can be removed
            val insetsPadding = if (windowWidthDp > configuration.smallestScreenWidthDp) 50 else 0
            val pageWidth = (windowWidthDp.toInt() - insetsPadding) / 2

            TwoPageAppTheme {
                TwoPageAppContent(
                    viewWidth = pageWidth,
                    isDualScreen = true,
                    foldSize = 0
                )
            }
        }

        // Simulate vertical fold
        publisherRule.simulateVerticalFold(composeTestRule)

        val pageTags = listOf(R.string.page1_tag, R.string.page2_tag, R.string.page3_tag, R.string.page4_tag)

        // Swipe forwards
        for (page in 0..2) {
            // Assert current pages are visible
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page + 1])).assertIsDisplayed()

            // Swipe to next page
            composeTestRule.onRoot().performGesture { swipeLeft() }

            // Assert current page is no longer visible (unless on the last page)
            when (page) {
                2 ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
                else ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsNotDisplayed()
            }
        }

        // Swipe backwards
        for (page in 3 downTo 1) {
            // Assert current pages are visible
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page - 1])).assertIsDisplayed()

            // Swipe to previous page
            composeTestRule.onRoot().performGesture { swipeRight() }

            // Assert current page is no longer visible (unless on the first page)
            when (page) {
                1 ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
                else ->
                    composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsNotDisplayed()
            }
        }
    }
}
