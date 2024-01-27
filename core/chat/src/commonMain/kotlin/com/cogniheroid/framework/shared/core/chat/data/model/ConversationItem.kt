package com.cogniheroid.framework.shared.core.chat.data.model

data class ConversationItem(val id:Long, val title:String, val imageUri:String?,
                            val lastMessage:String?, val lastMessageTime:Long?, val unreadCount:Int)