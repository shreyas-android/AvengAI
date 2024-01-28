package com.cogniheroid.framework.shared.core.chat.manager.sender

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow

interface SenderManager {

    companion object{

        fun getInstance(): SenderManager {
            return ChatDomainCore.instance.senderManager
        }
    }

    suspend fun getLatestLocalSenderId(): Long

    suspend fun getUserSender(): SenderEntity?

    suspend fun getSender(senderId: Long): CommonFlow<SenderEntity>

    suspend fun insertNewSender(senderName:String, senderImageUri:String?,
                                isUser:Boolean)

    suspend fun insertSender(senderItem: SenderEntity)

    suspend fun updateSender(senderItem: SenderEntity)

    suspend fun deleteSender(senderId: Long)


}