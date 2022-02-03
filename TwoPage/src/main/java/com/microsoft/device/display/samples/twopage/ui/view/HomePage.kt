/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.microsoft.device.display.samples.twopage.utils.PagerState
import com.microsoft.device.display.samples.twopage.utils.ViewPager
import com.microsoft.device.dualscreen.windowstate.WindowState
import kotlin.math.abs

@Composable
fun TwoPageApp(windowState: WindowState) {
    TwoPageAppContent(
        pane1WidthDp = windowState.pane1SizeDp().width.dp,
        pane2WidthDp = windowState.pane2SizeDp().width.dp,
        isDualScreen = windowState.isDualPortrait(),
        foldSizeDp = windowState.foldSizeDp
    )
}

@Composable
fun TwoPageAppContent(pane1WidthDp: Dp, pane2WidthDp: Dp, isDualScreen: Boolean, foldSizeDp: Dp) {
    // Calculate page text width based on the smallest pane width
    val pageTextWidth = min(pane1WidthDp, pane2WidthDp)

    // Calculate the necessary page padding, based on pane width differences and fold width
    val pagePadding = abs(pane1WidthDp.value - pane2WidthDp.value).dp + foldSizeDp

    val pages = setupPages(pageTextWidth, pagePadding)
    PageViews(pages, isDualScreen)
}

@Composable
fun PageViews(pages: List<@Composable () -> Unit>, isDualScreen: Boolean) {
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: PagerState =
        remember { PagerState(currentPage = 0, minPage = 0, maxPage = maxPage) }
    pagerState.isDualMode = isDualScreen
    ViewPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {
        pages[page]()
    }
}

private fun setupPages(pageTextWidth: Dp, pagePadding: Dp = 0.dp): List<@Composable () -> Unit> {
    val modifier = if (pageTextWidth.value != 0f) Modifier
        .padding(end = pagePadding)
        .width(pageTextWidth)
        .fillMaxHeight()
        .clipToBounds() else Modifier.fillMaxSize()
    return listOf<@Composable () -> Unit>(
        { FirstPage(modifier = modifier) },
        { SecondPage(modifier = modifier) },
        { ThirdPage(modifier = modifier) },
        { FourthPage(modifier = modifier) }
    )
}
