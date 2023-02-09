/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsProperties.VerticalScrollAxisRange
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.ui.theme.NavigationRailAppTheme
import com.microsoft.device.display.samples.navigationrail.ui.view.GalleryView
import com.microsoft.device.display.samples.navigationrail.ui.view.NavigationRailApp
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.windowstate.WindowState
import org.junit.Rule
import org.junit.Test

class GalleryTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    /**
     * Tests that the content of the gallery view is vertically scrollable
     */
    @Test
    fun galleryView_testContentScrolls() {
        composeTestRule.setContent {
            var id by rememberSaveable { mutableStateOf(0) }
            NavigationRailAppTheme {
                GalleryView(
                    galleryList = DataProvider.plantList,
                    currentImageId = id,
                    onImageSelected = { newId -> id = newId },
                    horizontalPadding = 10.dp,
                    paddingValues = PaddingValues()
                )
            }
        }

        // Assert that last plant is not visible at the start
        val lastEntry = DataProvider.plantList.last()
        val lastEntryDescription = getString(R.string.image_description, lastEntry.name, lastEntry.id)
        composeTestRule.onNodeWithContentDescription(lastEntryDescription).assertDoesNotExist()

        // Assert that gallery has vertical scroll action, then scroll to the end of the gallery
        composeTestRule.onNode(SemanticsMatcher.keyIsDefined(VerticalScrollAxisRange)).assertExists()
            .performTouchInput { swipeUp() }

        // Scroll to last item and assert that it is now visible
        composeTestRule.onNodeWithContentDescription(lastEntryDescription).performScrollTo().assertIsDisplayed()
    }

    /**
     * Tests that the gallery view fills the whole window in dual landscape mode
     */
    @Test
    fun app_galleryShowsInDualLandscape() {
        composeTestRule.setContent {
            NavigationRailAppTheme {
                NavigationRailApp(WindowState(hasFold = true, foldIsHorizontal = true, foldIsSeparating = true))
            }
        }

        // Assert "plants" title is visible in gallery view
        composeTestRule.onNodeWithText(getString(R.string.plants).lowercase()).assertIsDisplayed()

        // Assert that placeholder view isn't shown on start up (only one pane)
        val placeholderText = getString(R.string.placeholder_msg, getString(R.string.plants).lowercase())
        composeTestRule.onNodeWithText(placeholderText).assertDoesNotExist()
    }
}
