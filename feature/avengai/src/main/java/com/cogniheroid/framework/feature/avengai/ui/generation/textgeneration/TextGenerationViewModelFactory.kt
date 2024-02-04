package com.cogniheroid.framework.feature.avengai.ui.textgeneration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class TextGenerationViewModelFactory(private val avengerAIManager: AvengerAIManager):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TextGenerationViewModel(avengerAIManager) as T
    }
}