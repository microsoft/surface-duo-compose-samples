/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.models.ImageModel
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2
import kotlinx.coroutines.flow.collect

private lateinit var appStateViewModel: AppStateViewModel
const val SMALLEST_TABLET_SCREEN_WIDTH_DP = 585

@Composable
fun Home(viewModel: AppStateViewModel, windowInfoRep: WindowInfoRepository) {
    appStateViewModel = viewModel
    var isAppSpanned by remember { mutableStateOf(false) }

    LaunchedEffect(windowInfoRep) {
        windowInfoRep.windowLayoutInfo
            .collect { newLayoutInfo ->
                isAppSpanned = newLayoutInfo.displayFeatures.isNotEmpty()
            }
    }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp > SMALLEST_TABLET_SCREEN_WIDTH_DP
    val isDualMode = isAppSpanned || isTablet
    val isRotated = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    SetupUI(isDualMode, isRotated)
}

@Composable
fun SetupUI(isDualMode: Boolean, isRotated: Boolean) {
    val models = DataProvider.imageModels
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value

    TwoPaneLayout(
        paneMode = TwoPaneMode.TwoPane,
        pane1 = { ShowList(models, isDualMode) },
        pane2 = { ShowDetail(models, isDualMode, selectedIndex, isRotated) }
    )
}

@Composable
private fun ShowWithTopBar(content: @Composable () -> Unit, actions: @Composable () -> Unit, title: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicText(
                        text = title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 10.dp,
                actions = { actions() }
            )
        },
        content = { content() }
    )
}

@Composable
private fun ShowList(models: List<ImageModel>, isDualMode: Boolean) {
    ShowWithTopBar(
        content = { ShowListColumn(models = models, modifier = Modifier.fillMaxSize()) },
        actions = { if (!isDualMode) ListActions() },
        title = stringResource(R.string.app_name),
    )
}

@Composable
private fun ShowDetail(models: List<ImageModel>, isDualMode: Boolean, selectedIndex: Int, isRotated: Boolean) {
    if (isDualMode && isRotated) {
        // Don't show app bar when spanned/rotated
        ShowDetailImage(models = models, selectedIndex = selectedIndex)
    } else {
        ShowWithTopBar(
            content = { ShowDetailImage(models = models, selectedIndex = selectedIndex) },
            actions = { if (!isDualMode) DetailActions() },
            title = if (!isDualMode) stringResource(R.string.app_name) else "",
        )
    }
}

@Composable
private fun ListActions() {
    IconButton(onClick = { navigateToPane2() }) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_photo_24),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Switch to picture detail viewing mode",
        )
    }
}

@Composable
private fun DetailActions() {
    IconButton(onClick = { navigateToPane1() }) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_view_list_24),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Switch to list viewing mode",
        )
    }
}

@Composable
private fun ShowListColumn(models: List<ImageModel>, modifier: Modifier) {
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(models) { index, item ->
            Row(
                modifier = Modifier.selectable(
                    selected = (index == selectedIndex),
                    onClick = {
                        appStateViewModel.imageSelectionLiveData.value = index
                    }
                ) then Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.image),
                    modifier = Modifier
                        .height(100.dp)
                        .width(150.dp),
                    contentDescription = null
                )
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.fillMaxHeight() then Modifier.padding(16.dp)) {
                    BasicText(
                        text = item.id,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentSize(Alignment.Center),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onSurface,
                        )
                    )
                    BasicText(
                        text = item.title,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentSize(Alignment.Center),
                        style = TextStyle(
                            color = MaterialTheme.colors.onSurface,
                        )
                    )
                }
            }

            Divider(color = MaterialTheme.colors.onSurface)
        }
    }
}

@Composable
fun ShowDetailImage(models: List<ImageModel>, selectedIndex: Int) {
    val selectedImageModel = models[selectedIndex]

    Crossfade(
        targetState = selectedImageModel,
        animationSpec = tween(600)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            BasicText(
                text = it.id,
                style = TextStyle(
                    fontSize = 50.sp,
                    color = MaterialTheme.colors.onSurface,
                )
            )
            Image(
                painter = painterResource(id = it.image),
                contentDescription = it.subtitle,
            )
        }
    }
}
