package com.cogniheroid.framework.feature.chat

import android.content.Context
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.shared.core.chat.AndroidChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity

object ChatCore {

    lateinit var avengerAIManager: AvengerAIManager

    fun init(apiKey:String, context: Context){
        AndroidChatDomainCore.init(context)
        avengerAIManager = AvengerAIManager.getInstance(apiKey)
    }
}