package com.microsoft.device.display.samples.extendedcanvas

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberZoomableController
import androidx.compose.foundation.gestures.zoomable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.extendedcanvas.viewmodel.AppStateViewModel
import kotlin.math.roundToInt

@Composable
fun MainPage(viewModel: AppStateViewModel) {
    val isScreenPortraitLiveData = viewModel.getIsScreenPortraitLiveData()
    val isScreenPortrait = isScreenPortraitLiveData.observeAsState(initial = true).value
    ScaleImage(isScreenPortrait)
}

@Composable
fun ScaleImage(isPortrait: Boolean) {
    val max = 400.dp
    val min = -400.dp
    val offsetXPosition = remember { mutableStateOf(0f) }
    val offsetYPosition = remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(2f) }
    val zoomableController = rememberZoomableController { scale *= it }
    val scaleValue: ContentScale = if (isPortrait) ContentScale.FillHeight else ContentScale.FillWidth

    Image(
        bitmap = imageResource(R.drawable.mock_map),
        contentScale = scaleValue,
        alignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(offsetXPosition.value.roundToInt(), offsetYPosition.value.roundToInt()) }
            .pointerInput {
                detectDragGestures { _, dragAmount ->
                    val original = Offset(offsetXPosition.value, offsetYPosition.value)
                    val summed = original + dragAmount
                    val newValue = Offset(
                        x = summed.x.coerceIn(min.toPx(), max.toPx()),
                        y = summed.y.coerceIn(min.toPx(), max.toPx())
                    )
                    offsetXPosition.value = newValue.x
                    offsetYPosition.value = newValue.y
                }
            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .zoomable(zoomableController)
    )
}
