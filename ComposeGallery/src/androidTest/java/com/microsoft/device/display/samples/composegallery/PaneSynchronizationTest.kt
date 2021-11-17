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
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

private const val USE_SWIPE_GESTURE = false

class PaneSynchronizationTest {
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Test that clicking an item in the list pane updates the image shown in the detail pane
     */
    @ExperimentalTestApi
    @Test
    fun app_testListItemClickUpdatesDetailPane() {
        val viewModel =
            ViewModelProvider(composeTestRule.activity).get(AppStateViewModel::class.java)
        val windowLayoutInfo = composeTestRule.activity.windowInfoRepository().windowLayoutInfo

        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(viewModel = viewModel, windowLayoutInfo = windowLayoutInfo)
            }
        }

        // Span app so two panes are visible
        if (USE_SWIPE_GESTURE)
            device.spanFromStart()
        publisherRule.simulateVerticalFold(composeTestRule)

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

        // Check that viewmodel contains correct selection index
        check(viewModel.imageSelectionLiveData.value == 7) { "Expected: 7 Actual: ${viewModel.imageSelectionLiveData.value}" }

        // Check that detail pane has updated with the correct information
        composeTestRule.onNode(
            hasText(lastItem.id)
                and hasAnySibling(hasContentDescription(lastItem.contentDescription))
        ).assertIsDisplayed()
        composeTestRule.onNode(
            hasContentDescription(lastItem.contentDescription)
                and hasAnySibling(hasText(lastItem.id))
        ).assertIsDisplayed()

        // REVISIT: added to make tests consistent, can remove when state isn't saved between tests
        // Close the app
        device.closeStart()
    }

    /**
     * Test that a selection made when unspanned is remembered when span state changes
     */
    @Test
    fun app_testSelectionPersistenceAfterSpan() {
        val viewModel =
            ViewModelProvider(composeTestRule.activity).get(AppStateViewModel::class.java)
        val windowLayoutInfo = composeTestRule.activity.windowInfoRepository().windowLayoutInfo

        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(viewModel = viewModel, windowLayoutInfo = windowLayoutInfo)
            }
        }

        // Click on third surface duo entry
        composeTestRule.onNodeWithContentDescription(getString(R.string.duo3_content_des))
            .performClick()

        // Check that detail view of third surface duo is displayed
        composeTestRule.onNodeWithText(getString(R.string.duo3_id)).assertIsDisplayed()

        // Check that viewmodel contains correct selection index
        check(viewModel.imageSelectionLiveData.value == 2) { "Expected: 2 Actual: ${viewModel.imageSelectionLiveData.value}" }

        // Span the app
        if (USE_SWIPE_GESTURE)
            device.spanFromStart()
        publisherRule.simulateVerticalFold(composeTestRule)

        // Check that third surface duo image is still displayed
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.duo3_content_des))
                and hasAnySibling(hasText(getString(R.string.duo3_id)))
        ).assertIsDisplayed()

        // Unspan the app
        if (USE_SWIPE_GESTURE)
            device.unspanToStart()
        publisherRule.simulateNoFold()

        // Check that detail view of third surface duo is still displayed
        composeTestRule.onNodeWithText(getString(R.string.duo3_id)).assertIsDisplayed()

        // REVISIT: added to make tests consistent, can remove when state isn't saved between tests
        // Return to list view
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_list))
            .performClick()

        // Close the app
        device.closeStart()
    }

    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }
}
