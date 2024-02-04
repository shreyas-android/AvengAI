package com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate

import android.graphics.Bitmap

sealed class NutrientUIEvent {
    data class GenerateText(val image: Bitmap?, val defaultErrorMessage:String) : NutrientUIEvent()

    object OnOpenImagePicker : NutrientUIEvent()

    data class RemoveImage(val image: Bitmap) : NutrientUIEvent()

    data class AddImage(val image: Bitmap?) : NutrientUIEvent()
}