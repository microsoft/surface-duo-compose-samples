/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.twopage.R
import com.microsoft.device.display.samples.twopage.utils.AlignedCaption
import com.microsoft.device.display.samples.twopage.utils.PageLayout

@Composable
fun FirstPage(modifier: Modifier) {
    PageLayout(
        modifier = modifier,
        pageNumber = stringResource(R.string.page_number_1)
    ) {
        FirstPageContent()
    }
}

@Composable
fun FirstPageContent() {
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(R.string.article_title),
        color = MaterialTheme.colors.onBackground,
        style = typography.h5
    )
    Spacer(modifier = Modifier.requiredHeight(5.dp))
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
        text = stringResource(R.string.page1_image_caption),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.requiredHeight(5.dp))
    Text(
        text = stringResource(R.string.page1_text),
        color = MaterialTheme.colors.onBackground,
        style = typography.body1
    )
    Spacer(modifier = Modifier.height(10.dp))
}
