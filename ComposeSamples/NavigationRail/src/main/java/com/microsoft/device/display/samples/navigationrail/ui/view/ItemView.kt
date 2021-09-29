/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.Image

@Composable
fun ItemView(selectedImage: Image?) {
    selectedImage?.let { image ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        ) {
            Image(
                painterResource(id = image.image),
                contentDescription = (image.description),
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .border(5.dp, Color.Magenta),
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)
            ) {
                this.item {
                    Text(
                        text = stringResource(id = R.string.date_found, image.date),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Visible,
                    )
                }
                this.item {
                    Text(
                        text = stringResource(id = R.string.observation_details, image.details),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxSize(),
                        overflow = TextOverflow.Visible,
                    )
                }
            }
        }
    }
        ?: run {
            Text(
                text = stringResource(R.string.select_image),
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
            )
        }
}
