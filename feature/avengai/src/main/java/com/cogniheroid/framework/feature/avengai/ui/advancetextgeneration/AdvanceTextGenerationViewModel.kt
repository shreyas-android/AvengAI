package com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAITextModel
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIEffect
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIEvent
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch








class AdvanceTextGenerationViewModel(private val avengerAITextModel:AvengerAITextModel):ViewModel() {

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


    init {
        Log.d("CHECKVIEWMODEL","CHEKCI THE VIEWMODEL init = $")
    }
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

    private fun generateTextAndUpdateResult(images: List<Bitmap>, text:String) {
        viewModelScope.launch {
            avengerAITextModel?.generateTextStreamContent(imageInputList = images,
                text = text)?.collectLatest {
                result.value += it ?: ""
                isModelStartedGeneratingText.value = false
            }
        }
    }

    private fun clearResult(){
        result.value = ""
    }

    private fun setSideEffect(advanceTextGenerationUIEffect: AdvanceTextGenerationUIEffect){
        viewModelScope.launch {
            _advanceTextGenerationUIEffectFlow.send(advanceTextGenerationUIEffect)
        }
    }
}