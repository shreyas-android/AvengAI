package com.cogniheroid.framework.feature.imageai.ui.objectdetection

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.UIResult
import com.cogniheroid.framework.core.ai.data.model.ObjectDetectionInfo
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate.ObjectDetectionUIEffect
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate.ObjectDetectionUIEvent
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate.ObjectDetectionUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ObjectDetectionViewModel(private val avengerAIManager : AvengerAIManager) : ViewModel() {

    private var imageFlow : MutableStateFlow<Bitmap?> = MutableStateFlow(null)

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    private var result:MutableStateFlow<UIResult<List<ObjectDetectionInfo>>?> = MutableStateFlow(null)

    val objectDetectionUIStateFlow =
        combine(imageFlow, result, isModelStartedGeneratingText) { image, result, isGenerating ->
            ObjectDetectionUIState(image != null, result, isGenerating, image)
        }

    private val _nutrientUIEffectChannel = Channel<ObjectDetectionUIEffect>()
    val objectDetectionUIEffectFlow = _nutrientUIEffectChannel.receiveAsFlow()
    fun performIntent(equationRecognizerUIEvent : ObjectDetectionUIEvent) {
        when(equationRecognizerUIEvent) {
            is ObjectDetectionUIEvent.AddImage -> {
                imageFlow.value = equationRecognizerUIEvent.image
            }

            is ObjectDetectionUIEvent.DetectObject -> {
                isModelStartedGeneratingText.value = true
                clearResult()

                detectObject(
                    equationRecognizerUIEvent.image, equationRecognizerUIEvent.defaultErrorMessage)

            }

            ObjectDetectionUIEvent.OnOpenImagePicker -> {
                setSideEffects(ObjectDetectionUIEffect.UploadImagePicker)
            }

            is ObjectDetectionUIEvent.RemoveImage -> {
                imageFlow.value = null
            }
        }
    }

    private fun detectObject(image : Bitmap, defaultErrorMessage : String){
        viewModelScope.launch {
            avengerAIManager.detectImage(image, defaultErrorMessage).collectLatest {
                result.value = it
            }
        }
    }

    private fun setSideEffects(equationRecognizerUIEffect : ObjectDetectionUIEffect) {
        viewModelScope.launch {
            _nutrientUIEffectChannel.send(equationRecognizerUIEffect)
        }
    }

    private fun clearResult() {
        result.value = null
    }
}