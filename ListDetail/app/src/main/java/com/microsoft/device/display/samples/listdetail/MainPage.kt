package com.microsoft.device.display.samples.listdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.companionpane.viewModels.AppStateViewModel
import com.microsoft.device.display.samples.listdetail.models.images

private lateinit var appStateViewModel: AppStateViewModel
private val imagePadding = 10.dp
private val verticalPadding = 35.dp
private val horizontalPadding = 15.dp

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
    Row(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        ListView(modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .weight(1f))
        DetailView(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))
    }
}

@Composable
fun ListView(modifier: Modifier) {
    val imageSelectionLiveData = appStateViewModel.getImageSelectionLiveData()
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val imageList = images
    val subImageList = imageList.chunked(3)

    Box(modifier = modifier.then(
        Modifier
            .fillMaxSize()
            .padding(
                top = verticalPadding,
                bottom = verticalPadding,
                start = horizontalPadding,
                end = horizontalPadding
            )
        )
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(imagePadding)
        ) {
            items(subImageList) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(imagePadding)
                ) {
                    for (image in it) {
                        ImageView(imageId = image, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailView(modifier: Modifier) {
    val imageSelectionLiveData = appStateViewModel.getImageSelectionLiveData()
    val selectedIndex = 0//imageSelectionLiveData.observeAsState(initial = 0).value
    val selectedImageId = images[selectedIndex]

    Column(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                top = imagePadding * 2,
                bottom = imagePadding,
                start = imagePadding,
                end = imagePadding
            ), 
            contentAlignment = Alignment.Center
        ) {
            ImageView(imageId = selectedImageId,
                      modifier = Modifier
                          .height(500.dp)
                          .wrapContentWidth())
        }
        Spacer(modifier = Modifier.preferredHeight(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.preferredWidth(40.dp))
            Box(contentAlignment = Alignment.TopCenter,
                modifier = Modifier.padding(bottom = 10.dp)) {
                ImageView(imageId = R.drawable.info_icon,
                      modifier = Modifier
                          .height(15.dp)
                          .width(15.dp))
            }
            Spacer(modifier = Modifier.preferredWidth(15.dp))
            ImageView(imageId = R.drawable.image_icon,
                      modifier = Modifier
                          .width(25.dp)
                          .height(25.dp))
            Spacer(modifier = Modifier.preferredWidth(20.dp))
            Column(modifier = Modifier.wrapContentWidth()) {
                BasicText(
                    text = stringResource(R.string.camera),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.preferredHeight(3.dp))
                BasicText(
                    modifier = Modifier.wrapContentWidth(),
                    text = stringResource(R.string.camera_info),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_gary)
                    )
                )
            }
            Spacer(modifier = Modifier.preferredWidth(40.dp))
            ImageView(imageId = R.drawable.camera_icon,
                      modifier = Modifier
                          .width(25.dp)
                          .height(25.dp))
            Spacer(modifier = Modifier.preferredWidth(20.dp))
            Column(modifier = Modifier.wrapContentWidth()) {
                BasicText(
                    text = stringResource(R.string.device),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.preferredHeight(3.dp))
                BasicText(
                    modifier = Modifier.wrapContentWidth(),
                    text = stringResource(R.string.device_info),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_gary)
                    )
                )
            }
        }
    }
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

@Composable
fun ImageInfoTile() {

}