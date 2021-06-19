package com.microsoft.device.display.samples.chat.models

import java.util.ArrayList

data class Conversation(
    val target: User,
    val message: ArrayList<Message>,
    val inputText: String = ""
)

data class User(
    val imageId: Int,
    val name: String
)

data class Message (
    val sender: User,
    val text: String
)
