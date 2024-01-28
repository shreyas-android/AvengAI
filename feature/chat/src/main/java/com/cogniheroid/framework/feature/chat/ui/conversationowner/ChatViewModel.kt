package com.cogniheroid.framework.feature.chat.ui.conversationowner

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManager
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatListManager: ChatListManager,
    private val  senderManager: SenderManager
) : ViewModel() {

    init {
        val userSenderEntity = SenderEntity(1L, "User", null,
            true)


        val geminiSenderEntity = SenderEntity(2L, "Gemini AI",
            null,
            false)

        viewModelScope.launch {
            senderManager.insertSender(userSenderEntity)
            senderManager.insertSender(geminiSenderEntity)
        }

    }

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