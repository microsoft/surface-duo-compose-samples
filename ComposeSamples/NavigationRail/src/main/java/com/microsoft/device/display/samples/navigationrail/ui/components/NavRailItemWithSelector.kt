/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalMaterialApi
@Composable
fun ColumnScope.NavRailItemWithSelector(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    selectedContentColor: Color,
    unselectedContentColor: Color,
) {
    Box(modifier = Modifier.weight(1f, fill = false), contentAlignment = Alignment.Center) {
        if (selected) {
            Selector()
        }
        Column {
            NavigationRailItem(
                icon = icon,
                label = label,
                selected = selected,
                onClick = onClick,
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor
            )
        }
    }
}
