package com.cogniheroid.framework.shared.core.chat.manager.message

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageType
import com.cogniheroid.framework.shared.core.chat.data.enum.ReadStatusType
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow

interface MessageManager {

    companion object{

        fun getInstance(): MessageManager {
            return ChatDomainCore.instance.messageManager
        }
    }

   suspend fun getLatestLocalMessageId(): Long

    fun getMessages(chatId: Long): CommonFlow<List<MessageWithSender>>

    suspend fun insertAndGetNewMessage(chatId: Long,
                                       senderId: Long,
                                       message: String?,
                                       messageTime: Long,
                                       fileUri:String?,
                                       readStatus: ReadStatusType,
                                       messageType: MessageType,
                                       messageContentType: MessageContentType,
                                       replyMessageId: Long?): MessagesEntity
   suspend fun insertMessage(messagesEntity: MessagesEntity)

    suspend  fun updateMessage(messagesEntity: MessagesEntity)

    suspend fun deleteMessage(messageId: Long)

    suspend fun deleteMessageByChatId(chatId:Long)
}