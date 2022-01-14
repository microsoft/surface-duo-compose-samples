package com.microsoft.device.display.samples.listdetail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ImageView(imageId: Int, contentDescription: String, modifier: Modifier) {
    Image(
        painter = painterResource(imageId),
        contentDescription = contentDescription,
        modifier = modifier
    )
}
