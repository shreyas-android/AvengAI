package com.cogniheroid.framework.shared.core.chat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform