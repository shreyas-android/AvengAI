package com.cogniheroid.framework.feature.gemini.ui.textgeneration

import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.feature.gemini.CogniHeroidAICore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TextGenerationUIState(
    val inputText:String,
    val outputText:String,
    val isGenerating:Boolean
)

sealed class TextGenerationUIEvent(){
    data class InputText(val text:String): TextGenerationUIEvent()
    data class OutputText(val text:String): TextGenerationUIEvent()
    data class GenerateText(val text:String): TextGenerationUIEvent()

    object ClearText: TextGenerationUIEvent()
}

class TextGenerationViewModel:ViewModel() {

    private val avengerAITextModel = CogniHeroidAICore.avengerAITextModel

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

    fun generateTextAndUpdateResult(text:String) {
        viewModelScope.launch {
            avengerAITextModel?.generateTextStreamContent(text = text)?.collectLatest {
                result.value += it ?: ""
                isModelStartedGeneratingText.value = false
            }
        }
    }

    fun clearResult(){
        result.value = ""
    }

}