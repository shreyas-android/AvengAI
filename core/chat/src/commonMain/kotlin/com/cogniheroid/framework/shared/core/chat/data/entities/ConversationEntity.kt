package com.cogniheroid.framework.shared.core.chat.data.entities

data class ConversationEntity(val id:Long, val title:String, val imageUri:String?,
                              val lastMessageId:Long?, val unreadCount:Int)