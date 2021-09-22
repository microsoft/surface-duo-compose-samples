package com.example.navigationrail.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.navigationrail.R
import com.example.navigationrail.models.Image

@Composable
fun ItemView(selectedImage: Image?) {
    selectedImage?.let {
        Image(
            painterResource(id = it.id),
            contentDescription = (it.description),
            modifier = Modifier.fillMaxSize()
        )
    } ?: run {
        Text(
            text = stringResource(R.string.select_image),
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}
