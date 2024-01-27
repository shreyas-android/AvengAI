package com.cogniheroid.framework.feature.chat.ui.conversations.uistate

import com.cogniheroid.framework.feature.chat.callback.ExternalTextCallback

sealed class MessageSideEffect {

   data class OnAddAttachment(val externalTextCallback: ExternalTextCallback):MessageSideEffect()
}