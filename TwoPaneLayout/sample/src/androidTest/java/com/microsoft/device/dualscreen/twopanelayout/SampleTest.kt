/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.twopanelayout

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SampleTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MainPage()
        }
    }

    private val appName = "TwoPaneLayoutSample"
    private val firstPaneText = "First pane: Dual screens give you more opportunity to display content in several different patterns. Depending on the pattern you choose, the BLUE margins near the hinge are meant to be optional. For example, if you follow the List-Detail pattern, you might have to keep in mind the BLUE margins. However, if you follow the Extended Canvas pattern, then your content can flow across the hinge without having to worry about the BLUE margins near the hinge."
    private val secondPaneText = "Second pane: The list-detail pattern has a main pane (usually with a list view) and a details pane for content. When an item in the list is selected, the details pane is updated. This pattern is naturally good for when you have a wider viewing area. It is frequently used for email and address books. Taking advantage of the two distinct screens and snapping to the natural boundary, you could use one screen to show the items list and the other to show details of the selected item."

    @Test
    fun app_launches() {
        composeTestRule.onNodeWithText(appName).assertIsDisplayed()
    }

    @Test
    fun app_canNavigateToSecondPane() {
        composeTestRule.onNodeWithText(firstPaneText).performClick()
        composeTestRule.onNodeWithText(secondPaneText).assertIsDisplayed()
    }

    @Test
    fun app_canNavigateToFirstPane() {
        composeTestRule.onNodeWithText(firstPaneText).performClick()
        composeTestRule.onNodeWithText(secondPaneText).performClick()
        composeTestRule.onNodeWithText(firstPaneText).assertIsDisplayed()
    }
}
