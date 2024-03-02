package com.cogniheroid.framework.feature.nlpai.ui.textgeneration.uistate

sealed class TextGenerationUIEvent {
    data class InputText(val text:String): TextGenerationUIEvent()
    data class GenerateText(val text:String, val defaultErrorMessage:String): TextGenerationUIEvent()

    object ClearText: TextGenerationUIEvent()
}