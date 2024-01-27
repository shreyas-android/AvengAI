package com.cogniheroid.framework.feature.chat.ui.conversationowner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManager
import com.cogniheroid.framework.shared.core.chat.manager.message.MessageManager
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManager

class ConversationViewModelFactory(private val chatListManager: ChatListManager) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(chatListManager) as T
    }
}