package com.cogniheroid.framework.shared.core.chat.datasource.sender

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.cogniheroid.framework.shared.core.chat.data.mapper.toSenderInfo
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import database.SenderQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SenderDataSourceImpl(private val dispatcher:CoroutineContext, private val senderInfoQueries: SenderQueries):SenderDataSource {
    override suspend fun getLastLocalSenderId(): Long {
        return senderInfoQueries.getLocalSenderId().executeAsOne()
    }

    override fun getAllSenderInfo(): Flow<List<SenderEntity>> {
        return senderInfoQueries.getAllSenderInfo().asFlow().mapToList(dispatcher).map {
            it.map {sender->
                sender.toSenderInfo()
            }
        }
    }

    override suspend fun getSenderInfo(senderId: Long): Flow<SenderEntity> {
        return senderInfoQueries.getSenderInfo(senderId).asFlow().mapToOne(dispatcher).map {
           it.toSenderInfo()
        }
    }

    override suspend fun insertSenderInfo(senderEntity: SenderEntity) {
       senderInfoQueries.insertSenderInfo(senderEntity.senderId, senderEntity.senderName,
           senderEntity.senderImageUri, senderEntity.isUser)
    }

    override suspend fun updateSenderInfo(senderEntity: SenderEntity) {
        senderInfoQueries.updateSenderInfo(senderId = senderEntity.senderId, senderName = senderEntity.senderName,
          senderImageUri =   senderEntity.senderImageUri, isUser = senderEntity.isUser
        )
    }

    override suspend fun deleteSenderInfo(senderId: Long) {
        senderInfoQueries.deleteSenderInfo(senderId)
    }

    override suspend fun getUserSenderInfo(): SenderEntity? {
        val senderInfo = senderInfoQueries.getUserSenderInfo().executeAsOneOrNull()
        return senderInfo?.toSenderInfo()
    }
}