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
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.microsoft.device.dualscreen.testing.compose.foldableRuleChain
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.filters.MockFoldingFeature
import com.microsoft.device.dualscreen.testing.filters.SingleScreenTest
import com.microsoft.device.dualscreen.testing.rules.FoldableTestRule
import com.microsoft.device.dualscreen.testing.runner.FoldableJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(FoldableJUnit4ClassRunner::class)
class PageSwipeTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val foldableTestRule = FoldableTestRule()

    @get:Rule
    val testRule: TestRule = foldableRuleChain(composeTestRule, foldableTestRule)

    /**
     * Tests that the pages swipe only between 1 and 4 in single screen mode
     */
    @Test
    @SingleScreenTest
    fun app_singlescreen_pagesSwipeWithinLimits() {
        swipeOnePageAtATime()
    }

    /**
     * Tests that the pages swipe only between 1 and 4 when a horizontal fold is present
     */
    @Test
    @MockFoldingFeature(orientation = MockFoldingFeature.FoldingFeatureOrientation.HORIZONTAL)
    fun app_horizontalFold_pagesSwipeWithinLimits() {
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
            composeTestRule.onRoot().performTouchInput { swipeLeft() }

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
            composeTestRule.onRoot().performTouchInput { swipeRight() }

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
    @MockFoldingFeature(orientation = MockFoldingFeature.FoldingFeatureOrientation.VERTICAL)
    fun app_verticalFold_pagesSwipeWithinLimits() {
        val pageTags = listOf(R.string.page1_tag, R.string.page2_tag, R.string.page3_tag, R.string.page4_tag)

        // Swipe forwards
        for (page in 0..2) {
            // Assert current pages are visible
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page])).assertIsDisplayed()
            composeTestRule.onNodeWithTag(composeTestRule.getString(pageTags[page + 1])).assertIsDisplayed()

            // Swipe to next page
            composeTestRule.onRoot().performTouchInput { swipeLeft() }

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
            composeTestRule.onRoot().performTouchInput { swipeRight() }

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
