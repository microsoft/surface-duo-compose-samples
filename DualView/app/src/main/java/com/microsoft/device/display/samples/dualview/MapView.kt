package com.microsoft.device.display.samples.dualview.components

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
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.models.restaurants
import kotlin.math.roundToInt

@Composable
fun MapView(appStateViewModel: AppStateViewModel) {
    val selectionLiveData = appStateViewModel.getSelectionLiveData()
    val selectedIndex = selectionLiveData.observeAsState(initial = -1).value
    var selectedMapId = R.drawable.unselected_map
    if (selectedIndex > -1) {
        selectedMapId = restaurants[selectedIndex].mapImageResourceId
    }
    ScalableImageView(selectedMapId)
}

@Composable
fun ScalableImageView(imageId: Int) {
    val max = 400.dp
    val min = (-400).dp
    val offsetXPosition = remember { mutableStateOf(0f) }
    val offsetYPosition = remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(3f) }
    val zoomableController = rememberZoomableController { scale *= it }

    Image(
        bitmap = imageResource(imageId),
        contentScale = ContentScale.FillHeight,
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
