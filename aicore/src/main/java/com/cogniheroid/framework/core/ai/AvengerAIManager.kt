package com.cogniheroid.framework.core.ai

import com.cogniheroid.framework.core.ai.data.model.ModelInput
import kotlinx.coroutines.flow.Flow

interface AvengerAIManager {

    companion object{
        fun getInstance(apiKey:String):AvengerAIManager = AvengerAIManagerImpl(apiKey)
    }

    suspend fun generateConversation(modelInputHistory: List<ModelInput>,
                                     modelInput: ModelInput, defaultErrorMessage:String): Flow<String?>

    suspend fun generateTextStreamContent(modelInputList:List<ModelInput>,
                                          defaultErrorMessage:String): Flow<String?>
}