package com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.uistate

import android.graphics.Bitmap

sealed class FoodRecipeUIEvent {
    data class InputText(val text: String) : FoodRecipeUIEvent()
    data class GenerateText(val text: String, val defaultErrorMessage:String) : FoodRecipeUIEvent()

    object ClearText : FoodRecipeUIEvent()

    object OnOpenImagePicker : FoodRecipeUIEvent()

    data class RemoveImage(val image: Bitmap) : FoodRecipeUIEvent()

    data class AddImage(val image: Bitmap?) : FoodRecipeUIEvent()
}