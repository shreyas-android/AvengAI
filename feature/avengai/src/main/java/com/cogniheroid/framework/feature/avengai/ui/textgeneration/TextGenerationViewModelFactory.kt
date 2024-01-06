package com.cogniheroid.framework.feature.avengai.ui.textgeneration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAITextModel
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.AdvanceTextGenerationViewModel

class TextGenerationViewModelFactory(private val avengerAITextModel: AvengerAITextModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TextGenerationViewModel(avengerAITextModel) as T
    }
}