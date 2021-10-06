/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RowScope.BottomNavItemWithSelector(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    selectedContentColor: Color,
    unselectedContentColor: Color,
) {
    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
        if (selected) {
            Selector()
        }
        Row {
            BottomNavigationItem(
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
