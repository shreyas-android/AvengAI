package com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.uistate

import android.graphics.Bitmap

data class FoodRecipeUIState(
    val inputText:String,
    val outputText:String,
    val isGenerating:Boolean,
    val bitmaps: List<Bitmap>,
)