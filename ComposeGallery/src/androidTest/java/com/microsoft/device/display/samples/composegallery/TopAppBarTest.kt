/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.ui.ComposeGalleryTheme
import com.microsoft.device.display.samples.composegallery.ui.view.ComposeGalleryApp
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun composeGalleryTopAppBarTest_iconsShowWhenUnspanned() {
        var isDualMode = false

        composeTestRule.setContent {
            ComposeGalleryTheme {
                ComposeGalleryApp(AppStateViewModel(), isDualMode)
            }
        }

        // Check that icons are shown when app is unspanned
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_detail))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_list))
            .assertIsDisplayed()

        //isDualMode = true // REVISIT: do we really need this as a state input then?
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            .swipe(leftX(), bottomY(Device.Duo), middleX(), middleY(), spanSteps())

        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_detail))
            .assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(getString(R.string.switch_to_list))
            .assertDoesNotExist()
    }

    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }
}
