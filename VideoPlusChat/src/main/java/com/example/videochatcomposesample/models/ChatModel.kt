package com.example.videochatcomposesample.models

import androidx.compose.ui.graphics.Color
import com.example.videochatcomposesample.ChatMessage

class ChatModel {

    val chatHistory = listOf<ChatMessage>(

        ChatMessage(
            "LolforDoodad", "HHelloooo WORLLLDdddddd", Color.Green
        ),
        ChatMessage(
            "hoiiii", "Goodbye", Color.Blue
        ),
        ChatMessage(
            "bellweather", "We are ready to watch da movie", Color.Cyan
        ),
        ChatMessage(
            "wigwag", "Movie time!", Color.Yellow
        ),
        ChatMessage(
            "dinodancer22", "When is this gonna start?", Color.Cyan
        ),
        ChatMessage(
            "redrobinbun", "ive got popcorn", Color.Green
        ),
        ChatMessage(
            "cattidudE", "was i supposed to watch the last one?!!?!", Color.Cyan
        ),
        ChatMessage(
            "SmoothCriminal89", "Sign up for our free 20 day trial here: hllps://freedancelessions.com ", Color.Red
        ),
        ChatMessage(
            "LolforDoodad", "I wanna go ged lunch", Color.Green
        ),

    )
}
