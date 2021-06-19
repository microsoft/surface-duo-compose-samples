package com.microsoft.device.display.samples.chat.models

import com.microsoft.device.display.samples.chat.R
import kotlin.collections.ArrayList

object DataProvider {

    val contactModels: ArrayList<Conversation>
        get() {
            val items = ArrayList<Conversation>()
            val friends: ArrayList<User> = arrayListOf(
                User(
                    imageId = R.drawable.microsoft,
                    name = "Microsoft"
                ),
                User(
                    imageId = R.drawable.twitter,
                    name = "Twitter"
                ),
                User(
                    imageId = R.drawable.google,
                    name = "Google"
                ),
            )
            items.add(
                Conversation(
                    target = friends[0],
                    message = arrayListOf(
                        Message(
                            sender = friends[0],
                            text = "Hi, This is Microsoft"
                        ),
                        Message(
                            sender = friends[0],
                            text = "Do you like Surface Duo?"
                        ),
                        Message(
                            sender = friends[0],
                            text = "Welcome to Surface Duo"
                        )
                    )
                )
            )
            items.add(
                Conversation(
                    target = friends[1],
                    message = arrayListOf(
                        Message(
                            sender = friends[1],
                            text = "Hi, I'm Twitter"
                        ),
                        Message(
                            sender = friends[1],
                            text = "I like use Surface Duo!"
                        ),
                    )
                )
            )
            items.add(
                Conversation(
                    target = friends[2],
                    message = arrayListOf(
                        Message(
                            sender = friends[2],
                            text = "Hi, I'm Google"
                        ),
                        Message(
                            sender = friends[2],
                            text = "Welcome to Surface Duo"
                        )
                    )
                )
            )

            return items
        }
}
