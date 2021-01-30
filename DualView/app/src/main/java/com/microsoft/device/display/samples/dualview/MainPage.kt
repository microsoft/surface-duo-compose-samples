package com.microsoft.device.display.samples.dualview

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberZoomableController
import androidx.compose.foundation.gestures.zoomable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.res.loadVectorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.display.samples.dualview.components.MapView
import com.microsoft.device.display.samples.dualview.components.RestaurantListView
import com.microsoft.device.display.samples.dualview.components.RestaurantView
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.models.Restaurant
import kotlin.math.roundToInt

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
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            RestaurantViewWithTopBar(
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
        composable("map") {
            MapViewWithTopBar(
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
    }
}

@Composable
fun DualScreenUI() {
    ListDetailView()
}

@Composable
fun RestaurantViewWithTopBar(navController: NavController, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("map")
                        }
                    ) {
                        val image = loadVectorResource(id = R.drawable.ic_map)
                        image.resource.resource?.let {
                            Icon(
                                imageVector = it,
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        bodyContent = {
            RestaurantView(navController, appStateViewModel)
        }
    )
}

@Composable
fun MapViewWithTopBar(navController: NavController, appStateViewModel: AppStateViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        val image = loadVectorResource(id = R.drawable.ic_list)
                        image.resource.resource?.let {
                            Icon(
                                imageVector = it,
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        bodyContent = {
            MapView(appStateViewModel)
        }
    )
}

@Composable
fun ListDetailView() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ListViewSpanned(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .weight(1f),
            appStateViewModel = appStateViewModel
        )
        MapView(appStateViewModel)
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
