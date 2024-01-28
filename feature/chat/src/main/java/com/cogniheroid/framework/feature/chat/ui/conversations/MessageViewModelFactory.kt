package com.cogniheroid.framework.feature.chat.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManager
import com.cogniheroid.framework.shared.core.chat.manager.message.MessageManager
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManager

class MessageViewModelFactory(
    private val chatListManager: ChatListManager,
    private val avengerAIManager: AvengerAIManager,
    private val messageManager: MessageManager, private val senderManager: SenderManager):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MessageViewModel(chatListManager, messageManager, avengerAIManager, senderManager) as T
    }


}