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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

enum class Effects(@StringRes val title: Int, @DrawableRes val image: Int) {
    FILTER(R.string.filter, R.drawable.filter_icon),
    HDR(R.string.hdr, R.drawable.hdr_icon),
    ELLIPSE(R.string.ellipse, R.drawable.ecllipse_icon),
    HORIZONTAL(R.string.vertical, R.drawable.vertical_icon),
    VERTICAL(R.string.horizontal, R.drawable.horizontal_icon),
    ZOOM(R.string.zoom, R.drawable.zoom_icon),
    BRIGHTNESS(R.string.brightness, R.drawable.brightness_icon)
}

private val filterList = Filters.values()

private val fullIconList = Effects.values().toList()

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
            contentDescription = stringResource(R.string.scale)
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
                painter = painterResource(id = icon.image),
                contentDescription = stringResource(icon.title),
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
                painter = painterResource(id = icon.image),
                contentDescription = stringResource(icon.title),
            )
        }
    }
}
