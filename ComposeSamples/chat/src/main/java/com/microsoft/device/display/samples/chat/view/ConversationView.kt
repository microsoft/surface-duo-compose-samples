package com.microsoft.device.display.samples.chat.view

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.microsoft.device.display.samples.chat.R
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel
import androidx.compose.ui.text.style.TextOverflow
import com.microsoft.device.display.samples.chat.ChatDetails
import com.microsoft.device.display.samples.chat.models.UserViewModel
import com.microsoft.device.display.samples.chat.utils.NoRippleIconButton

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
        Row {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                ConversationView()
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                ChatDetails()
            }
        }
    }
}

@Composable
fun SingleScreenUI() {
    val appStateViewModel = hiltViewModel<AppStateViewModel>()
    val userViewModel = hiltViewModel<UserViewModel>()

    Log.d(TAG, "value is ${userViewModel.me.value.name}")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (appStateViewModel.displayChatDetails) {
                        Text(userViewModel.activeConversation.value!!.target.value.name.value)
                    }
                    else Text(stringResource(id = R.string.app_name))
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    if (appStateViewModel.displayChatDetails) {
                        IconButton(
                            onClick = { appStateViewModel.displayChatDetails = false }
                        ) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    } else {
                        NoRippleIconButton(onClick = {  }
                        ) {
                            Icon(Icons.Filled.Home, null)
                        }
                    }
                }
            )
        },
        backgroundColor = Color(0xFFF8F8F8)
    ) {
        ConversationView()
        ChatDetails()
    }
}

@Composable
fun ConversationView() {

    val userViewModel = hiltViewModel<UserViewModel>()
    val appStateViewModel = hiltViewModel<AppStateViewModel>()
    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value
    val list = rememberLazyListState()
    val conversations = userViewModel.conversations

    LazyColumn(
        state = list
    ) {
        items(conversations.size) { item ->
            val conversation = conversations[item]
            ConversationItem(
                contactName = conversation.target.value.name.value,
                lastMessage = conversation.messages.lastOrNull()?.content?.value ?: "",
                unreadMessageNum = conversation.messages.size,
                id = conversation.id.value,
                appStateViewModel = appStateViewModel,
                logoId = conversation.target.value.avatar.value!!,
                isDualMode = isDualMode
            )
            if (item != conversations.size - 1) Divider(Modifier.padding(start = 70.dp), thickness = (0.5).dp)
        }
    }
}

@Composable
fun ConversationItem(
    contactName: String,
    lastMessage: String?,
    unreadMessageNum: Int,
    id: String,
    appStateViewModel: AppStateViewModel,
    logoId: Int,
    isDualMode: Boolean
) {
    val userViewModel = hiltViewModel<UserViewModel>()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                userViewModel.setActiveConversation(id)
                if (!isDualMode) appStateViewModel.displayChatDetails = true
            }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(35.dp)
        ) {
            Image(
                painterResource(id = logoId),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = contactName,
                    fontWeight = FontWeight.W700
                )
                Spacer(Modifier.padding(horizontal = 3.dp))
                Icon(
                    painterResource(id = R.drawable.verified),
                    contentDescription = null,
                    tint = Color(0xFF1DA1F2),
                    modifier = Modifier.size(18.dp)
                )
            }
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium
            ) {
                Text(
                    text = lastMessage!!,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Surface(
                color = Color.Red,
                shape = CircleShape
            ) {
                Text(
                    text = unreadMessageNum.toString(),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
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
