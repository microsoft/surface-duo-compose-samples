/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.utils.PagerState
import com.microsoft.device.display.samples.twopage.utils.ViewPager
import com.microsoft.device.dualscreen.windowstate.WindowState

@Composable
fun TwoPageApp(windowState: WindowState) {
    val density = LocalDensity.current.density
    var viewWidth by remember { mutableStateOf(0) }

    viewWidth = when (windowState.isDualPortrait() && !windowState.hasFold) {
        true -> LocalConfiguration.current.screenWidthDp / 2
        else -> (windowState.foldablePaneWidth / density).toInt()
    }

    val isDualScreen = windowState.isDualPortrait()
    val pages = setupPages(viewWidth)
    PageViews(pages, isDualScreen, windowState.foldSize / 2)
}

@Composable
fun PageViews(pages: List<@Composable () -> Unit>, isDualScreen: Boolean, pagePadding: Int) {
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: PagerState =
        remember { PagerState(currentPage = 0, minPage = 0, maxPage = maxPage) }
    pagerState.isDualMode = isDualScreen
    ViewPager(
        state = pagerState,
        pagePadding = pagePadding,
        modifier = Modifier.fillMaxSize()
    ) {
        pages[page]()
    }
}

fun setupPages(width: Int): List<@Composable () -> Unit> {
    val modifier = if (width != 0) Modifier
        .width(width.dp)
        .fillMaxHeight()
        .clipToBounds() else Modifier.fillMaxSize()
    return listOf<@Composable () -> Unit>(
        { FirstPage(modifier = modifier) },
        { SecondPage(modifier = modifier) },
        { ThirdPage(modifier = modifier) },
        { FourthPage(modifier = modifier) }
    )
}
