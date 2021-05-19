/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microsoft.device.display.twopanelayout.screenState.DeviceType
import com.microsoft.device.display.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.LayoutState
import com.microsoft.device.display.twopanelayout.screenState.ScreenState
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@RunWith(AndroidJUnit4::class)
class TwoPaneTestTest: LayoutTest() {
    lateinit var screenState: ScreenState

    @Before
    fun before() {
        isDebugInspectorInfoEnabled = true
    }

    @After
    fun after() {
        isDebugInspectorInfoEnabled = false
    }

    @Test
    fun testTwoPane_withChild() {
        val hingeBounds = Rect()
        screenState = ScreenState(
            deviceType = DeviceType.Single,
            screenSize = Size(400f, 600f),
            hingeBounds = hingeBounds,
            orientation = LayoutOrientation.Vertical,
            layoutState = LayoutState.Fold
        )
        val paddingBounds = Rect()

        val drawLatch = CountDownLatch(1)
        val childSize = arrayOfNulls<IntSize>(1)
        val childPosition = arrayOfNulls<Offset>(1)
        activityTestRule.setContent {
            MockTwoPaneLayout(
                screenState = screenState,
                paddingBounds = paddingBounds) {
                Container(
                    Modifier
                        .onGloballyPositioned { coordinates ->
                            childSize[0] = coordinates.size
                            childPosition[0] = coordinates.positionInRoot()
                            drawLatch.countDown()
                        }
                ) {
                }
            }
        }
        assertTrue(drawLatch.await(1, TimeUnit.SECONDS))

        assertEquals(IntSize(width = screenState.screenSize.width.roundToInt(), height = screenState.screenSize.height.roundToInt()), childSize[0])
        assertEquals(Offset(0f, 0f), childPosition[0])
    }

    @Test
    fun testTwoPane_withTwoChildrenWithoutWeight() {
        val hingeBounds = Rect(390, 0, 410, 600)
        screenState = ScreenState(
            deviceType = DeviceType.Multiple,
            screenSize = Size(800f, 600f),
            hingeBounds = hingeBounds,
            orientation = LayoutOrientation.Vertical,
            layoutState = LayoutState.Open
        )
        val paddingBounds = Rect(0, 0, 0, 0)

        val drawLatch = CountDownLatch(2)
        val childSize = arrayOfNulls<IntSize>(2)
        val childPosition = arrayOfNulls<Offset>(2)
        activityTestRule.setContent {
            MockTwoPaneLayout(
                screenState = screenState,
                paddingBounds = paddingBounds) {
                Container(
                    Modifier
                        .onGloballyPositioned { coordinates ->
                            childSize[0] = coordinates.size
                            childPosition[0] = coordinates.positionInRoot()
                            drawLatch.countDown()
                        }
                ) {
                }
                Container(
                    Modifier
                        .onGloballyPositioned { coordinates ->
                            childSize[1] = coordinates.size
                            childPosition[1] = coordinates.positionInWindow()
                            drawLatch.countDown()
                        }
                ) {
                }
            }
        }
        assertTrue(drawLatch.await(1, TimeUnit.SECONDS))

        assertEquals(IntSize(width = hingeBounds.left, height = hingeBounds.height()), childSize[0])
        assertEquals(IntSize(width = hingeBounds.left, height = hingeBounds.height()), childSize[1])
        assertEquals(Offset(0f, 0f), childPosition[0])
        assertEquals(Offset(hingeBounds.right.toFloat(), 0f), childPosition[1])
    }

    @Test
    fun testTwoPane_onTabletWithTwoChildrenWithWeight() {
        val hingeBounds = Rect()
        screenState = ScreenState(
            deviceType = DeviceType.Multiple,
            screenSize = Size(800f, 1200f),
            hingeBounds = hingeBounds,
            orientation = LayoutOrientation.Horizontal,
            layoutState = LayoutState.Open
        )
        val paddingBounds = Rect()

        val drawLatch = CountDownLatch(2)
        val childSize = arrayOfNulls<IntSize>(2)
        val childPosition = arrayOfNulls<Offset>(2)
        activityTestRule.setContent {
            MockTwoPaneLayout(
                screenState = screenState,
                paddingBounds = paddingBounds) {
                Container(
                    Modifier
                        .weight(.4f)
                        .onGloballyPositioned { coordinates ->
                            childSize[0] = coordinates.size
                            childPosition[0] = coordinates.positionInRoot()
                            drawLatch.countDown()
                        }
                ) {
                }
                Container(
                    Modifier
                        .weight(.6f)
                        .onGloballyPositioned { coordinates ->
                            childSize[1] = coordinates.size
                            childPosition[1] = coordinates.positionInRoot()
                            drawLatch.countDown()
                        }
                ) {
                }
            }
        }
        assertTrue(drawLatch.await(1, TimeUnit.SECONDS))

        val screenWidth = screenState.screenSize.width
        val screenHeight = screenState.screenSize.height
        assertEquals(IntSize(width = screenWidth.roundToInt(), height = (screenHeight * .4f).roundToInt()), childSize[0])
        assertEquals(IntSize(width = screenWidth.roundToInt(), height = (screenHeight * .6f).roundToInt()), childSize[1])
        assertEquals(Offset(0f, 0f), childPosition[0])
        assertEquals(Offset(0f, screenHeight * .4f), childPosition[1])
    }
}