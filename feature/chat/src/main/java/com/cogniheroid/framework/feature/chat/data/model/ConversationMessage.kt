package com.cogniheroid.framework.feature.chat.data.model

sealed class ConversationMessage {

    object Loading: ConversationMessage()

    data class Messages(
        val messageMap:Map<String, List<ChatDetailItem>>
    ): ConversationMessage()
}