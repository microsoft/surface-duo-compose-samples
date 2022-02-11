/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import com.microsoft.device.display.samples.composegallery.ui.view.DetailPane
import com.microsoft.device.display.samples.composegallery.ui.view.ListPane
import com.microsoft.device.dualscreen.testing.getString
import com.microsoft.device.dualscreen.windowstate.WindowState
import org.junit.Rule
import org.junit.Test

/**
 * Tests that the correct text and icons appear in the top app bars for the list and details
 * panes in single vs. dual mode
 */
class TopAppBarTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val models = DataProvider.imageModels
    private val selectedImageIndex = 0
    private val updateImageIndex = { _: Int -> }
    private val index = 0
    private val lazyListState @Composable get() = rememberLazyListState()

    /**
     * Test that picture/detail icon is hidden in the list pane when the app is spanned
     */
    @Test
    fun listPane_iconHiddenInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, true, selectedImageIndex, updateImageIndex, lazyListState)
            }
        }

        // isDualMode is true, icon should be hidden
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_detail))
            .assertDoesNotExist()
    }

    /**
     * Test that picture/detail icon appears in the list pane when the app is unspanned
     */
    @Test
    fun listPane_iconShowsInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, false, selectedImageIndex, updateImageIndex, lazyListState)
            }
        }

        // isDualMode is false, icon should show
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_detail))
            .assertIsDisplayed()
    }

    /**
     * Test that list icon is hidden in the detail pane when the app in spanned
     */
    @Test
    fun detailPane_iconHiddenInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, true, index)
            }
        }

        // isDualMode is true, icon should be hidden
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_list))
            .assertDoesNotExist()
    }

    /**
     * Test that list icon appears in the detail pane when the app is unspanned
     */
    @Test
    fun detailPane_iconShowsInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, false, index)
            }
        }

        // isDualMode is false, icon should show
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_list))
            .assertIsDisplayed()
    }

    /**
     * Test that the list pane top bar displays the app name when the app is unspanned
     */
    @Test
    fun listPane_showsAppNameInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, false, selectedImageIndex, updateImageIndex, lazyListState)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.top_app_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertIsDisplayed()
    }

    /**
     * Test that the list pane top bar displays the app name when the app is spanned
     */
    @Test
    fun listPane_showsAppNameInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, true, selectedImageIndex, updateImageIndex, lazyListState)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.top_app_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertIsDisplayed()
    }

    /**
     * Test that the detail pane top bar displays the app name when the app is unspanned
     */
    @Test
    fun detailPane_showsAppNameInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, false, index)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.top_app_bar)))
                and hasText(composeTestRule.getString(R.string.app_name))
        ).assertIsDisplayed()
    }

    /**
     * Test that the detail pane top bar displays a blank string when the app is spanned
     */
    @Test
    fun detailPane_blankTitleInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, true, index)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(composeTestRule.getString(R.string.top_app_bar)))
                and hasText("")
        ).assertExists()
    }

    /**
     * Test that top bar icons navigate to the other panes
     */
    @Test
    fun app_testTopBarIconsSwitchPanes() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(WindowState())
            }
        }

        // Check that list pane is currently displayed
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.gallery_list))
            .assertIsDisplayed()

        // Click on picture/detail icon
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_detail))
            .performClick()

        // Check that list pane is no longer displayed
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.gallery_list))
            .assertDoesNotExist()

        // Click on list icon
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.switch_to_list))
            .performClick()

        // Check that list pane is displayed again
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.gallery_list))
            .assertIsDisplayed()
    }
}
