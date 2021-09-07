/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

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
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowInfoRepository
import com.microsoft.device.display.samples.composegallery.models.AppStateViewModel
import com.microsoft.device.display.samples.composegallery.models.DataProvider
import com.microsoft.device.display.samples.composegallery.models.ImageModel
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
    SetupUI(isDualMode)
}

@Composable
fun SetupUI(isDualMode: Boolean) {
    val models = DataProvider.imageModels

    if (isDualMode) {
        ShowDetailWithList(models)
    } else {
        ShowList(models)
    }
}

@Composable
private fun ShowList(models: List<ImageModel>) {
    ShowListColumn(models, Modifier.fillMaxHeight() then Modifier.fillMaxWidth())
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
fun ShowDetailWithList(models: List<ImageModel>) {
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val selectedImageModel = models[selectedIndex]

    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        ShowListColumn(
            models,
            Modifier
                .fillMaxHeight()
                .wrapContentSize(Alignment.Center)
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentSize(Alignment.Center)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {
            Crossfade(
                targetState = selectedImageModel,
                animationSpec = tween(600)
            ) {
                Column {
                    BasicText(
                        text = it.id,
                        style = TextStyle(
                            fontSize = 50.sp,
                            color = MaterialTheme.colors.onSurface,
                        )
                    )
                    Image(
                        painter = painterResource(id = it.image),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
