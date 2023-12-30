package com.cogniheroid.framework.feature.gemini.ui.textgeneration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAITextModel
import com.cogniheroid.framework.feature.gemini.ui.textgeneration.uistate.TextGenerationUIEvent
import com.cogniheroid.framework.feature.gemini.ui.textgeneration.uistate.TextGenerationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch





class TextGenerationViewModel(private val avengerAITextModel: AvengerAITextModel):ViewModel() {

    private val inputField = MutableStateFlow("")

    private var result = MutableStateFlow("")

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    val textGenerationUIStateFlow = combine(inputField, result, isModelStartedGeneratingText) { inputText, outputText, isGenerating ->
        TextGenerationUIState(inputText, outputText, isGenerating)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        TextGenerationUIState("", "", false)
    )


    fun performIntent(textGenerationUIEvent: TextGenerationUIEvent){
        when(textGenerationUIEvent){
            is TextGenerationUIEvent.GenerateText -> {
                isModelStartedGeneratingText.value = true
                clearResult()
                generateTextAndUpdateResult(textGenerationUIEvent.text)
            }
            is TextGenerationUIEvent.InputText -> {
                inputField.value = textGenerationUIEvent.text
            }
            is TextGenerationUIEvent.OutputText -> TODO()
            TextGenerationUIEvent.ClearText -> {
                inputField.value = ""
            }
        }
    }

    private fun generateTextAndUpdateResult(text:String) {
        viewModelScope.launch {
            avengerAITextModel.generateTextStreamContent(text = text).collectLatest {
                result.value += it ?: ""
                isModelStartedGeneratingText.value = false
            }
        }
    }

    private fun clearResult(){
        result.value = ""
    }

}