package com.cogniheroid.framework.feature.chat.ui.conversations

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.chat.callback.ExternalTextCallback
import com.cogniheroid.framework.feature.chat.data.model.ChatDetailItem
import com.cogniheroid.framework.feature.chat.data.model.ConversationMessage
import com.cogniheroid.framework.feature.chat.ui.conversations.uistate.MessageEvent
import com.cogniheroid.framework.feature.chat.ui.conversations.uistate.MessageSideEffect
import com.cogniheroid.framework.feature.chat.utils.DisplayUtils
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageType
import com.cogniheroid.framework.shared.core.chat.data.enum.ReadStatusType
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.manager.message.MessageManager
import com.cogniheroid.framework.shared.core.chat.manager.sender.SenderManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MessageViewModel(
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
                    ConversationMessage.Messages(mappedChatDetailItems.groupBy { it.messageStartDate })
            }
        }
    }


    private fun getConversationMessages(chatListItem: ConversationItem): CommonFlow<List<MessageWithSender>> {
        this.chatListItem = chatListItem
        return messageManager.getMessages(chatListItem.id)
    }


    fun updateChatDetailMap(chatListItem: ConversationItem) {
        viewModelScope.launch {
            getConversationMessages(chatListItem).collectLatest { it ->
                _allMessages.value = it
            }
        }
    }

    private suspend fun convertAllMessagesToModelInput(context: Context): List<ModelInput> {

        val modelInputList = mutableListOf<ModelInput>()

        _allMessages.value.forEach {
            when (it.messageContentType) {
                MessageContentType.TEXT -> {
                    it.message?.let { message ->
                        modelInputList.add(ModelInput.Text(message))
                    }
                }

                MessageContentType.IMAGE -> {
                    it.fileUri?.let { uri ->
                        val bitmap = DisplayUtils.getBitmap(context, uri)
                        bitmap?.let {
                            modelInputList.add(ModelInput.Image(it))
                        }

                    }
                }

                else -> {
                    it.message?.let { message ->
                        modelInputList.add(ModelInput.Text(message))
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
                    insertNewMessage(messageEvent.message)

                    avengerAIManager.generateConversation(
                        modelInputHistory = convertAllMessagesToModelInput(messageEvent.context),
                        modelInput = ModelInput.Text(messageEvent.message)
                    ).collectLatest {
                        insertNewMessage(it ?: "")
                    }
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


        }
    }

    private fun setSideEffect(messageSideEffect: MessageSideEffect) {
        viewModelScope.launch {
            _messageSideEffect.send(messageSideEffect)
        }
    }


    private fun insertNewMessage(message: String) {
        viewModelScope.launch {
            messageManager.insertAndGetNewMessage(
                chatId = chatListItem.id,
                senderId = 1,
                message = message,
                fileUri = null,
                messageTime = System.currentTimeMillis(),
                readStatus = ReadStatusType.READ,
                messageType = MessageType.NORMAL,
                replyMessageId = null,
                messageContentType = MessageContentType.TEXT
            )
        }
    }

    fun insertListOfNewAttachment(uriList: List<Uri>) {
        uriList.forEach {
            insertNewAttachment(it.toString())
        }

    }

    private fun insertNewAttachment(uri: String) {
        Log.d("CHECKTHEATTACH","CHEKCIG THE ATTACH = $uri")
        viewModelScope.launch {
            messageManager.insertAndGetNewMessage(
                chatId = chatListItem.id,
                senderId = 1,
                message = null,
                fileUri = uri,
                messageTime = System.currentTimeMillis(),
                readStatus = ReadStatusType.READ,
                messageType = MessageType.NORMAL,
                replyMessageId = null,
                messageContentType = MessageContentType.IMAGE
            )
        }
    }
}