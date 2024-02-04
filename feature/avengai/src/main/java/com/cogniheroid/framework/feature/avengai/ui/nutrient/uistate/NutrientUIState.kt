package com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate

import android.graphics.Bitmap

data class NutrientUIState(
    val isFileUploaded:Boolean,
    val outputText:String,
    val isGenerating:Boolean,
    val image: Bitmap?,
){

    companion object{
        fun getDefault() : NutrientUIState {
            return NutrientUIState(false, "",false, null)
        }
    }
}