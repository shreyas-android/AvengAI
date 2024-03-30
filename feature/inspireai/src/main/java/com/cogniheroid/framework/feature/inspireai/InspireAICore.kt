package com.cogniheroid.framework.feature.inspireai

import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.feature.inspireai.ui.viewmodel.InspireAIViewModelFactory

object InspireAICore {

    lateinit var inspireAIViewModelFactory : InspireAIViewModelFactory

    fun init(apiKey:String){
        val avengerAIManagerImpl = AvengerAIManager.getInstance(apiKey)
        inspireAIViewModelFactory = InspireAIViewModelFactory(avengerAIManagerImpl)
    }

}