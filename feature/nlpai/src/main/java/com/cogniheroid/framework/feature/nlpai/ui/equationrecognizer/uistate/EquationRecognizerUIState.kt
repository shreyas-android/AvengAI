package com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer.uistate

import android.graphics.Bitmap

data class EquationRecognizerUIState(
    val isFileUploaded:Boolean,
    val outputText:String,
    val isGenerating:Boolean,
    val image: Bitmap?,
){

    companion object{
        fun getDefault() : EquationRecognizerUIState {
            return EquationRecognizerUIState(false, "",false, null)
        }
    }
}