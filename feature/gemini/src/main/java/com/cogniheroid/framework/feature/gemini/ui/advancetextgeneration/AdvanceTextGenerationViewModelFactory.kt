package com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAITextModel

class AdvanceTextGenerationViewModelFactory(private val avengerAITextModel: AvengerAITextModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdvanceTextGenerationViewModel(avengerAITextModel) as T
    }
}