package com.cogniheroid.framework.shared.core.chat.manager.sender

import com.cogniheroid.framework.shared.core.chat.UniqueIdGenerator
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.repository.sender.SenderRepository

class SenderManagerImpl(private val senderRepository: SenderRepository):SenderManager {

    private val localSenderIdGenerator = UniqueIdGenerator {
        senderRepository.getLastLocalSenderId()
    }

    override suspend fun getLatestLocalSenderId(): Long {
        return localSenderIdGenerator.get()
    }

    override suspend fun getUserSender(): SenderEntity? {
        return senderRepository.getUserSenderInfo()
    }

    override suspend fun getSender(senderId: Long): CommonFlow<SenderEntity> {
        return senderRepository.getSenderEntity(senderId)
    }

    override suspend fun insertSender(senderItem: SenderEntity) {
        senderRepository.insertSenderInfo(senderItem)
    }

    override suspend fun updateSender(senderItem: SenderEntity) {
        senderRepository.updateSenderInfo(senderItem)
    }

    override suspend fun deleteSender(senderId: Long) {
        senderRepository.deleteSenderInfo(senderId)
    }
}