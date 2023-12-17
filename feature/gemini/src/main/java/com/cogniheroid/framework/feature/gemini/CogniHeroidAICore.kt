package com.cogniheroid.framework.feature.gemini

import com.cogniheroid.framework.core.ai.AvengerAITextModel

object CogniHeroidAICore {

    var avengerAITextModel:AvengerAITextModel? = null
    fun init(apiKey:String){
        avengerAITextModel = AvengerAITextModel(apiKey)
    }
}