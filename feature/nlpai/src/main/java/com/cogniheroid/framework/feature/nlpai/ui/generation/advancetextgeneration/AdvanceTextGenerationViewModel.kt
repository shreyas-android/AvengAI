package com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration.uistate.AdvanceTextGenerationUIEffect
import com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration.uistate.AdvanceTextGenerationUIEvent
import com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration.uistate.AdvanceTextGenerationUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdvanceTextGenerationViewModel(private val avengerAIManager:AvengerAIManager):ViewModel() {

    private val inputField = MutableStateFlow("")

    private var result = MutableStateFlow("")

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    private var imageListFlow:MutableStateFlow<List<Bitmap>> = MutableStateFlow(listOf())

    val advanceTextGenerationUIStateStateFlow = combine(inputField, result, isModelStartedGeneratingText, imageListFlow) { inputText, outputText, isGenerating, bitmapList ->
        AdvanceTextGenerationUIState(inputText, outputText, isGenerating, bitmapList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        AdvanceTextGenerationUIState("", "", false, listOf())
    )

    private val advanceTextGenerationUIEffectChannel = Channel<AdvanceTextGenerationUIEffect>()
    val advanceTextGenerationUIEffectFlow = advanceTextGenerationUIEffectChannel.receiveAsFlow()

    fun performIntent(textGenerationUIEvent: AdvanceTextGenerationUIEvent){
        when(textGenerationUIEvent){
            is AdvanceTextGenerationUIEvent.GenerateText -> {
                isModelStartedGeneratingText.value = true
                clearResult()
                generateTextAndUpdateResult(imageListFlow.value, textGenerationUIEvent.text,
                    textGenerationUIEvent.defaultErrorMessage)
            }
            is AdvanceTextGenerationUIEvent.InputText -> {
                inputField.value = textGenerationUIEvent.text
            }
            AdvanceTextGenerationUIEvent.ClearText -> {
                inputField.value = ""
            }

            is AdvanceTextGenerationUIEvent.RemoveImage -> {
                val mutableList = imageListFlow.value.toMutableList()
                mutableList.remove(textGenerationUIEvent.image)
                imageListFlow.value = mutableList

            }

            is AdvanceTextGenerationUIEvent.AddImage -> {
                textGenerationUIEvent.image?.let {
                    val  mutableList = imageListFlow.value.toMutableList()
                    mutableList.add(textGenerationUIEvent.image)
                    imageListFlow.value = mutableList
                }

            }

            is AdvanceTextGenerationUIEvent.OnOpenImagePicker -> {
                setSideEffect(AdvanceTextGenerationUIEffect.ShowImagePicker)
            }
        }
    }

    private fun generateTextAndUpdateResult(images: List<Bitmap>, text:String, defaultErrorMessage:String) {
        viewModelScope.launch {

            avengerAIManager.generateTextStreamContent(getModelInputList(images, text),
                defaultErrorMessage)
                .collectLatest {
                    result.value += it ?: ""
                    isModelStartedGeneratingText.value = false
                }
        }
    }

    private fun getModelInputList(images: List<Bitmap>, text:String):List<ModelInput>{
        val modelInputList = mutableListOf<ModelInput>()
        images.forEach {
            modelInputList.add(ModelInput.Image(true, it))
        }
        modelInputList.add(ModelInput.Text(true, text))
        return modelInputList
    }

    private fun clearResult(){
        result.value = ""
    }

    private fun setSideEffect(advanceTextGenerationUIEffect: AdvanceTextGenerationUIEffect){
        viewModelScope.launch {
            advanceTextGenerationUIEffectChannel.send(advanceTextGenerationUIEffect)
        }
    }
}