package com.cogniheroid.framework.feature.avengai

import android.content.Intent
import com.cogniheroid.framework.core.ai.AvengerAITextModel
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.AdvanceTextGenerationViewModelFactory
import com.cogniheroid.framework.feature.avengai.ui.textgeneration.TextGenerationViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

object AvengAICore {

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