/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.ui.theme.TwoPageAppTheme
import com.microsoft.device.display.samples.twopage.utils.PageLayout
import com.microsoft.device.dualscreen.testutils.getString
import org.junit.Rule
import org.junit.Test

class PageContentTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Tests that page content is scrollable
     */
    @Test
    fun page_contentIsScrollable() {
        composeTestRule.setContent {
            TwoPageAppTheme {
                FullPage()
            }
        }

        // Assert last text element in not visible
        composeTestRule.onNodeWithText(composeTestRule.getString(R.string.article_title)).assertIsNotDisplayed()

        // Assert page has scroll action
        composeTestRule.onNode(hasScrollAction()).assertExists()

        // Scroll to end of page content and assert last element is now visible
        composeTestRule.onNodeWithText(composeTestRule.getString(R.string.article_title)).performScrollTo()
        composeTestRule.onNodeWithText(composeTestRule.getString(R.string.article_title)).assertIsDisplayed()
    }

    /**
     * Tests that page numbers are always visible, even if the content isn't scrolled all the way to the bottom
     */
    @Test
    fun page_pageNumberIsAlwaysVisible() {
        val pageNumberText = "Test page number"

        composeTestRule.setContent {
            TwoPageAppTheme {
                FullPage(pageNumberText)
            }
        }

        // Assert that page number is visible at the top of the content
        composeTestRule.onNodeWithText(pageNumberText).assertIsDisplayed()

        // Scroll to end of page content and assert that page number is still visible
        composeTestRule.onNodeWithText(composeTestRule.getString(R.string.article_title)).performScrollTo()
        composeTestRule.onNodeWithText(pageNumberText).assertIsDisplayed()
    }

    /**
     * Mock page with lots of content, so not all of it is visible at once
     */
    @Composable
    private fun FullPage(pageNumber: String = "test page number") {
        PageLayout(modifier = Modifier.fillMaxSize(), pageNumber = pageNumber) {
            for (i in 0..10) {
                Text(text = stringResource(R.string.page4_text1))
                Spacer(Modifier.height(10.dp))
            }
            Text(text = stringResource(R.string.article_title), fontWeight = FontWeight.Bold)
        }
    }
}
