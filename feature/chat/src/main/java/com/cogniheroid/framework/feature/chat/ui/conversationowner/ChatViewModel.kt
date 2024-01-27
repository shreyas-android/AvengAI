package com.cogniheroid.framework.feature.chat.ui.conversationowner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatListManager: ChatListManager
) : ViewModel() {

    private var selectedChatListItem: ConversationItem? = null

    fun getChatListItems() = chatListManager.getConversationItems()

    fun getSelectedChatListItem(): ConversationItem? {
        return selectedChatListItem
    }

    fun updateSelectedChatListItem(chatListItem: ConversationItem) {
        selectedChatListItem = chatListItem
    }

   suspend fun getNewChatListItem(title:String): ConversationItem {
       return viewModelScope.async {
           chatListManager.insertAndGetNewConversationItem(title, null)
       }.await()
    }

}