package com.cogniheroid.framework.feature.avengai.ui.textgeneration.uistate

sealed class TextGenerationUIEvent {
    data class InputText(val text:String): TextGenerationUIEvent()
    data class OutputText(val text:String): TextGenerationUIEvent()
    data class GenerateText(val text:String): TextGenerationUIEvent()

    object ClearText: TextGenerationUIEvent()
}