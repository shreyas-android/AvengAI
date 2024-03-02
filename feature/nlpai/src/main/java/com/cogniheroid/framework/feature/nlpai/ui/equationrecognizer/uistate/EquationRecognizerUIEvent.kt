package com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer.uistate

import android.graphics.Bitmap

sealed class EquationRecognizerUIEvent {
    data class GenerateText(val image: Bitmap?, val defaultErrorMessage:String) : EquationRecognizerUIEvent()

    object OnOpenImagePicker : EquationRecognizerUIEvent()

    data class RemoveImage(val image: Bitmap) : EquationRecognizerUIEvent()

    data class AddImage(val image: Bitmap?) : EquationRecognizerUIEvent()
}