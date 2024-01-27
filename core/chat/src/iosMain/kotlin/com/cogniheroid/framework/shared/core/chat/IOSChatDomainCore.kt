package com.cogniheroid.framework.shared.core.chat

object IOSChatDomainCore {

    fun init(){
        val databaseDriverFactory = DatabaseDriverFactory()
        ChatDomainCore.init(databaseDriverFactory)
    }
}