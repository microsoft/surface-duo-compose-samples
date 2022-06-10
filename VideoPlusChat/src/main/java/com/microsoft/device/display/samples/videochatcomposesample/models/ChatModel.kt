/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.models

import androidx.compose.ui.graphics.Color
import com.microsoft.device.display.samples.videochatcomposesample.R

class ChatModel {
    val chatHistory = listOf(
        ChatMessage(R.string.auth1, R.string.chat_text_1, Color.Red),
        ChatMessage(R.string.auth2, R.string.chat_text_2, Color.Green),
        ChatMessage(R.string.auth3, R.string.chat_text_3, Color.Cyan),
        ChatMessage(R.string.auth4, R.string.chat_text_4, Color.Blue),
        ChatMessage(R.string.auth5, R.string.chat_text_5, Color.Magenta),
        ChatMessage(R.string.auth1, R.string.chat_text_6, Color.Red),
        ChatMessage(R.string.auth6, R.string.chat_text_7, Color.Yellow),
        ChatMessage(R.string.auth3, R.string.chat_text_8, Color.Cyan),
        ChatMessage(R.string.auth7, R.string.chat_text_9, Color.LightGray),
        ChatMessage(R.string.auth7, R.string.chat_text_10, Color.LightGray)
    )
}
