package com.cogniheroid.framework.shared.core.chat

import android.content.Context

object AndroidChatDomainCore {

    fun init(context: Context){
        val databaseDriverFactory = DatabaseDriverFactory(context)
        ChatDomainCore.init(databaseDriverFactory)
    }

}