/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout

import android.graphics.Rect
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microsoft.device.display.twopanelayout.screenState.DeviceType
import com.microsoft.device.display.twopanelayout.screenState.LayoutOrientation
import com.microsoft.device.display.twopanelayout.screenState.LayoutState
import com.microsoft.device.display.twopanelayout.screenState.ScreenState
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@RunWith(AndroidJUnit4::class)
class TwoPaneTestTest: LayoutTest() {
    lateinit var screenPaddingBounds: Rect
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

        val drawLatch = CountDownLatch(0)
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

    }

    @Test
    fun testTwoPane_withTwoChildrenWithSameWeight() {

    }

    @Test
    fun testTwoPane_withTwoChildrenWithDifferentWeight() {

    }
}