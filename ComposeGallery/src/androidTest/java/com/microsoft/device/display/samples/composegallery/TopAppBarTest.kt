/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.lifecycle.MutableLiveData
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.DetailPane
import com.microsoft.device.display.samples.composegallery.ui.view.ListPane
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
    private val imageSelection = MutableLiveData(0)
    private val index = 0

    @Test
    fun listPane_iconShowsInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, true, imageSelection)
            }
        }

        // isDualMode is true, icon should be hidden
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_detail))
            .assertDoesNotExist()
    }

    @Test
    fun listPane_iconHiddenInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, false, imageSelection)
            }
        }

        // isDualMode is false, icon should show
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_detail))
            .assertIsDisplayed()
    }

    @Test
    fun detailPane_iconShowsInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, true, index)
            }
        }

        // isDualMode is true, icon should be hidden
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_list))
            .assertDoesNotExist()
    }

    @Test
    fun detailPane_iconHiddenInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, false, index)
            }
        }

        // isDualMode is false, icon should show
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_list))
            .assertIsDisplayed()
    }

    @Test
    fun listPane_showsAppNameInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, false, imageSelection)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(getString(R.string.top_app_bar)))
                and hasText(getString(R.string.app_name))
        ).assertIsDisplayed()
    }

    @Test
    fun listPane_showsAppNameInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ListPane(models, true, imageSelection)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(getString(R.string.top_app_bar)))
                and hasText(getString(R.string.app_name))
        ).assertIsDisplayed()
    }

    @Test
    fun detailPane_showsAppNameInSingleMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, false, index)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(getString(R.string.top_app_bar)))
                and hasText(getString(R.string.app_name))
        ).assertIsDisplayed()
    }

    @Test
    fun detailPane_blankTitleInDualMode() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                DetailPane(models, true, index)
            }
        }

        composeTestRule.onNode(
            hasParent(hasTestTag(getString(R.string.top_app_bar)))
                and hasText("")
        ).assertExists()
    }

    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }
}
