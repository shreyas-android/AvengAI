package com.cogniheroid.framework.shared.core.chat.repository.sender

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow

interface SenderRepository {

    companion object{
            fun getInstance(): SenderRepository {
                return ChatDomainCore.instance.senderRepository
            }
    }

   suspend fun getLastLocalSenderId(): Long

    suspend fun getSenderEntities():CommonFlow<List<SenderEntity>>

    suspend fun getSenderEntity(senderId: Long): CommonFlow<SenderEntity>

    suspend fun insertSenderInfo(senderEntity: SenderEntity)

    suspend fun updateSenderInfo(senderEntity: SenderEntity)

    suspend fun deleteSenderInfo(senderId: Long)

    suspend fun getUserSenderInfo(): SenderEntity?

}