package com.cogniheroid.framework.shared.core.chat

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.cogniheroid.framework.shared.core.chat.database.ChatDatabase
import com.cogniheroid.framework.shared.core.chat.datasource.chatlist.ChatListDataSource
import com.cogniheroid.framework.shared.core.chat.datasource.chatlist.ChatListDataSourceImpl
import com.cogniheroid.framework.shared.core.chat.datasource.message.MessageDataSource
import com.cogniheroid.framework.shared.core.chat.datasource.message.MessageDataSourceImpl
import com.cogniheroid.framework.shared.core.chat.datasource.sender.SenderDataSource
import com.cogniheroid.framework.shared.core.chat.datasource.sender.SenderDataSourceImpl
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManagerImpl
import com.cogniheroid.framework.shared.core.chat.manager.message.MessageManagerImpl
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManagerImpl
import com.cogniheroid.framework.shared.core.chat.repository.chatlist.ChatListRepositoryImpl
import com.cogniheroid.framework.shared.core.chat.repository.message.MessageRepositoryImpl
import com.cogniheroid.framework.shared.core.chat.repository.sender.SenderRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import migrations.Conversation
import migrations.Messages
import migrations.Sender

class Instance(private val driver: SqlDriver) {

    private fun getDatabase(): ChatDatabase {
        val intAdapter = object : ColumnAdapter<Int, Long> {
            override fun decode(databaseValue: Long): Int {
                return databaseValue.toInt()
            }

            override fun encode(value: Int): Long {
                return value.toLong()
            }
        }

        val booleanAdapter = object : ColumnAdapter<Boolean, Long> {
            override fun decode(databaseValue: Long): Boolean {
                return if (databaseValue == 1L) {
                    true
                } else {
                    false
                }
            }

            override fun encode(value: Boolean): Long {
                return if (value) {
                    1
                } else {
                    0
                }
            }
        }

       return ChatDatabase(
            driver,
            ConversationAdapter = Conversation.Adapter(intAdapter),
            MessagesAdapter = Messages.Adapter(
                readStatusAdapter = intAdapter,
                messageContentTypeAdapter = intAdapter, messageTypeAdapter = intAdapter
            ),
          //  SenderAdapter = Sender.Adapter(booleanAdapter)
        )
    }

    private val chatDatabase by lazy {
        getDatabase()
    }

   private val dispatcher by lazy {
        Dispatchers.IO
    }

    val senderDataSource by lazy {
        SenderDataSourceImpl(dispatcher, chatDatabase.senderQueries)
    }

    val chatListDataSource by lazy {
        ChatListDataSourceImpl(dispatcher, chatDatabase.conversationQueries)
    }

     val messageDataSource by lazy {
        MessageDataSourceImpl(dispatcher, chatDatabase.messagesQueries)
    }

     val chatListRepository by lazy {
        ChatListRepositoryImpl(chatListDataSource)
    }

     val messageRepository by lazy {
        MessageRepositoryImpl(messageDataSource)
    }

     val senderRepository by lazy {
        SenderRepositoryImpl(senderDataSource)
    }

    val chatListManager by lazy {
        ChatListManagerImpl(chatListRepository, messageRepository)
    }

    val messageManager by lazy {
        MessageManagerImpl(messageRepository)
    }

    val senderManager by lazy {
        SenderManagerImpl(senderRepository)
    }


}