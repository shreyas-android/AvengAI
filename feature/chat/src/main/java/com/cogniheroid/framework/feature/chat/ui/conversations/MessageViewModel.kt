package com.cogniheroid.framework.feature.chat.ui.conversations

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.chat.callback.ExternalTextCallback
import com.cogniheroid.framework.feature.chat.data.model.ChatDetailItem
import com.cogniheroid.framework.feature.chat.data.model.ConversationMessage
import com.cogniheroid.framework.feature.chat.ui.conversations.uistate.MessageEvent
import com.cogniheroid.framework.feature.chat.ui.conversations.uistate.MessageSideEffect
import com.cogniheroid.framework.feature.chat.utils.DisplayUtils
import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageType
import com.cogniheroid.framework.shared.core.chat.data.enum.ReadStatusType
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.manager.chatlist.ChatListManager
import com.cogniheroid.framework.shared.core.chat.manager.message.MessageManager
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MessageViewModel(
    private val chatListManager: ChatListManager,
    private val messageManager: MessageManager,
    private val avengerAIManager: AvengerAIManager,
    private val senderManager: SenderManager
) : ViewModel() {

    private val _messageSideEffect = Channel<MessageSideEffect>()
    val messageSideEffect = _messageSideEffect.receiveAsFlow()

    val conversationMessages: MutableStateFlow<ConversationMessage> = MutableStateFlow(
        ConversationMessage.Loading
    )

    private lateinit var chatListItem: ConversationItem

    private val _allMessages = MutableStateFlow<List<MessageWithSender>>(listOf())

    private val _generativeAIChatFlow = MutableStateFlow<String>("")

    init {
        viewModelScope.launch {
            _allMessages.collectLatest {
                val mappedChatDetailItems = it.map { messageWithSender ->
                    if (messageWithSender.replyMessageId != null) {
                        ChatDetailItem.ReplyMessage(
                            messageWithSender.messageStartDate,
                            messageWithSender
                        )
                    } else {
                        ChatDetailItem.NormalMessage(
                            messageWithSender.messageStartDate,
                            messageWithSender
                        )
                    }
                }

                conversationMessages.value =
                    ConversationMessage.Messages(mappedChatDetailItems.groupBy {
                        Log.d("CHECKMESSAGE","CHEKCIGN THE MESSAGE = ${it.messageStartDate}")
                        it.messageStartDate })
            }
        }
    }


    private fun getConversationMessages(chatListItem: ConversationItem): CommonFlow<List<MessageWithSender>> {
        this.chatListItem = chatListItem
        return messageManager.getMessages(chatListItem.id)
    }


    fun updateChatDetailMap(chatListItem: ConversationItem) {
        viewModelScope.launch {
            getConversationMessages(chatListItem).collectLatest {
                _allMessages.value = it
            }
        }
    }

    private suspend fun convertAllMessagesToModelInput(context: Context): List<ModelInput> {

        val modelInputList = mutableListOf<ModelInput>()

        _allMessages.value.forEach { messageWithSender ->
            when (messageWithSender.messageContentType) {
                MessageContentType.TEXT -> {
                    messageWithSender.message?.let { message ->
                        modelInputList.add(ModelInput.Text(isUser = messageWithSender.isUser, message))
                    }
                }

                MessageContentType.IMAGE -> {
                    messageWithSender.fileUri?.let { uri ->
                        val bitmap = DisplayUtils.getBitmap(context, uri)
                        bitmap?.let {
                            modelInputList.add(ModelInput.Image(isUser = messageWithSender.isUser,
                                it))
                        }

                    }
                }

                else -> {
                    messageWithSender.message?.let { message ->
                        modelInputList.add(ModelInput.Text(isUser = messageWithSender.isUser,
                            message))
                    }
                }
            }
        }

        return modelInputList
    }

    fun performEvent(messageEvent: MessageEvent) {
        when (messageEvent) {
            is MessageEvent.OnSendMessageEvent -> {
                viewModelScope.launch {
                    onUserSentMessage(messageEvent.context, messageEvent.message, messageEvent
                        .defaultErrorMessage, messageEvent.geminiInitialMessage)
                }
            }

            is MessageEvent.OnAddAttachmentClicked -> {
                val externalTextCallback = object : ExternalTextCallback {
                    override fun onAttachmentAdded(context: Context, fileUriList: List<Uri>) {
                        insertListOfNewAttachment(fileUriList)
                    }
                }

                setSideEffect(MessageSideEffect.OnAddAttachment(externalTextCallback))
            }

            is MessageEvent.OnTitleChanged -> {
                viewModelScope.launch {
                    chatListManager.updateChatListItemTitle(messageEvent.title, chatListItem.id)
                }
            }
        }
    }

    private fun setSideEffect(messageSideEffect: MessageSideEffect) {
        viewModelScope.launch {
            _messageSideEffect.send(messageSideEffect)
        }
    }

   private suspend fun onUserSentMessage(context: Context, message: String, defaultErrorMessage:String, geminiInitialMessage:String){
        insertNewMessage(message, 1L)

        val geminiMessage =  insertNewMessage(geminiInitialMessage, 2L)
        avengerAIManager.generateConversation(
            modelInputHistory = convertAllMessagesToModelInput(context),
            modelInput = ModelInput.Text(isUser = true, message), defaultErrorMessage).collectLatest {
            updateMessage(geminiMessage.copy(message = it?:""))
        }
    }

    private suspend fun updateMessage(messagesEntity: MessagesEntity){
        messageManager.updateMessage(messagesEntity)
    }

    private suspend fun insertNewMessage(message: String, senderId:Long):MessagesEntity {
           return insertNewMessageEntity(message, null, MessageContentType.TEXT,  senderId)

    }

    private suspend fun insertNewMessageEntity(message: String?, uri:String?, contentType: MessageContentType, senderId:Long): MessagesEntity {
       val messageEntity =  messageManager.insertAndGetNewMessage(
            chatId = chatListItem.id,
            senderId = senderId,
            message = message,
            fileUri = uri,
            messageTime = System.currentTimeMillis(),
            readStatus = ReadStatusType.READ,
            messageType = MessageType.NORMAL,
            replyMessageId = null,
            messageContentType = contentType
        )

        chatListManager.updateChatListItemLastMessageId(messageEntity.messageId, chatListItem.id)

        return messageEntity
    }

    fun insertListOfNewAttachment(uriList: List<Uri>) {
        uriList.forEach {
            insertNewAttachment(it.toString(), 1L)
        }

    }

    private fun insertNewAttachment(uri: String, senderId: Long) {
        viewModelScope.launch {
            insertNewMessageEntity(null, uri,  MessageContentType.IMAGE, senderId)
        }
    }
}