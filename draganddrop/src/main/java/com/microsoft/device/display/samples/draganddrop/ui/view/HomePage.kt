/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  
 */

package com.microsoft.device.display.samples.draganddrop.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

@Composable
fun DragAndDropApp() {
    TwoPaneLayout(
        pane1 = { DragPane() }, 
        pane2 = { DropPane() }
    )
}

@Preview
@Composable
fun DragAndDropAppPreview() {
    DragPane()
}



