package com.microsoft.device.display.samples.chat.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.microsoft.device.display.samples.chat.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

/**
 * App user model
 */

data class User(
    val info: Contact,
    val conversations: List<Conversation>
)

data class Contact(
    val id: String,
    val name: String,
    val avatar: Int?
)

data class Conversation(
    val id: String,
    val target: Contact,
    val messages: List<Message>,
    val contentToSend: String = ""
)

data class Message(
    val id: String,
    val sender: Contact,
    val content: String,
    val time: Long
)

data class UserState(private val user: User) {
    val info = mutableStateOf(user.info)
    val conversations = let {
        val value = mutableStateListOf<ConversationState>()
        value.addAll(
            user.conversations.map {
                ConversationState(it)
            }
        )
        value
    }
}

data class ContactState(private val contact: Contact) {
    val name = mutableStateOf(contact.name)
    val avatar = mutableStateOf(contact.avatar)
}

data class ConversationState(private val conversation: Conversation) {
    val id = mutableStateOf(conversation.id)
    val target = mutableStateOf(ContactState(conversation.target))
    val messages = mutableStateListOf(
        conversation.messages.last().let {
            MessageState(it)
        }
    )
    val contentToSend = mutableStateOf(conversation.contentToSend)
}

data class MessageState(private val message: Message) {
    val id = mutableStateOf(message.id)
    val sender = mutableStateOf(ContactState(message.sender))
    val content = mutableStateOf(message.content)
    val time = mutableStateOf(message.time)
}

class UserRepos @Inject constructor() {
    fun getUser(): User {

        val me = Contact(id = "123", name = "def", avatar = 123)
        val microsoft = Contact(id = "1", name = "Microsoft", avatar = R.drawable.microsoft)
        val twitter = Contact(id = "2", name = "Twitter", avatar = R.drawable.twitter)
        val google = Contact(id = "3", name = "Google", avatar = R.drawable.google)

        return User(
            info = me,
            conversations = listOf(
                Conversation(
                    id = UUID.randomUUID().toString(),
                    target = microsoft,
                    messages = listOf(
                        Message(
                            id = UUID.randomUUID().toString(),
                            sender = microsoft,
                            content = "hello this is Microsoft",
                            time = 12345678912345L
                        ),
                    )
                ),
                Conversation(
                    id = UUID.randomUUID().toString(),
                    target = twitter,
                    messages = listOf(
                        Message(
                            id = UUID.randomUUID().toString(),
                            sender = twitter,
                            content = "hello im twitter",
                            time = 123111678912345L
                        ),
                    )
                ),
                Conversation(
                    id = UUID.randomUUID().toString(),
                    target = google,
                    messages = listOf(
                        Message(
                            id = UUID.randomUUID().toString(),
                            sender = google,
                            content = "hello im google",
                            time = 12388878912345L
                        ),
                    )
                ),
            )
        )
    }
}

@HiltViewModel
class UserViewModel @Inject constructor(
    userRepos: UserRepos
) : ViewModel() {
    private var _state: UserState = UserState(userRepos.getUser())
    private val _activeConversation: MutableState<ConversationState?> = mutableStateOf(null)

    val me by lazy { _state.info }
    val conversations by lazy { _state.conversations }
    val activeConversation = _activeConversation

    fun setActiveConversation(id: String?) {
        _activeConversation.value =
            if (id != null) conversations.first { it.id.value == id } else null
    }

    fun sendMessage(content: String): String? {
        activeConversation.value?.let {
            val msg = Message(
                id = UUID.randomUUID().toString(),
                sender = me.value,
                content = content,
                time = System.currentTimeMillis()
            )
            it.messages.add(MessageState(msg))
            return msg.id
        }
        return null
    }

    fun deleteMessage(id: String) {
        activeConversation.value?.let {
            it.messages.removeAll { it2 -> it2.id.value == id }
        }
    }
}
