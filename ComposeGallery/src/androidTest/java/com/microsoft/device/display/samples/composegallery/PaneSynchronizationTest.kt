/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import androidx.annotation.StringRes
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import org.junit.Rule
import org.junit.Test

class PaneSynchronizationTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @ExperimentalTestApi
    @Test
    fun app_testListItemClickUpdatesDetailPane() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(viewModel = AppStateViewModel(), isAppSpanned = false)
            }
        }

        // Span app so two panes are visible
        device.spanFromStart()

        // Scroll to end of list
        composeTestRule.onNode(
            hasScrollAction() and hasTestTag(getString(R.string.gallery_list))
        ).performScrollToIndex(7)

        // Click on last list item
        val lastItem = DataProvider.imageModels.last()
        composeTestRule.onNode(
            hasAnyAncestor(hasTestTag(getString(R.string.gallery_list)))
                and hasContentDescription(lastItem.contentDescription)
        ).performClick()

        // Check that detail pane has updated with the correct information
        composeTestRule.onNode(
            hasText(lastItem.id)
                and hasAnySibling(hasContentDescription(lastItem.contentDescription))
        ).assertIsDisplayed()
        composeTestRule.onNode(
            hasContentDescription(lastItem.contentDescription)
                and hasAnySibling(hasText(lastItem.id))
        ).assertIsDisplayed()
    }

    @Test
    fun app_testSelectionPersistsAfterSpan() {
        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(viewModel = AppStateViewModel(), isAppSpanned = false)
            }
        }

        // Click on third surface duo entry
        composeTestRule.onNodeWithContentDescription(getString(R.string.duo3_content_des))
            .performClick()

        // Check that detail view of third surface duo is displayed
        composeTestRule.onNodeWithText(getString(R.string.duo3_id)).assertIsDisplayed()

        // Span the app
        device.spanFromStart()

        // Check that third surface duo image is still displayed
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.duo3_content_des))
                and hasAnySibling(hasText(getString(R.string.duo3_id)))
        ).assertIsDisplayed()
    }

    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }
}
