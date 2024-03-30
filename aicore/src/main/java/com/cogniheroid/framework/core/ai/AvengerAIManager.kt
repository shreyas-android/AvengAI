package com.cogniheroid.framework.core.ai

import android.graphics.Bitmap
import com.cogniheroid.framework.core.ai.data.RemoteResult
import com.cogniheroid.framework.core.ai.data.UIResult
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.core.ai.data.model.ObjectDetectionInfo
import kotlinx.coroutines.flow.Flow

interface AvengerAIManager {

    companion object{
        fun getInstance(apiKey:String):AvengerAIManager = AvengerAIManagerImpl(apiKey)
    }

    suspend fun generateConversation(modelInputHistory: List<ModelInput>,
                                     modelInput: ModelInput, defaultErrorMessage:String): Flow<String?>

    suspend fun generateTextStreamContent(modelInputList:List<ModelInput>,
                                          defaultErrorMessage:String): Flow<String?>

    suspend fun detectImage(bitmap: Bitmap, defaultErrorMessage : String): Flow<UIResult<List<ObjectDetectionInfo>>>
}