package com.cogniheroid.framework.feature.avengai.ui.nutrient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.feature.avengai.ui.generation.advancetextgeneration.AdvanceTextGenerationViewModel

class NutrientAIViewModelFactory(private val avengerAIManager: AvengerAIManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NutrientAIViewModel(avengerAIManager) as T
    }
}