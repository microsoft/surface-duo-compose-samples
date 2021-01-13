/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.composesample

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.composesample.models.DataProvider
import com.microsoft.device.display.samples.composesample.models.ImageModel
import com.microsoft.device.display.samples.composesample.viewModels.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel
private val DEBUG_TAG = "ComposeSample"

@Preview
@Composable
fun HomePreview() {
    SetupUI()
}

@Composable
fun Home(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel
    SetupUI()
}

@Composable
fun SetupUI() {
    val models = DataProvider.imageModels
    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

    Log.i(DEBUG_TAG, "SetupUI isScreenSpanned: $isScreenSpanned")

    if (isScreenSpanned) {
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
    val imageSelectionLiveData = appStateViewModel.getImageSelectionLiveData()
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(models) { index, item ->
            Row(
                modifier = Modifier.selectable(
                    selected = (index == selectedIndex),
                    onClick = {
                        appStateViewModel.setImageSelectionLiveData(index)
                    }
                ) then Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val image = loadImageResource(id = item.image)
                image.resource.resource?.let {
                    Image(
                        bitmap = it,
                        modifier = Modifier.preferredHeight(100.dp).preferredWidth(150.dp)
                    )
                }
                Spacer(Modifier.preferredWidth(16.dp))
                Column(modifier = Modifier.fillMaxHeight() then Modifier.padding(16.dp)) {
                    BasicText(
                        text = item.id,
                        modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    BasicText(
                        text = item.title,
                        modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center)
                    )
                }
            }
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
fun ShowDetailWithList(models: List<ImageModel>) {
    val imageSelectionLiveData = appStateViewModel.getImageSelectionLiveData()
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val selectedImageModel = models[selectedIndex]

    Row(
        modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center)
            then Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
    ) {
        ShowListColumn(
            models, Modifier.fillMaxHeight().wrapContentSize(Alignment.Center).weight(1f)
        )
        Column(
            modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center).weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {
            BasicText(
                text = selectedImageModel.id,
                style = TextStyle(fontSize = 50.sp)
            )
            val image = loadImageResource(id = selectedImageModel.image)
            image.resource.resource?.let {
                Image(bitmap = it)
            }
        }
    }
}
