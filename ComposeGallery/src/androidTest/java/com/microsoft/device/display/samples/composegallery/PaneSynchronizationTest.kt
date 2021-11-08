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
import androidx.lifecycle.ViewModelProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PaneSynchronizationTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private lateinit var viewModel: AppStateViewModel
    private lateinit var windowLayoutInfo: Flow<WindowLayoutInfo>

    /**
     * Set up viewmodel and window layout info params and set the content of the test rule
     * to the entire app
     */
    @Before
    fun app_setUp() {
        viewModel = ViewModelProvider(composeTestRule.activity).get(AppStateViewModel::class.java)
        windowLayoutInfo = composeTestRule.activity.windowInfoRepository().windowLayoutInfo

        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(viewModel = viewModel, windowLayoutInfo = windowLayoutInfo)
            }
        }
    }

    /**
     * Test that clicking an item in the list pane updates the image shown in the detail pane
     */
    @ExperimentalTestApi
    @Test
    fun app_testListItemClickUpdatesDetailPane() {
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

    /**
     * Test that a selection made when unspanned is remembered when span state changes
     */
    @Test
    fun app_testSelectionPersistenceAfterSpan() {
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

        // Unspan the app
        device.unspanToStart()

        // Check that detail view of third surface duo is still displayed
        composeTestRule.onNodeWithText(getString(R.string.duo3_id)).assertIsDisplayed()
    }

    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }
}
