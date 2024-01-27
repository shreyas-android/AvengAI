package com.cogniheroid.framework.shared.core.chat.datasource.sender

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import kotlinx.coroutines.flow.Flow

interface SenderDataSource {

    companion object{

            fun getInstance(): SenderDataSource {
                return ChatDomainCore.instance.senderDataSource
            }
    }

    suspend fun getLastLocalSenderId(): Long

    fun getAllSenderInfo(): Flow<List<SenderEntity>>

    suspend  fun getSenderInfo(senderId: Long): Flow<SenderEntity>

    suspend fun insertSenderInfo(senderEntity: SenderEntity)

    suspend fun updateSenderInfo(senderEntity: SenderEntity)

    suspend  fun deleteSenderInfo(senderId: Long)

    suspend fun getUserSenderInfo(): SenderEntity?
}