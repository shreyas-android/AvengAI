package com.cogniheroid.framework.feature.imageai

import android.content.Intent
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.ObjectDetectionViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

object ImageAICore {

        lateinit var objectDetectionViewModelFactory : ObjectDetectionViewModelFactory

        internal val imageIntentFlow = MutableStateFlow<Intent?>(null)

        fun init(apiKey:String){
            val avengerAIManagerImpl = AvengerAIManager.getInstance(apiKey)
            objectDetectionViewModelFactory = ObjectDetectionViewModelFactory(avengerAIManagerImpl)
        }

        fun onImageAdded(intent: Intent){
            imageIntentFlow.value = intent
        }
    }
