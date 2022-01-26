/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.extendedcanvas

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeLeft
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.extendedcanvas.ui.ExtendedCanvasAppsTheme
import com.microsoft.device.dualscreen.testutils.compare
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.testutils.zoomIn
import com.microsoft.device.dualscreen.testutils.zoomOut
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class ExtendedCanvasTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

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

        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image))
            .assertIsDisplayed()

        // Take screenshot of initial map image state
        val original = composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).captureToImage()
            .asAndroidBitmap()

        // Drag the map to the left
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).performGesture {
            swipeLeft()
        }

        // Take screenshot of new map image state
        val afterSwipe =
            composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).captureToImage()
                .asAndroidBitmap()

        // Make sure bitmaps are not the same anymore
        assert(!original.compare(afterSwipe))
    }

    /**
     * Tests that the map image can zoom in
     */
    @Test
    fun mapView_testImageZoomsIn() {
        composeTestRule.setContent {
            ExtendedCanvasAppsTheme {
                ExtendedCanvasApp()
            }
        }

        // Take screenshot of initial map image state
        val original = composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).captureToImage()
            .asAndroidBitmap()

        // Zoom in on map image map
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).performGesture {
            zoomIn()
        }

        // Take screenshot of new state
        val afterZoom =
            composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).captureToImage()
                .asAndroidBitmap()

        // Make sure bitmaps are not the same anymore
        assert(!original.compare(afterZoom))
    }

    /**
     * Test that the map image can zoom out
     */
    @Test
    fun mapView_testImageZoomsOut() {
        composeTestRule.setContent {
            ExtendedCanvasAppsTheme {
                ExtendedCanvasApp()
            }
        }

        // Take screenshot of initial map image state
        val original = composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).captureToImage()
            .asAndroidBitmap()

        // Zoom out on map image map and take screenshot of new state
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).performGesture {
            zoomOut()
        }
        val afterZoom =
            composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.map_image)).captureToImage()
                .asAndroidBitmap()

        // Make sure bitmaps are not the same anymore
        assert(!original.compare(afterZoom))
    }
}
