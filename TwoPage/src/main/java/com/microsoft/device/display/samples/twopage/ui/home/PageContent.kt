/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.R

private val HorizontalPadding = 30.dp

@Composable
fun FirstPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = HorizontalPadding,
                end = HorizontalPadding
            ),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.article_title),
            style = typography.h5
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(id = R.drawable.two_page_rome_image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().padding(start = HorizontalPadding, end = HorizontalPadding)
        )
        Text(
            text = stringResource(R.string.two_page_image_caption),
            style = typography.caption
        )
        Text(
            text = stringResource(R.string.two_page_page1_text),
            style = typography.body1
        )
        Text(
            text = stringResource(R.string.two_page_page1_page_number),
            style = typography.caption
        )
    }
}