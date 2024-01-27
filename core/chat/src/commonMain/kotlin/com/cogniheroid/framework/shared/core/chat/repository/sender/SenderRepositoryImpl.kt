package com.cogniheroid.framework.shared.core.chat.repository.sender

import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import com.cogniheroid.framework.shared.core.chat.datasource.sender.SenderDataSource
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.flow.toCommonFlow

class SenderRepositoryImpl(private val senderDataSource: SenderDataSource):SenderRepository {
    override suspend fun getLastLocalSenderId(): Long {
        return senderDataSource.getLastLocalSenderId()
    }

    override suspend fun getSenderEntities(): CommonFlow<List<SenderEntity>> {
        return senderDataSource.getAllSenderInfo().toCommonFlow()
    }

    override suspend fun getSenderEntity(senderId: Long): CommonFlow<SenderEntity> {
        return senderDataSource.getSenderInfo(senderId).toCommonFlow()
    }

    override suspend fun insertSenderInfo(senderEntity: SenderEntity) {
        senderDataSource.insertSenderInfo(senderEntity)
    }

    override suspend fun updateSenderInfo(senderEntity: SenderEntity) {
       senderDataSource.updateSenderInfo(senderEntity)
    }

    override suspend fun deleteSenderInfo(senderId: Long) {
       senderDataSource.deleteSenderInfo(senderId)
    }

    override suspend fun getUserSenderInfo(): SenderEntity? {
        return senderDataSource.getUserSenderInfo()
    }
}