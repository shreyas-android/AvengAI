package com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class FoodRecipeViewModelFactory(private val avengerAIManager: AvengerAIManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FoodRecipeViewModel(avengerAIManager) as T
    }
}