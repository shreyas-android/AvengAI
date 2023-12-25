package com.cogniheroid.framework.feature.gemini

import android.util.Log
import com.cogniheroid.framework.core.ai.AvengerAITextModel

object CogniHeroidAICore {

    var avengerAITextModel:AvengerAITextModel? = null
    fun init(apiKey:String){
        Log.d("CHECKAPIKEY","CHEKCING THE API KEY = $apiKey")
        avengerAITextModel = AvengerAITextModel(apiKey)
    }
}