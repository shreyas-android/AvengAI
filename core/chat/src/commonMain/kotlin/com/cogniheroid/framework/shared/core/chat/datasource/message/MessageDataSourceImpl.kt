package com.cogniheroid.framework.shared.core.chat.datasource.message

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.cogniheroid.framework.shared.core.chat.data.mapper.toMessageEntity
import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.mapper.toMessageWithSender
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import database.MessagesQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class MessageDataSourceImpl(private val dispatcher:CoroutineContext, private val messageQueries: MessagesQueries):MessageDataSource {
    override suspend fun getLastLocalMessageId(): Long {
        return messageQueries.getLocalMessageId().executeAsOne().MAX ?: 1L
    }

    override fun getMessages(chatId: Long): Flow<List<MessagesEntity>> {
        return messageQueries.getMessages(chatId).asFlow().mapToList(dispatcher).map {
                it.map {messageItem ->
                    messageItem.toMessageEntity()
                }
        }
    }

    override fun getJoinedMessageEntities(chatId: Long): Flow<List<MessageWithSender>> {
        return messageQueries.getJoinedMessages(chatId).asFlow().mapToList(dispatcher).map {
            it.map { getJoinedMessages ->
                getJoinedMessages.toMessageWithSender()
            }
        }
    }

    override suspend fun insertMessage(messagesEntity: MessagesEntity) {
        messageQueries.insertMessage(messagesEntity.messageId, messagesEntity.chatId, messagesEntity.senderId,
            message = messagesEntity.message, messageContentType = messagesEntity.messageContentType,
            messageStartDate = messagesEntity.messageStartDate, messageTime = messagesEntity.messageTime,
            fileUri = messagesEntity.fileUri,
            messageType = messagesEntity.messageType, replyMessageId = messagesEntity.replyMessageId,
            readStatus = messagesEntity.readStatus)
    }

    override suspend fun updateMessage(messagesEntity: MessagesEntity) {
        messageQueries.updateMessage(messageId = messagesEntity.messageId, chatId =  messagesEntity.chatId,
            senderId = messagesEntity.senderId,
            message = messagesEntity.message, messageContentType = messagesEntity.messageContentType,
            messageStartDate = messagesEntity.messageStartDate, messageTime = messagesEntity.messageTime,
            fileUri = messagesEntity.fileUri,
            messageType = messagesEntity.messageType, replyMessageId = messagesEntity.replyMessageId,
            readStatus = messagesEntity.readStatus)
    }

    override suspend fun deleteMessage(messageId: Long) {
        messageQueries.deleteMessage(messageId)
    }

    override suspend fun deleteMessageByChatId(chatId: Long) {
        messageQueries.deleteMessageByChatId(chatId)
    }
}