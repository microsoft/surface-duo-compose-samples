/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.extendedcanvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.microsoft.device.display.samples.extendedcanvas.ui.ExtendedCanvasAppsTheme
import com.microsoft.device.dualscreen.testing.getString
import org.junit.Rule
import org.junit.Test

class ExtendedCanvasTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Tests that the top bar shows in the app
     */
    @Test
    fun topBar_shows() {
        composeTestRule.setContent {
            ExtendedCanvasAppsTheme {
                ExtendedCanvasApp()
            }
        }

        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.top_bar))
            .assertIsDisplayed()
    }

    /**
     * Tests that the map view image can be dragged
     */
    @Test
    fun mapView_testImageDrags() {
        composeTestRule.setContent {
            ExtendedCanvasAppsTheme {
                ExtendedCanvasApp()
            }
        }

        // Get node for the map image
        val mapImageNode =
            composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image))

        // Assert the map image is shown
        mapImageNode.assertIsDisplayed()

        // Drag the map to the left
        mapImageNode.performTouchInput { swipeLeft() }

        val defaultImageOffset = Offset.Zero

        // Make sure bitmap offset is not the same anymore
        mapImageNode.assertImageOffsetNotEquals(defaultImageOffset)
    }

    /**
     * Asserts that the image offset of the node doesn't match the given offset
     */
    private fun SemanticsNodeInteraction.assertImageOffsetNotEquals(imageOffset: Offset) =
        assert(!SemanticsMatcher.expectValue(ImageOffsetKey, imageOffset))
}
