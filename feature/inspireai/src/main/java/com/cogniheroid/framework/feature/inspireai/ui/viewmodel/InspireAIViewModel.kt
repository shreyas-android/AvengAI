package com.cogniheroid.framework.feature.inspireai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.inspireai.ui.uistate.QuoteUIEvent
import com.cogniheroid.framework.feature.inspireai.ui.uistate.QuotesUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InspireAIViewModel(private val avengerAIManager : AvengerAIManager) : ViewModel() {


    private var result = MutableStateFlow("")

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    private val _currentQuote = combine(result, isModelStartedGeneratingText,){outputText, isGenerating ->
        QuotesUIState(outputText, isGenerating)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        QuotesUIState("", false)
    )
    val currentQuote: StateFlow<QuotesUIState>
        get() = _currentQuote

    private fun generateTextAndUpdateResult(text:String, defaultErrorMessage:String) {
        viewModelScope.launch {
            val modelInput = ModelInput.Text(true, text)
            avengerAIManager.generateTextStreamContent(listOf( modelInput),
                defaultErrorMessage).collectLatest {
                result.value += it ?: ""
                isModelStartedGeneratingText.value = false
            }
        }
    }

    fun performIntent(quoteUIEvent: QuoteUIEvent){
        when(quoteUIEvent){
           is QuoteUIEvent.GenerateQuotes -> {
                isModelStartedGeneratingText.value = true
                clearResult()
                generateTextAndUpdateResult(getDefaultPrompt(),
                    quoteUIEvent.defaultErrorMessage)
            }
        }

    }

    private fun clearResult(){
        result.value = ""
    }

    private fun getDefaultPrompt() : String {
        return """
            Can you please generate me one simple quotes which is created by Artificial 
            general intelligence. The quotes should be in simple english.
       """.trimIndent()

    }

    // Can you please generate me one food related quotes to live a healthy life which is created by Artificial
    //            general intelligence. Do not repeat the quotes.
    //             The quotes should be in simple english.
}
