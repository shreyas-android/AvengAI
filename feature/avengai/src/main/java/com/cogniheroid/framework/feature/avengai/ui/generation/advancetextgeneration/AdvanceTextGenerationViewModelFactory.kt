package com.cogniheroid.framework.feature.avengai.ui.generation.advancetextgeneration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class AdvanceTextGenerationViewModelFactory(private val avengerAIManager: AvengerAIManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdvanceTextGenerationViewModel(avengerAIManager) as T
    }
}