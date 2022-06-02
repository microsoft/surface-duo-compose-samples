package com.example.videochatcomposesample.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.videochatcomposesample.ChatMessage
import com.example.videochatcomposesample.models.ChatModel

var chatModel: ChatModel = ChatModel()

@Composable
fun ChatPage(focusManager: FocusManager) {
    Scaffold(
        topBar = { ChatTitle() },
        bottomBar = { ChatInputBar(focusManager = focusManager) }
    ) {
        Chat()
    }
}

@Composable
fun Chat() {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.85f)
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
    ) {
        ChatList()
    }
}

@Composable
fun ChatTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text("STREAM CHAT", modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colors.onBackground)
    }
}

@Composable
fun ChatInputBar(focusManager: FocusManager) {
    var text by remember { mutableStateOf("") }
    fun closeKeyBoard() {
        focusManager.clearFocus()
    }

    CompositionLocalProvider(LocalElevationOverlay provides null) {
        BottomAppBar(backgroundColor = MaterialTheme.colors.background, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .align(Alignment.CenterVertically)
            ) {
                TextField(
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            "Send",
                            modifier = Modifier
                                .clickable
                                {
                                    // TODO Handle Chat Sending here
                                    closeKeyBoard()
                                },
                            tint = MaterialTheme.colors.onBackground

                        )
                    },
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text(
                            "Send a Chat Message...",
                            style = TextStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 10.sp
                            )
                        )
                    },

                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                        .fillMaxWidth(0.95f),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 10.sp,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onAny = {
                            closeKeyBoard()
                        }
                    ),
                )
            }
        }
    }
}

@Composable
fun ChatList() {
    LazyColumn(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        items(chatModel.chatHistory) { chat ->
            Column() {
                IndividualChatMessage(chat = chat)
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun IndividualChatMessage(chat: ChatMessage) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

    ) {
        Text(
            stringResource(id = chat.author) + ":",
            color = chat.color,
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.Top)
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            stringResource(id = chat.message),
            color = MaterialTheme.colors.onBackground,
            fontSize = 15.sp
        )
    }
}
