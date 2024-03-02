package com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration.uistate

import android.graphics.Bitmap

sealed class AdvanceTextGenerationUIEvent {
    data class InputText(val text: String) : AdvanceTextGenerationUIEvent()
    data class GenerateText(val text: String, val defaultErrorMessage:String) : AdvanceTextGenerationUIEvent()

    object ClearText : AdvanceTextGenerationUIEvent()

    object OnOpenImagePicker : AdvanceTextGenerationUIEvent()

    data class RemoveImage(val image: Bitmap) : AdvanceTextGenerationUIEvent()

    data class AddImage(val image: Bitmap?) : AdvanceTextGenerationUIEvent()
}