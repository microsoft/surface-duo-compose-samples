package com.microsoft.device.display.samples.listdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.viewModels.AppStateViewModel
import com.microsoft.device.display.samples.listdetail.models.images

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel
    val isScreenSpannedLiveData = appStateViewModel.getIsScreenSpannedLiveData()
    val isScreenSpanned = isScreenSpannedLiveData.observeAsState(initial = false).value

    if (isScreenSpanned) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }
}

@Composable
fun SingleScreenUI() {
    ListView(modifier = Modifier.fillMaxSize())
}

@Composable
fun DualScreenUI() {

}

@Composable
fun ListView(modifier: Modifier) {
    val imageSelectionLiveData = appStateViewModel.getImageSelectionLiveData()
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val imageList = images
    val subImageList = imageList.chunked(3)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(subImageList) {
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                for (image in it)
                    ImageView(imageId = image, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun DetailView() {

}

@Composable
fun ImageView(imageId: Int, modifier: Modifier) {
    val image = loadImageResource(id = imageId)
    image.resource.resource?.let {
        Image(
            bitmap = it,
            modifier = modifier
        )
    }
}