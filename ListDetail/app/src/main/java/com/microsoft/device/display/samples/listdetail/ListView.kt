package com.microsoft.device.display.samples.listdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.microsoft.device.display.samples.listdetail.models.AppStateViewModel
import com.microsoft.device.display.samples.listdetail.models.images

private val imagePadding = 10.dp
private val verticalPadding = 35.dp
private val horizontalPadding = 15.dp
private val imageMargin = 3.dp

@Composable
fun ListViewSpanned(modifier: Modifier, appStateViewModel: AppStateViewModel) {
    ListView(
        modifier = modifier,
        navController = null,
        appStateViewModel = appStateViewModel
    )
}

@Composable
fun ListViewUnspanned(navController: NavController?, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicText(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            )
        },
        bodyContent = {
            ListView(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
    )
}

@Composable
fun ListView(modifier: Modifier, navController: NavController?, appStateViewModel: AppStateViewModel) {
    val imageSelectionLiveData = appStateViewModel.getImageSelectionLiveData()
    val selectedIndex = imageSelectionLiveData.observeAsState(initial = 0).value
    val imageList = images
    val subImageList = imageList.chunked(3)

    Box(
        modifier = modifier.then(
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
            itemsIndexed(subImageList) { index, item ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(imagePadding)
                ) {
                    for ((imageIndex, image) in item.withIndex()) {
                        var listIndex = 3 * index + imageIndex
                        var outlineWidth = if (listIndex == selectedIndex) imageMargin else 0.dp
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(color = colorResource(id = R.color.outline_blue))
                                .padding(
                                    top = outlineWidth,
                                    bottom = outlineWidth,
                                    start = outlineWidth,
                                    end = outlineWidth
                                )
                        ) {
                            ImageView(
                                imageId = image,
                                modifier = Modifier
                                    .selectable(
                                        selected = (listIndex == selectedIndex),
                                        onClick = {
                                            appStateViewModel.setImageSelectionLiveData(listIndex)
                                            navController?.let {
                                                it.navigate("detail")
                                            }
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}
