package com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration.uistate

import android.graphics.Bitmap

data class AdvanceTextGenerationUIState(
    val inputText:String,
    val outputText:String,
    val isGenerating:Boolean,
    val bitmaps: List<Bitmap>,
)