package com.cogniheroid.framework.feature.avengai.ui.textgeneration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.avengai.ui.textgeneration.uistate.TextGenerationUIEvent
import com.cogniheroid.framework.feature.avengai.ui.textgeneration.uistate.TextGenerationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TextGenerationViewModel(private val avengerAIManager: AvengerAIManager):ViewModel() {

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
                generateTextAndUpdateResult(textGenerationUIEvent.text,
                    defaultErrorMessage = textGenerationUIEvent.defaultErrorMessage)
            }
            is TextGenerationUIEvent.InputText -> {
                inputField.value = textGenerationUIEvent.text
            }
            TextGenerationUIEvent.ClearText -> {
                inputField.value = ""
            }
        }
    }

    private fun generateTextAndUpdateResult(text:String, defaultErrorMessage:String) {
        viewModelScope.launch {
            val modelInput = ModelInput.Text(text)
            avengerAIManager.generateTextStreamContent(listOf( modelInput), defaultErrorMessage).collectLatest {
                result.value += it ?: ""
                isModelStartedGeneratingText.value = false
            }
        }
    }

    private fun clearResult(){
        result.value = ""
    }

}