/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.ui.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.listdetail.R
import com.microsoft.device.display.samples.listdetail.models.AppStateViewModel
import com.microsoft.device.display.samples.listdetail.models.images
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1

private val imageSize = 25.dp
private val verticalPadding = 35.dp
private val horizontalPadding = 20.dp

@Composable
fun DetailViewWithTopBar(isDualScreen: Boolean, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    if (!isDualScreen) {
                        DetailViewTopBar()
                    }
                }
            )
        },
        content = {
            DetailView(appStateViewModel = appStateViewModel)
        }
    )
}

@Composable
fun DetailViewTopBar() {
    IconButton(
        onClick = {
            navigateToPane1()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
fun DetailView(appStateViewModel: AppStateViewModel) {
    val imageSelectionLiveData = appStateViewModel.imageSelectionLiveData
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val selectedImageId = images[selectedIndex]

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(.85f)
                .padding(
                    top = verticalPadding,
                    start = horizontalPadding,
                    end = horizontalPadding
                )
        ) {
            ImageView(
                imageId = selectedImageId,
                modifier = Modifier.fillMaxSize()
            )
        }
        ImageInfoTile(
            modifier = Modifier
                .fillMaxSize()
                .weight(.15f)
                .padding(start = 25.dp)
        )
    }
}

@Composable
fun ImageInfoTile(modifier: Modifier) {
    Row(
        modifier = modifier.horizontalScroll(
            rememberScrollState()
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            ImageView(
                imageId = R.drawable.info_icon,
                modifier = Modifier
                    .height(15.dp)
                    .width(15.dp)
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        CameraInfoTile()
        DeviceInfoTile()
        Spacer(modifier = Modifier.width(5.dp))
    }
}

@Composable
fun CameraInfoTile() {
    ImageView(
        imageId = R.drawable.image_icon,
        modifier = Modifier
            .width(imageSize)
            .height(imageSize)
    )
    Spacer(modifier = Modifier.width(20.dp))
    Column(modifier = Modifier.wrapContentWidth()) {
        Text(
            text = stringResource(R.string.camera),
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.camera_info),
            style = TextStyle(
                fontSize = 12.sp,
                color = colorResource(id = R.color.light_gary)
            )
        )
    }
}

@Composable
fun DeviceInfoTile() {
    Spacer(modifier = Modifier.width(40.dp))
    ImageView(
        imageId = R.drawable.camera_icon,
        modifier = Modifier
            .width(imageSize)
            .height(imageSize)
    )
    Spacer(modifier = Modifier.width(20.dp))
    Column(modifier = Modifier.wrapContentWidth()) {
        Text(
            text = stringResource(R.string.device),
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.device_info),
            style = TextStyle(
                fontSize = 12.sp,
                color = colorResource(id = R.color.light_gary)
            )
        )
    }
}
