package com.cogniheroid.framework.feature.gemini

import android.content.Intent
import android.util.Log
import com.cogniheroid.framework.core.ai.AvengerAITextModel
import com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration.AdvanceTextGenerationViewModelFactory
import com.cogniheroid.framework.feature.gemini.ui.textgeneration.TextGenerationViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

object CogniHeroidAICore {

    lateinit var advanceTextGenerationViewModelFactory: AdvanceTextGenerationViewModelFactory

    lateinit var textGenerationViewModelFactory: TextGenerationViewModelFactory

    internal val imageIntentFlow = MutableStateFlow<Intent?>(null)

    fun init(apiKey:String){
        val avengerAITextModel = AvengerAITextModel(apiKey)
        advanceTextGenerationViewModelFactory =
            AdvanceTextGenerationViewModelFactory(avengerAITextModel)
        textGenerationViewModelFactory =
            TextGenerationViewModelFactory(avengerAITextModel)
    }

    fun onImageAdded(intent: Intent){
        imageIntentFlow.value = intent
    }
}