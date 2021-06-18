package com.microsoft.device.display.samples.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value

    if (isDualMode) {
        DualScreenUI()
    } else {
        SingleScreenUI()
    }
}

@Composable
fun DualScreenUI() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                backgroundColor = Color.White
            )
        },
        backgroundColor = Color(0xFFF8F8F8)
    ) {
        Row() {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                ContactList()
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text("HEllo")
            }
        }
    }
}

@Composable
fun SingleScreenUI() {
    Text("Hi")
}


@Composable
fun ContactList() {
    LazyColumn() {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable {

                    }
                    .padding(8.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .size(45.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.logo),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.logo_name),
                            fontWeight = FontWeight.W700
                        )
                        Spacer(Modifier.padding(horizontal = 3.dp))
                        Icon(painterResource(id = R.drawable.verified), null, tint = Color(0xFF1DA1F2))
                    }
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.medium
                    ) {
                        Text(
                            text = "Welcome to the Surface Duo!",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}
