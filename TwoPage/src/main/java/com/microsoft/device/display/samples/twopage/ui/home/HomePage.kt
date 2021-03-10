/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.models.AppStateViewModel
import com.microsoft.device.display.samples.twopage.utils.PagerState
import com.microsoft.device.display.samples.twopage.utils.ViewPager

private lateinit var pageModifier: Modifier

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    var appStateViewModel = viewModel

    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

    val isScreenPortraitLiveData = appStateViewModel.getIsScreenPortraitLiveData()
    val isScreenPortrait = isScreenPortraitLiveData.observeAsState(initial = true).value

    val width = appStateViewModel.screenWidth
    pageModifier = if (width > 0) Modifier.width(width.dp).fillMaxHeight().clipToBounds() else Modifier.fillMaxSize()

    val hingeWidth = appStateViewModel.hingeWidth

    PageViews(isScreenSpanned, hingeWidth)
}

@Composable
fun PageViews(isScreenSpanned: Boolean, pagePadding: Int) {
    val pages = Pages
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: PagerState = remember { PagerState(currentPage = 0, minPage = 0, maxPage = maxPage) }
    pagerState.isDualMode = isScreenSpanned
    ViewPager(
        state = pagerState,
        pagePadding = pagePadding,
        modifier = Modifier.fillMaxSize()
    ) {
        pages[page]()
    }
}

private val Pages: List<@Composable () -> Unit> = listOf(
    {
        FirstPage(modifier = pageModifier)
    },
    {
        SecondPage(modifier = pageModifier)
    },
    {
        ThirdPage(modifier = pageModifier)
    },
    {
        FourthPage(modifier = pageModifier)
    }
)
