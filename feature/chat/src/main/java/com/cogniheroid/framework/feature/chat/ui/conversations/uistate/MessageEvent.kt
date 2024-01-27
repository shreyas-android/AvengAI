package com.cogniheroid.framework.feature.chat.ui.conversations.uistate

import android.content.Context
import com.cogniheroid.framework.feature.chat.callback.ExternalTextCallback

sealed class MessageEvent {

    data class OnSendMessageEvent(val context: Context, val messageTime:Long, val message:String): MessageEvent()

    object OnAddAttachmentClicked:MessageEvent()

}