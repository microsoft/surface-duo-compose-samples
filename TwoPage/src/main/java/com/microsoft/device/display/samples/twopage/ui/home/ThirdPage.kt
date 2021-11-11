/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.microsoft.device.display.samples.twopage.R
import com.microsoft.device.display.samples.twopage.utils.AlignedCaption
import com.microsoft.device.display.samples.twopage.utils.PageLayout

@Composable
fun ThirdPage(modifier: Modifier) {
    PageLayout(modifier = modifier) {
        ThirdPageContent()
    }
}

@Composable
fun ThirdPageContent() {
    Text(
        text = stringResource(R.string.two_page_page3_title1),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6
    )
    Text(
        text = stringResource(R.string.two_page_page3_text1),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1
    )
    AlignedCaption(
        text = stringResource(R.string.two_page_page3_page_number),
        arrangement = Arrangement.End
    )
}
