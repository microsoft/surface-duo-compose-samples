/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  
 */

package com.microsoft.device.display.samples.draganddrop.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.draganddrop.utils.DragContainer
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

@Composable
fun DragAndDropApp() {
    DragContainer(modifier = Modifier.fillMaxSize()) {
        TwoPaneLayout(
            pane1 = { DragPane() },
            pane2 = { DropPane() }
        )
    }
}
