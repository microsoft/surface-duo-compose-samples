/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.ui.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.microsoft.device.display.samples.twopage.R
import com.microsoft.device.display.samples.twopage.utils.PageLayout

@Composable
fun ThirdPage(modifier: Modifier) {
    PageLayout(
        modifier = modifier.testTag(stringResource(R.string.page3_tag)),
        pageNumber = stringResource(R.string.page_number_3)
    ) {
        ThirdPageContent()
    }
}

@Composable
fun ThirdPageContent() {
    Text(
        modifier = Modifier.semantics { heading() },
        text = stringResource(R.string.page3_title1),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6
    )
    Text(
        text = stringResource(R.string.page3_text1),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1
    )
}
