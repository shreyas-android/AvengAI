package com.cogniheroid.framework.core.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


class AvengerAITextModel(apiKey:String) {

    private val textGenerativeModel = GenerativeModel(
        modelName = GenerativeAIModelEnum.GEMINI_TEXT.modelName,
        apiKey = apiKey
    )

    suspend fun generateText(text:String, callback:(String)->Unit){
        val generateResponse = textGenerativeModel.generateContent(text)
        generateResponse.text?.let {
            callback(it)
        }
    }

    suspend fun generateTextStream(text: String): Flow<GenerateContentResponse> {
        return textGenerativeModel.generateContentStream(text)
    }

}