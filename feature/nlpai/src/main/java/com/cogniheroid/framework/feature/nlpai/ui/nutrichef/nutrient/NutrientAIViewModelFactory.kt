package com.cogniheroid.framework.feature.nlpai.ui.nutrichef.nutrient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class NutrientAIViewModelFactory(private val avengerAIManager: AvengerAIManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NutrientAIViewModel(avengerAIManager) as T
    }
}