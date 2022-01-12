/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.microsoft.device.display.samples.listdetail.ui.theme.ListDetailComposeSampleTheme
import com.microsoft.device.display.samples.listdetail.ui.view.DetailViewTopBar
import com.microsoft.device.display.samples.listdetail.ui.view.ListDetailApp
import com.microsoft.device.display.samples.listdetail.ui.view.ListViewTopBar
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.windowstate.WindowState
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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

        composeTestRule.onNode(
            hasText(composeTestRule.getString(R.string.app_name))
        ).assertExists()
    }

    /**
     * Tests that the back button is hidden in the top bar of the detail view when dual-screen
     */
    @Test
    fun detailBar_buttonHiddenInDualScreenMode() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                DetailViewTopBar(isDualScreen = true)
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.back_to_list))
            .assertDoesNotExist()
    }

    /**
     * Tests that the back button shows in the top bar of the detail view single screen
     */
    @Test
    fun detailBar_buttonShowsInSingleScreenMode() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                DetailViewTopBar(isDualScreen = false)
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.back_to_list))
            .assertExists()
    }

//    @Test
//    fun app_iconsSwitchViewsInSingleScreenMode() {
//        composeTestRule.setContent {
//            ListDetailComposeSampleTheme {
//                ListDetailApp(WindowState())
//            }
//        }
//
//        // Assert detail view is shown first
//        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.list_view)).assertExists()
//
//        // Click map icon to switch views
//        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_map)).performClick()
//
//        // Assert map view is now shown
//        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_top_bar)).assertExists()
//
//        // Click restaurant icon to switch views
//        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_rest)).performClick()
//
//        // Assert restaurant view is shown again
//        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.restaurant_top_bar)).assertExists()
//    }
}
