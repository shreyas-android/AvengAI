package com.cogniheroid.framework.feature.chat

import com.cogniheroid.framework.feature.chat.ui.conversationowner.ConversationViewModelFactory
import com.cogniheroid.framework.feature.chat.ui.conversations.MessageViewModelFactory
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManager
import com.cogniheroid.framework.shared.core.chat.manager.message.MessageManager
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManager

object Instance {


    private val chatListManager: ChatListManager by lazy {
        ChatListManager.getInstance()
    }

    private val messageManager: MessageManager by lazy {
        MessageManager.getInstance()
    }

    private val senderManager: SenderManager by lazy {
        SenderManager.getInstance()
    }

    val conversationViewModelFactory: ConversationViewModelFactory by lazy {
        ConversationViewModelFactory(chatListManager, senderManager)
    }

    val messageViewModelFactory: MessageViewModelFactory by lazy {
        MessageViewModelFactory(chatListManager = chatListManager, messageManager = messageManager, avengerAIManager = ChatCore.avengerAIManager,
           senderManager =  senderManager)
    }

}