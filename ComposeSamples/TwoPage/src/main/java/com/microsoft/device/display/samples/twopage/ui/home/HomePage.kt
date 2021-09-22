/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.twopage.utils.PagerState
import com.microsoft.device.display.samples.twopage.utils.ViewPager
import kotlinx.coroutines.flow.collect

const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun SetupUI(windowInfoRep: WindowInfoRepository) {
    val density = LocalDensity.current.density
    var isAppSpanned by remember { mutableStateOf(false) }
    var viewWidth by remember { mutableStateOf(0) }
    var hingeThickness by remember { mutableStateOf(0) }
    var isHingeHorizontal by remember { mutableStateOf(false) }

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                val displayFeatures = newLayoutInfo.displayFeatures
                isAppSpanned = displayFeatures.isNotEmpty()
                if (isAppSpanned) {
                    val foldingFeature = displayFeatures.first() as FoldingFeature
                    val vWidth: Int

                    if (foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL) {
                        isHingeHorizontal = false
                        vWidth = foldingFeature.bounds.left
                        hingeThickness = foldingFeature.bounds.width()
                    } else {
                        isHingeHorizontal = true
                        vWidth = foldingFeature.bounds.right
                        hingeThickness = foldingFeature.bounds.height()
                    }

                    viewWidth = (vWidth / density).toInt()
                }
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTabletDualMode = isTablet && isLandscape
    if (isTabletDualMode) {
        viewWidth = LocalConfiguration.current.screenWidthDp / 2
    }

    val isDualScreen = (isAppSpanned || isTabletDualMode) && !isHingeHorizontal
    val pages = setupPages(viewWidth)
    PageViews(pages, isDualScreen, hingeThickness / 2)
}

@Composable
fun PageViews(pages: List<@Composable () -> Unit>, isDualScreen: Boolean, pagePadding: Int) {
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: PagerState = remember { PagerState(currentPage = 0, minPage = 0, maxPage = maxPage) }
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
        {
            FirstPage(modifier = modifier)
        },
        {
            SecondPage(modifier = modifier)
        },
        {
            ThirdPage(modifier = modifier)
        },
        {
            FourthPage(modifier = modifier)
        }
    )
}
