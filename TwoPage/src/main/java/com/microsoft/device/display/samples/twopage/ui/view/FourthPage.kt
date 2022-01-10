/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.microsoft.device.display.samples.twopage.R
import com.microsoft.device.display.samples.twopage.utils.PageLayout

@Composable
fun FourthPage(modifier: Modifier) {
    PageLayout(
        modifier = modifier,
        pageNumber = stringResource(R.string.page_number_4)
    ) {
        FourthPageContent()
    }
}

@Composable
fun FourthPageContent() {
    Text(
        text = stringResource(R.string.page4_title1),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6
    )
    Text(
        text = stringResource(R.string.page4_text1),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body2
    )
    Text(
        text = stringResource(R.string.page4_title2),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6
    )
    Text(
        text = stringResource(R.string.page4_text2),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body2
    )
}
