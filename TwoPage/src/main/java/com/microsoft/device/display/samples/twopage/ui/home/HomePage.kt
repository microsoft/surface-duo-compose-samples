/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.models.AppStateViewModel
import com.microsoft.device.display.samples.twopage.utils.DualPageContainer
import com.microsoft.device.display.samples.twopage.utils.PagerState
import com.microsoft.device.display.samples.twopage.utils.ViewPager

private lateinit var appStateViewModel: AppStateViewModel
val HorizontalPadding = 30.dp

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

    val isScreenPortraitLiveData = appStateViewModel.getIsScreenPortraitLiveData()
    val isScreenPortrait = isScreenPortraitLiveData.observeAsState(initial = true).value

    if (isScreenSpanned && !isScreenPortrait) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }
}

@Composable
fun SingleScreenUI() {
    PageViews(false)
}

@Composable
fun DualScreenUI() {
    PageViews(true)
}

@Composable
fun PageViews(isDual: Boolean) {
    val pages = if (isDual) DualPages else SinglePages
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: PagerState = remember { PagerState(currentPage = 0, minPage = 0, maxPage = maxPage) }
    ViewPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {
        pages[page]()
    }
}

val SinglePages: List<@Composable () -> Unit> = listOf(
    {
        FirstPage(modifier = Modifier.fillMaxSize())
    },
    {
        SecondPage(modifier = Modifier.fillMaxSize())
    },
    {
        ThirdPage(modifier = Modifier.fillMaxSize())
    },
    {
        FourthPage(modifier = Modifier.fillMaxSize())
    }
)

val DualPages: List<@Composable () -> Unit> = listOf(
    {
        DualPageContainer(
            left = SinglePages[0],
            right = SinglePages[1]
        )
    },
    {
        DualPageContainer(
            left = SinglePages[2],
            right = SinglePages[3]
        )
    }
)
