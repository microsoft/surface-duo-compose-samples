package com.microsoft.device.display.samples.dualview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.zoomable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.rawDragGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.models.restaurants

@Composable
fun MapView(modifier: Modifier, appStateViewModel: AppStateViewModel) {
    val selectionLiveData = appStateViewModel.getSelectionLiveData()
    val selectedIndex = selectionLiveData.observeAsState(initial = -1).value
    var selectedMapId = R.drawable.unselected_map
    if (selectedIndex > -1) {
        selectedMapId = restaurants[selectedIndex].mapImageResourceId
    }
    
    Box(modifier = modifier) {
        Box(
            Modifier
                .matchParentSize()
                .background(Color.Yellow)
        ) {
            ScalableImageView(imageId = selectedMapId)
        }
    }
}

@Composable
fun ScalableImageView(imageId: Int) {
    var scale by remember { mutableStateOf(3f) }
    var translate by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier.zoomable(onZoomDelta = { scale *= it }).rawDragGestureFilter(
            object : DragObserver {
                override fun onDrag(dragDistance: Offset): Offset {
                    translate = translate.plus(dragDistance)
                    return super.onDrag(dragDistance)
                }
            })
    ) {
        val imageBitmap = imageResource(id = imageId)
        Image(
            modifier = Modifier.fillMaxSize().graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = translate.x,
                translationY = translate.y
            ),
            bitmap = imageBitmap
        )
    }
}
