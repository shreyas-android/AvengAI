package com.cogniheroid.framework.feature.inspireai.ui.uistate

sealed class QuoteUIEvent {

    data class GenerateQuotes(val defaultErrorMessage:String): QuoteUIEvent()
}