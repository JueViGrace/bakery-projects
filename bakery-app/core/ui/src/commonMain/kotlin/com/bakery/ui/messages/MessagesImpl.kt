package com.bakery.ui.messages

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

internal object MessagesImpl : Messages {
    private val _messages: Channel<Message> = Channel<Message>()
    override val messages: Flow<Message> = _messages.receiveAsFlow()

    override suspend fun sendMessage(message: StringResource, description: String?) {
        _messages.send(Message(message, description))
    }
}
