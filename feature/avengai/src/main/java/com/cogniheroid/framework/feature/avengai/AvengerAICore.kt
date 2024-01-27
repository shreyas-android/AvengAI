package com.cogniheroid.framework.feature.avengai

import android.content.Intent
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.AdvanceTextGenerationViewModelFactory
import com.cogniheroid.framework.feature.avengai.ui.textgeneration.TextGenerationViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

object AvengerAICore {

    lateinit var advanceTextGenerationViewModelFactory: AdvanceTextGenerationViewModelFactory

    lateinit var textGenerationViewModelFactory: TextGenerationViewModelFactory

    internal val imageIntentFlow = MutableStateFlow<Intent?>(null)

    fun init(apiKey:String){
        val avengerAIManagerImpl = AvengerAIManager.getInstance(apiKey)
        advanceTextGenerationViewModelFactory =
            AdvanceTextGenerationViewModelFactory(avengerAIManagerImpl)
        textGenerationViewModelFactory =
            TextGenerationViewModelFactory(avengerAIManagerImpl)
    }

    fun onImageAdded(intent: Intent){
        imageIntentFlow.value = intent
    }
}