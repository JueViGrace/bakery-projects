package com.bakery.ui.messages

import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

interface Messages {
    val messages: Flow<Message>

    suspend fun sendMessage(message: StringResource, description: String? = null)

    companion object {
        val instance: Messages = MessagesImpl
    }
}
