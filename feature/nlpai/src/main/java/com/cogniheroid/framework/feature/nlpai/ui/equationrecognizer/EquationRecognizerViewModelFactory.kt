package com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class EquationRecognizerViewModelFactory(private val avengerAIManager: AvengerAIManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EquationRecognizerViewModel(avengerAIManager) as T
    }
}