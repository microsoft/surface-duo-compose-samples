/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.companionpane.R

private val imageWidth = 80.dp
private val controlWidth = 100.dp

enum class Filters(@StringRes val title: Int, @DrawableRes val image: Int) {
    GINGHAM(R.string.gingham, R.drawable.gingham),
    ORIGINAL(R.string.original, R.drawable.original),
    LARK(R.string.lark, R.drawable.lark),
    JUNO(R.string.juno, R.drawable.juno),
    LUDWIG(R.string.ludwig, R.drawable.ludwig)
}

private val filterList = Filters.values()

private val fullIconList = listOf<@DrawableRes Int>(
    R.drawable.filter_icon,
    R.drawable.hdr_icon,
    R.drawable.ecllipse_icon,
    R.drawable.vertical_icon,
    R.drawable.horizontal_icon,
    R.drawable.zoom_icon,
    R.drawable.brightness_icon
)

private val shortIconList = fullIconList.subList(2, 5)

@Composable
fun FilterPanel() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
    ) {
        filterList.forEachIndexed { index, filter ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (index == 0) {
                    // Show filters label over first filter option
                    Text(
                        text = stringResource(R.string.filters),
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
                ImageWithText(filter.image, stringResource(filter.title), imageWidth, controlWidth)
            }
        }
    }
}


@Composable
fun ShortFilterControl() {
    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ShortIconsPanel()
        }
        AdjustScale()
    }
}

@Composable
fun FullFilterControl() {
    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        FullIconsPanel()
        AdjustScale()
    }
}

@Composable
fun AdjustScale() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.dot),
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            contentDescription = null
        )
        Image(
            painter = painterResource(R.drawable.scale_icon),
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            contentDescription = null
        )
    }
}

@Composable
fun FullIconsPanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        fullIconList.forEach { icon ->
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun ShortIconsPanel() {
    Row(
        modifier = Modifier
            .width(200.dp)
            .height(25.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        shortIconList.forEach { icon ->
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        }
    }
}
