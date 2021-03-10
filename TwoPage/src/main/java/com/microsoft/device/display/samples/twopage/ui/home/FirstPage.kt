/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.R
import com.microsoft.device.display.samples.twopage.utils.AlignedCaption
import com.microsoft.device.display.samples.twopage.utils.PageLayout

@Composable
fun FirstPage(modifier: Modifier) {
    PageLayout(modifier) {
        FirstPageContent()
    }
}

@Composable
fun FirstPageContent() {
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(R.string.article_title),
        style = typography.h5
    )
    Spacer(modifier = Modifier.height(3.dp))
    Image(
        painter = painterResource(id = R.drawable.two_page_rome_image),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp
            )
    )
    AlignedCaption(
        text = stringResource(R.string.two_page_image_caption),
        arrangement = Arrangement.Center
    )
    Text(
        text = stringResource(R.string.two_page_page1_text),
        style = typography.body1
    )
    AlignedCaption(
        text = stringResource(R.string.two_page_page1_page_number),
        arrangement = Arrangement.End
    )
}
