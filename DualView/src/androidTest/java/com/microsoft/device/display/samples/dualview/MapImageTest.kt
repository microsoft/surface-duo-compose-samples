/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.GestureScope
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.bottom
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.left
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.pinch
import androidx.compose.ui.test.right
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.top
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.dualview.models.restaurants
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewAppTheme
import com.microsoft.device.display.samples.dualview.ui.view.DualViewApp
import com.microsoft.device.display.samples.dualview.ui.view.MapView
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.testutils.simulateHorizontalFold
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

const val PINCH_MILLIS: Long = 500

class MapImageTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()
    private val viewSize = 400

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Temporary test to save screenshots of composables to use as reference images - uncomment to run
     * and follow instructions below to transfer images from device to computer:
     * https://stackoverflow.com/questions/40323126/where-do-i-find-the-saved-image-in-android
     * You may also need to add write permissions to AndroidManifest.xml in your own project.
     */
    @ExperimentalTestApi
    // @Test
    fun app_saveScreenshots() {
        composeTestRule.setContent {
            DualViewAppTheme {
                DualViewApp(windowState = composeTestRule.activity.rememberWindowState(), viewSize = viewSize)
            }
        }

        // Simulate horizontal fold
        publisherRule.simulateHorizontalFold(composeTestRule)

        clickEveryRestaurant(::saveScreenshotToDevice)
    }

    @ExperimentalTestApi
    @Test
    fun app_horizontalFold_mapUpdatesAfterRestaurantClick() {
        composeTestRule.setContent {
            DualViewAppTheme {
                DualViewApp(windowState = composeTestRule.activity.rememberWindowState(), viewSize = viewSize)
            }
        }

        // Simulate horizontal fold
        publisherRule.simulateHorizontalFold(composeTestRule)

        clickEveryRestaurant()
    }

    @ExperimentalTestApi
    private fun clickEveryRestaurant(action: (String, SemanticsNodeInteraction) -> Unit = ::assertScreenshotMatchesReference) {
        // Create list of reference images file names (from src/androidTest/assets folder)
        val referenceAssets =
            mutableListOf("unselected", "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth")
        referenceAssets.forEachIndexed { index, prefix ->
            referenceAssets[index] = prefix.plus("_map.png")
        }

        // Click each restaurant item and perform action on map image
        restaurants.forEachIndexed { index, rest ->
            action(
                referenceAssets[index],
                composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image))
            )

            composeTestRule.onNode(hasScrollToIndexAction()).performScrollToIndex(index)

            composeTestRule.onNode(
                hasContentDescription(composeTestRule.getString(rest.title)) and hasParent(
                    hasAnySibling(hasText(composeTestRule.getString(R.string.list_title)))
                )
            ).performClick()
        }
    }

    @Test
    fun mapView_testImageDrags() {
        composeTestRule.setContent {
            DualViewAppTheme {
                MapView(selectedIndex = 0, viewSize = viewSize)
            }
        }

        // Take screenshot of initial map image state
        val original = composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image)).captureToImage()
            .asAndroidBitmap()

        // Swipe map and take screenshot of new state
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.zoomable_map_image)).performGesture {
            swipeDown()
        }
        val afterSwipe =
            composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image)).captureToImage()
                .asAndroidBitmap()

        // Make sure bitmaps are not the same anymore
        assert(!original.compare(afterSwipe))
    }

    data class ZoomCoordinates(
        val leftOuter: Offset,
        val leftInner: Offset,
        val rightInner: Offset,
        val rightOuter: Offset
    ) {
        override fun toString(): String {
            return "[LeftOuter: $leftOuter, LeftInner: $leftInner, RightInner: $rightInner, RightOuter: $rightOuter]"
        }
    }

    private fun GestureScope.zoomIn() {
        val coords = setupZoomCoords()
        Log.d("MapImageTest", "Zooming in: $coords")
        pinch(coords.leftInner, coords.leftOuter, coords.rightInner, coords.rightOuter, PINCH_MILLIS)
    }

    private fun GestureScope.zoomOut() {
        val coords = setupZoomCoords()
        Log.d("MapImageTest", "Zooming out: $coords")
        pinch(coords.leftOuter, coords.leftInner, coords.rightOuter, coords.rightInner, PINCH_MILLIS)
    }

    private fun GestureScope.setupZoomCoords(): ZoomCoordinates {
        // Get height and width of node
        val width = (right - left).toLong()
        val height = (bottom - top).toLong()

        // Set up zoom coordinates offsets
        return ZoomCoordinates(
            leftOuter = Offset(left + width * 0.25f, top + height * 0.3f),
            leftInner = Offset(left + width * 0.45f, top + height * 0.3f),
            rightInner = Offset(left + width * 0.55f, height * 0.7f),
            rightOuter = Offset(left + width * 0.75f, height * 0.7f),
        )
    }

    @Test
    fun mapView_testImageZoomsIn() {
        composeTestRule.setContent {
            DualViewAppTheme {
                MapView(selectedIndex = 0, viewSize = viewSize)
            }
        }

        // Take screenshot of initial map image state
        val original = composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image)).captureToImage()
            .asAndroidBitmap()

        // Zoom in on map image map and take screenshot of new state
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.zoomable_map_image)).performGesture {
            zoomIn()
        }
        val afterZoom =
            composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image)).captureToImage()
                .asAndroidBitmap()

        // Make sure bitmaps are not the same anymore
        assert(!original.compare(afterZoom))
    }

    @Test
    fun mapView_testImageZoomsOut() {
        composeTestRule.setContent {
            DualViewAppTheme {
                MapView(selectedIndex = 0, viewSize = viewSize)
            }
        }

        // Take screenshot of initial map image state
        val original = composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image)).captureToImage()
            .asAndroidBitmap()

        // Zoom out on map image map and take screenshot of new state
        composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.zoomable_map_image)).performGesture {
            zoomOut()
        }
        val afterZoom =
            composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.map_image)).captureToImage()
                .asAndroidBitmap()

        // Make sure bitmaps are not the same anymore
        assert(!original.compare(afterZoom))
    }
}
