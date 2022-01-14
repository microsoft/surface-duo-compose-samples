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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.listdetail.R
import com.microsoft.device.display.samples.listdetail.models.images
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

private val imagePadding = 10.dp
private val verticalPadding = 35.dp
private val horizontalPadding = 15.dp

@Composable
fun ListViewWithTopBar(isDualScreen: Boolean, selectedIndex: Int, updateSelectedIndex: (Int) -> Unit) {
    Scaffold(
        topBar = { ListViewTopBar() },
        content = { ListView(isDualScreen, selectedIndex, updateSelectedIndex) }
    )
}

@Composable
fun ListViewTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    )
}

@Composable
fun ListView(isDualScreen: Boolean, selectedIndex: Int, updateSelectedIndex: (Int) -> Unit) {
    val subImageList = images.chunked(3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = verticalPadding,
                bottom = verticalPadding,
                start = horizontalPadding,
                end = horizontalPadding
            )
            .testTag(stringResource(R.string.list_view))
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
                        val isSelected = (listIndex == selectedIndex)
                        DecorativeBox(
                            modifier = Modifier
                                .wrapContentSize()
                                .weight(1f),
                            isSelected
                        ) {
                            ImageView(
                                imageId = image,
                                contentDescription = listIndex.toString(),
                                modifier = Modifier
                                    .selectable(
                                        selected = (listIndex == selectedIndex),
                                        onClick = {
                                            updateSelectedIndex(listIndex)
                                            if (!isDualScreen) {
                                                navigateToPane2()
                                            }
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
    isSelected: Boolean,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .border(
                width = 5.dp,
                color = if (isSelected) MaterialTheme.colors.primaryVariant else Color.Transparent
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
