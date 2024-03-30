package com.cogniheroid.framework.feature.imageai.ui.objectdetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class ObjectDetectionViewModelFactory(private val avengerAIManager: AvengerAIManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ObjectDetectionViewModel(avengerAIManager) as T
    }
}