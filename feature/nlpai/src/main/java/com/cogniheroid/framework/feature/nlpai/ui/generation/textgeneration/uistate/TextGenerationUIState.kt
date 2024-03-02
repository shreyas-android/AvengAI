package com.cogniheroid.framework.feature.nlpai.ui.textgeneration.uistate

data class TextGenerationUIState(
    val inputText:String,
    val outputText:String,
    val isGenerating:Boolean
)