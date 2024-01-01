package com.cogniheroid.framework.feature.chat

import androidx.lifecycle.ViewModel
import com.cogniheroid.framework.feature.chat.data.model.ChatDetailItem
import com.cogniheroid.framework.feature.chat.data.model.MessageItem
import com.cogniheroid.framework.feature.chat.data.model.ChatListItem

class ChatViewModel: ViewModel() {

    private var selectedChatListItem:ChatListItem? = null

    fun getSelectedChatListItem(): ChatListItem? {
        return selectedChatListItem
    }

    fun updateSelectedChatListItem(chatListItem: ChatListItem) {
        selectedChatListItem = chatListItem
    }

    fun getChatDetailMap(messageItems:List<MessageItem>): Map<String, List<ChatDetailItem>> {
        val mappedChatDetailItems = messageItems.map {
            if (it.replyId != null){
                ChatDetailItem.ReplyMessage(it.messageStartDate, it)
            }else{
                ChatDetailItem.NormalMessage(it.messageStartDate, it)
            }
        }
       return mappedChatDetailItems.groupBy { it.messageStartDate }


    }
}