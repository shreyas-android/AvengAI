package com.cogniheroid.framework.shared.core.chat.data.enum

enum class MessageContentType(val value:Int) {
    UNKNOWN(0),
    TEXT(1),
    IMAGE(2),
    VIDEO(3),
    AUDIO(4),
    DOCUMENT(5),
    LOCATION(6),
    CONTACT(7),
    STICKER(8),
    LINK(9),
}