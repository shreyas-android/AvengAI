package com.cogniheroid.framework.feature.avengai.ui.equationrecognizer

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.avengai.ui.equationrecognizer.uistate.EquationRecognizerUIEffect
import com.cogniheroid.framework.feature.avengai.ui.equationrecognizer.uistate.EquationRecognizerUIEvent
import com.cogniheroid.framework.feature.avengai.ui.equationrecognizer.uistate.EquationRecognizerUIState
import com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate.NutrientUIEffect
import com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate.NutrientUIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EquationRecognizerViewModel(private val avengerAIManager : AvengerAIManager) : ViewModel() {

    private var imageFlow : MutableStateFlow<Bitmap?> = MutableStateFlow(null)

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    private var result = MutableStateFlow("")

    val equationRecognizerUIStateFlow =
        combine(imageFlow, result, isModelStartedGeneratingText) { image, result, isGenerating ->
            EquationRecognizerUIState(image != null, result, isGenerating, image)
        }

    private val _nutrientUIEffectChannel = Channel<EquationRecognizerUIEffect>()
    val equationRecognizerUIEffectFlow = _nutrientUIEffectChannel.receiveAsFlow()
    fun performIntent(equationRecognizerUIEvent : EquationRecognizerUIEvent) {
        when(equationRecognizerUIEvent) {
            is EquationRecognizerUIEvent.AddImage -> {
                imageFlow.value = equationRecognizerUIEvent.image
            }

            is EquationRecognizerUIEvent.GenerateText -> {
                isModelStartedGeneratingText.value = true
                clearResult()

                equationRecognizerUIEvent.image?.let {
                    generateTextAndUpdateResult(
                        it, getDefaultPrompt(), equationRecognizerUIEvent.defaultErrorMessage)
                }

            }

            EquationRecognizerUIEvent.OnOpenImagePicker -> {
                setSideEffects(EquationRecognizerUIEffect.UploadImagePicker)
            }

            is EquationRecognizerUIEvent.RemoveImage -> {
                imageFlow.value = null
            }
        }
    }

    private fun generateTextAndUpdateResult(
            image : Bitmap, text : String, defaultErrorMessage : String) {
        viewModelScope.launch {
            avengerAIManager
                .generateTextStreamContent(getModelInputList(image, text), defaultErrorMessage)
                .collectLatest {
                    result.value += it
                        ?: ""
                    isModelStartedGeneratingText.value = false
                }
        }
    }

    private fun setSideEffects(equationRecognizerUIEffect : EquationRecognizerUIEffect) {
        viewModelScope.launch {
            _nutrientUIEffectChannel.send(equationRecognizerUIEffect)
        }
    }

    private fun getModelInputList(images : Bitmap, text : String) : List<ModelInput> {
        val modelInputList = mutableListOf<ModelInput>()
        images.let {
            modelInputList.add(ModelInput.Image(true, it))
        }
        modelInputList.add(ModelInput.Text(true, text))
        return modelInputList
    }

    private fun clearResult() {
        result.value = ""
    }

    private fun getDefaultPrompt() : String {
        return """
             You are an expert in equation analyzer where you need to accurately see the equation from the image , identify the equation in the image accurately 
          and  explain the equations, what is the name of the equation, founder and founder year. Your data should be more accurate. It should match the value in internet.
           Also provide the details of every equation as below format.  Please follow the below format for generated text
           
           Equation 1 
           
           
           Equation name - 
           
           Equation founder -
                      
           Equation founded year -
           
           Description of equation - 
           
           Example with real world example - 
           
          
          ---
       """.trimIndent()

    }
}