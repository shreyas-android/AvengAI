package com.cogniheroid.android

import android.os.Bundle
import androidx.activity.compose.setContent
import com.cogniheroid.framework.feature.chat.ChatScreen


class ChatWithAIActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatScreen()
        }

    }
}
