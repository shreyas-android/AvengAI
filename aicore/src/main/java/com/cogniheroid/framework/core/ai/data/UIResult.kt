package com.cogniheroid.framework.core.ai.data

sealed class UIResult<out T> {

    object Loading : UIResult<Nothing>()

    data class Success<T>(val data: T) : UIResult<T>()

    data class Error(val message:String) : UIResult<Nothing>()
}