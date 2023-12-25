package com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.feature.gemini.CogniHeroidAICore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AdvanceTextGenerationUIState(
    val inputText:String,
    val outputText:String,
    val isGenerating:Boolean,
    val bitmaps: List<Bitmap>,
)

sealed class AdvanceTextGenerationUIEvent {
    data class InputText(val text: String) : AdvanceTextGenerationUIEvent()
    data class OutputText(val text: String) : AdvanceTextGenerationUIEvent()
    data class GenerateText(val text: String) : AdvanceTextGenerationUIEvent()

    object ClearText : AdvanceTextGenerationUIEvent()

    object OnOpenImagePicker : AdvanceTextGenerationUIEvent()

    data class RemoveImage(val image: Bitmap) : AdvanceTextGenerationUIEvent()

    data class AddImage(val image: Bitmap?) : AdvanceTextGenerationUIEvent()
}

sealed class AdvanceTextGenerationUIEffect {
   object ShowImagePicker : AdvanceTextGenerationUIEffect()
}


class AdvanceTextGenerationViewModel:ViewModel() {

    private val avengerAITextModel = CogniHeroidAICore.avengerAITextModel

    private val inputField = MutableStateFlow("")

    private var result = MutableStateFlow("")

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    private var imageListFlow:MutableStateFlow<List<Bitmap>> = MutableStateFlow(listOf())

    val advanceTextGenerationUIStateFlow = combine(inputField, result, isModelStartedGeneratingText, imageListFlow) { inputText, outputText, isGenerating, bitmapList ->
        AdvanceTextGenerationUIState(inputText, outputText, isGenerating, bitmapList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        AdvanceTextGenerationUIState("", "", false, listOf())
    )

    private val _advanceTextGenerationUIEffectFlow = Channel<AdvanceTextGenerationUIEffect>()
    val advanceTextGenerationUIEffectFlow = _advanceTextGenerationUIEffectFlow.receiveAsFlow()


    fun performIntent(textGenerationUIEvent: AdvanceTextGenerationUIEvent){
        when(textGenerationUIEvent){
            is AdvanceTextGenerationUIEvent.GenerateText -> {
                isModelStartedGeneratingText.value = true
                clearResult()
                generateTextAndUpdateResult(imageListFlow.value, textGenerationUIEvent.text)
            }
            is AdvanceTextGenerationUIEvent.InputText -> {
                inputField.value = textGenerationUIEvent.text
            }
            is AdvanceTextGenerationUIEvent.OutputText -> TODO()
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

    fun generateTextAndUpdateResult(images: List<Bitmap>, text:String) {
        viewModelScope.launch {
            avengerAITextModel?.generateTextStreamContent(imageInputList = images,
                text = text)?.collectLatest {
                result.value += it ?: ""
                isModelStartedGeneratingText.value = false
            }
        }
    }

    fun clearResult(){
        result.value = ""
    }

    fun setSideEffect(advanceTextGenerationUIEffect: AdvanceTextGenerationUIEffect){
        viewModelScope.launch {
            _advanceTextGenerationUIEffectFlow.send(advanceTextGenerationUIEffect)
        }
    }
}