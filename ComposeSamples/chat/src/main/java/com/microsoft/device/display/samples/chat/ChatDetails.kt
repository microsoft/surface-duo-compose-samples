package com.microsoft.device.display.samples.chat

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.chat.models.ContactModel
import com.microsoft.device.display.samples.chat.utils.ChatBubbleLeftArrowShape
import com.microsoft.device.display.samples.chat.utils.NoRippleIconButton
import com.microsoft.device.display.samples.chat.utils.percentOffsetX
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel

@Composable
fun ChatDetails(
    models: List<ContactModel>,
    appStateViewModel: AppStateViewModel
) {
    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value
    val percentOffsetX = animateFloatAsState(if (appStateViewModel.displayChatDetails && !isDualMode) 0f else 1f)
    if (isDualMode) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
        ) {
            ChatList(models, appStateViewModel)
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                appStateViewModel.displayChatDetails = false
                            }
                        ) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    },
                    backgroundColor = Color.White
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .percentOffsetX(percentOffsetX.value),
            backgroundColor = Color(0xFFF8F8F8)
        ) {
            ChatList(models, appStateViewModel)
        }
    }
}

@Composable
fun ChatList(
    models: List<ContactModel>,
    appStateViewModel: AppStateViewModel
) {
    val listState = rememberLazyListState()
    val index = appStateViewModel.selectedIndex
    val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
    val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value
    var isFocused by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = if (isDualMode) 25.dp else 12.dp, vertical = 5.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            item {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium
                ) {
                    Text(
                        text = "16:00",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.padding(vertical = 8.dp))
            }
            items(
                models[index].message.size
            ) { message ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = models[index].imageId),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Row {
                        Surface(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = ChatBubbleLeftArrowShape()
                                )
                                .width(8.dp)
                        ) { }
                        Surface(
                            shape = RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp),
                            color = Color.White
                        ) {
                            Text(
                                text = models[index].message[message],
                                modifier = Modifier
                                    .padding(8.dp),
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.W600
                            )
                        }
                    }
                }
                Spacer(Modifier.padding(vertical = 5.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .weight(1f)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .height(35.dp),
                decorationBox = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NoRippleIconButton(onClick = { /*TODO*/ }) {
                            Icon(painterResource(id = R.drawable.mood), null)
                        }
                        if (!isFocused && text == "") {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.medium
                            ) {
                                Text("Type a Message")
                            }
                        }
                        it()
                    }
                },
            )
            if (text != "") {
                Spacer(Modifier.padding(horizontal = 6.dp))
                Surface(
                    color = Color(0xFF0079D3),
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Filled.Send,
                        null,
                        tint = Color.White,
                        modifier = Modifier
                            .clickable { }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}
