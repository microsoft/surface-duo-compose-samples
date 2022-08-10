/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.microsoft.device.display.samples.listdetail.ui.theme.ListDetailComposeSampleTheme
import com.microsoft.device.display.samples.listdetail.ui.view.DetailViewTopBar
import com.microsoft.device.display.samples.listdetail.ui.view.ListDetailApp
import com.microsoft.device.display.samples.listdetail.ui.view.ListViewTopBar
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.twopanelayout.twopanelayout.TwoPaneScopeTest
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    /**
     * Tests that app title shows in the top bar of the list view
     */
    @Test
    fun listBar_showsAppTitle() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                ListViewTopBar()
            }
        }

        composeTestRule.onNode(hasText(getString(R.string.app_name))).assertExists()
    }

    /**
     * Tests that the back button is hidden in the top bar of the detail view when dual-screen
     */
    @Test
    fun detailBar_buttonHiddenInDualScreenMode() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                TwoPaneScopeTest(isSinglePane = false).DetailViewTopBar()
            }
        }

        // Assert the back button is not shown
        composeTestRule.onNodeWithContentDescription(getString(R.string.back_to_list)).assertDoesNotExist()
    }

    /**
     * Tests that the back button shows in the top bar of the detail view single screen
     */
    @Test
    fun detailBar_buttonShowsInSingleScreenMode() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                TwoPaneScopeTest().DetailViewTopBar()
            }
        }

        // Assert the back button is shown
        composeTestRule.onNodeWithContentDescription(getString(R.string.back_to_list)).assertExists()
    }

    /**
     * Tests that the back button can switch the UI from the detail
     * view to the list view on the single screen mode
     */
    @Test
    fun app_backToListButtonInSingleScreenMode() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                ListDetailApp()
            }
        }

        // Assert the list view is shown first
        composeTestRule.onNodeWithTag(getString(R.string.list_view)).assertExists()

        // Assert the back button is not shown yet
        composeTestRule.onNodeWithContentDescription(getString(R.string.back_to_list)).assertDoesNotExist()

        // Click the first image from the list
        val index = 0
        composeTestRule.onNodeWithContentDescription(index.toString()).performClick()

        // Assert the correct detail image is shown
        composeTestRule.onNodeWithContentDescription(getString(R.string.image_tag) + index.toString())
            .assertExists()

        // Assert the back button is now shown
        composeTestRule.onNodeWithContentDescription(getString(R.string.back_to_list)).assertExists()

        // Click the back button to go back to the list view
        composeTestRule.onNodeWithContentDescription(getString(R.string.back_to_list)).performClick()

        // Assert the detail view is not shown
        composeTestRule.onNodeWithTag(getString(R.string.detail_view)).assertDoesNotExist()

        // Assert the list view is now shown
        composeTestRule.onNodeWithTag(getString(R.string.list_view)).assertExists()
    }
}
