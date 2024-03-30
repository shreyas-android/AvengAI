package com.cogniheroid.framework.core.ai.data

sealed class RemoteResult<out T> {

    data class Success<T>(val data:T):RemoteResult<T>()

    data class Failure(val errorMessage:String):RemoteResult<Nothing>()
}