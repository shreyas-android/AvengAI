package com.cogniheroid.framework.shared.core.chat.manager.message

import com.cogniheroid.framework.shared.core.chat.DateTime
import com.cogniheroid.framework.shared.core.chat.UniqueIdGenerator
import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageType
import com.cogniheroid.framework.shared.core.chat.data.enum.ReadStatusType
import com.cogniheroid.framework.shared.core.chat.data.mapper.toConversationItem
import com.cogniheroid.framework.shared.core.chat.data.mapper.toMessageWithSender
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.flow.toCommonFlow
import com.cogniheroid.framework.shared.core.chat.repository.message.MessageRepository
import com.cogniheroid.framework.shared.core.chat.utils.DateTimeUtils
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class MessageManagerImpl(private val messageRepository: MessageRepository): MessageManager {

    private val localMessageIdGenerator = UniqueIdGenerator {
        messageRepository.getLastLocalMessageId()
    }

    override suspend fun getLatestLocalMessageId(): Long {
        return localMessageIdGenerator.get()
    }

    override fun getMessages(chatId: Long): CommonFlow<List<MessageWithSender>> {
        return messageRepository.getMessagesEntities(chatId).map {
            it.map {
                it.toMessageWithSender("")
            }
        }.toCommonFlow()
    }

    override suspend fun insertAndGetNewMessage(
        chatId: Long,
        senderId: Long,
        message: String?,
        messageTime: Long,
        fileUri:String?,
        readStatus: ReadStatusType,
        messageType: MessageType,
        messageContentType: MessageContentType,
        replyMessageId: Long?
    ): MessagesEntity {
        val newMessageId = getLatestLocalMessageId()

        val messageStartDate = DateTimeUtils.getStartDay(messageTime)
        val messageEntity = MessagesEntity(messageId = newMessageId, chatId = chatId, senderId = senderId,
            message = message, messageTime = messageTime, readStatus = readStatus.value,
            messageType = messageType.value, replyMessageId = replyMessageId,
            messageContentType = messageContentType.value, messageStartDate = messageStartDate,
            fileUri = fileUri)
        messageRepository.insertMessage(messageEntity)

        return messageEntity
    }

    override suspend fun insertMessage(messagesEntity: MessagesEntity) {
       messageRepository.insertMessage(messagesEntity)
    }

    override suspend fun updateMessage(messagesEntity: MessagesEntity) {
        messageRepository.updateMessage(messagesEntity)
    }

    override suspend fun deleteMessage(messageId: Long) {
        messageRepository.deleteMessage(messageId)
    }

    override suspend fun deleteMessageByChatId(chatId: Long) {
        messageRepository.deleteMessageByChatId(chatId)
    }
}