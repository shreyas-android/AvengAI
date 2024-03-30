package com.cogniheroid.framework.feature.inspireai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.framework.core.ai.AvengerAIManager

class InspireAIViewModelFactory(private val avengerAIManager : AvengerAIManager) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(InspireAIViewModel::class.java)) {
      @Suppress("unchecked_cast")
      return InspireAIViewModel(avengerAIManager) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
  }
}