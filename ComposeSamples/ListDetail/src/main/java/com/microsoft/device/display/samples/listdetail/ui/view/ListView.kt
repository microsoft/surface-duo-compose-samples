/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.microsoft.device.display.samples.listdetail.R
import com.microsoft.device.display.samples.listdetail.models.AppStateViewModel
import com.microsoft.device.display.samples.listdetail.models.images

private val imagePadding = 10.dp
private val verticalPadding = 35.dp
private val horizontalPadding = 15.dp
private val imageMargin = 3.dp

@Composable
fun ListViewSpanned(appStateViewModel: AppStateViewModel) {
    ListView(
        navController = null,
        appStateViewModel = appStateViewModel
    )
}

@Composable
fun ListViewUnspanned(navController: NavController?, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicText(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            )
        },
        content = {
            ListView(navController = navController, appStateViewModel = appStateViewModel)
        }
    )
}

@Composable
fun ListView(navController: NavController?, appStateViewModel: AppStateViewModel) {
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val imageList = images
    val subImageList = imageList.chunked(3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = verticalPadding,
                bottom = verticalPadding,
                start = horizontalPadding,
                end = horizontalPadding
            )
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(imagePadding)
        ) {
            itemsIndexed(subImageList) { index, item ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(imagePadding)
                ) {
                    for ((imageIndex, image) in item.withIndex()) {
                        val listIndex = 3 * index + imageIndex
                        val outlineWidth = if (listIndex == selectedIndex) imageMargin else 0.dp
                        DecorativeBox(
                            modifier = Modifier
                                .wrapContentSize()
                                .weight(1f)
                                .padding(
                                    top = outlineWidth,
                                    bottom = outlineWidth,
                                    start = outlineWidth,
                                    end = outlineWidth
                                ),
                            listIndex, appStateViewModel
                        ) {
                            ImageView(
                                imageId = image,
                                modifier = Modifier
                                    .selectable(
                                        selected = (listIndex == selectedIndex),
                                        onClick = {
                                            appStateViewModel.imageSelectionLiveData.value = listIndex
                                            navController?.navigate("detail")
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DecorativeBox(
    modifier: Modifier = Modifier,
    listIndex: Int,
    appStateViewModel: AppStateViewModel,
    content: @Composable () -> Unit,
) {
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .border(
                width = 3.dp,
                color = if (listIndex == selectedIndex) colorResource(id = R.color.outline_blue) else Color.Transparent
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
