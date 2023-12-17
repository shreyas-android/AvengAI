package com.cogniheroid.framework.feature.gemini.textgeneration

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
    val outputText:String
)

sealed class TextGenerationUIEvent(){
    data class InputText(val text:String):TextGenerationUIEvent()
    data class OutputText(val text:String):TextGenerationUIEvent()
    data class GenerateText(val text:String):TextGenerationUIEvent()
}

class TextGenerationViewModel:ViewModel() {

    private val avengerAITextModel = CogniHeroidAICore.avengerAITextModel

    private val inputField = MutableStateFlow("")

    private var result = MutableStateFlow("")

    val textGenerationUIStateFlow = combine(inputField, result) { inputText, outputText ->
        TextGenerationUIState(inputText, outputText)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        TextGenerationUIState("", ""))


    fun performIntent(textGenerationUIEvent: TextGenerationUIEvent){
        when(textGenerationUIEvent){
            is TextGenerationUIEvent.GenerateText -> {
                viewModelScope.launch {
                   /* avengerAITextModel?.generateTextStream(textGenerationUIEvent.text)?.collectLatest {
                        result.value += it.text?:""
                    }*/
                }
            }
            is TextGenerationUIEvent.InputText -> {
                inputField.value = textGenerationUIEvent.text
            }
            is TextGenerationUIEvent.OutputText -> TODO()
        }
    }

}