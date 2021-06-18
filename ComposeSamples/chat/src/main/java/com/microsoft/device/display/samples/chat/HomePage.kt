package com.microsoft.device.display.samples.chat

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.chat.models.ContactModel
import com.microsoft.device.display.samples.chat.models.DataProvider
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel

private lateinit var appStateViewModel: AppStateViewModel

@Composable
fun SetupUI(viewModel: AppStateViewModel) {
    appStateViewModel = viewModel

    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value
    val models = DataProvider.contactModels

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
        if (isDualMode) {
            DualScreenUI(models, appStateViewModel)
        } else {
            SingleScreenUI(models)
        }
    }
}

@Composable
fun DualScreenUI(
    models: List<ContactModel>,
    appStateViewModel: AppStateViewModel
) {
    Row {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ContactList(models, appStateViewModel)
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Crossfade(targetState = appStateViewModel.selectedIndex) {
                ChatDetails(models, it)
            }
        }
    }
}

@Composable
fun SingleScreenUI(
    models: List<ContactModel>
) {
    ContactList(models, appStateViewModel)
}

@Composable
fun ContactList(
    models: List<ContactModel>,
    appStateViewModel: AppStateViewModel
) {
    LazyColumn() {
        itemsIndexed(models) { index, item ->
            ContactListItem(contactName = item.name, lastMessage = "Welcome to Surface Duo", unreadMessageNum = 5, index, appStateViewModel) {
                Image(
                    painterResource(id = item.imageId),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ContactListItem(
    contactName: String,
    lastMessage: String,
    unreadMessageNum: Int,
    index: Int,
    appStateViewModel: AppStateViewModel,
    logo: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                appStateViewModel.selectedIndex = index
            }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(40.dp)
        ) {
            logo()
        }
        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Row {
                Text(
                    text = contactName,
                    fontWeight = FontWeight.W700
                )
                Spacer(Modifier.padding(horizontal = 3.dp))
                Icon(
                    painterResource(id = R.drawable.verified),
                    contentDescription = null,
                    tint = Color(0xFF1DA1F2),
                    modifier = Modifier.size(20.dp)
                )
            }
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium
            ) {
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Surface(
                color = Color.Red,
                shape = CircleShape
            ) {
                Text(
                    text = unreadMessageNum.toString(),
                    color = Color.White,
                    modifier = Modifier.padding(
                        start = 6.dp,
                        end = 6.dp,
                        top = 2.dp,
                        bottom = 2.dp
                    ),
                    style = TextStyle(
                        fontWeight = FontWeight.W900,
                        fontSize = 12.sp,
                        letterSpacing = 0.15.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}