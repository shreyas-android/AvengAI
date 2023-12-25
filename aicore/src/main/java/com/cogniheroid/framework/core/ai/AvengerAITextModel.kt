package com.cogniheroid.framework.core.ai

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class AvengerAITextModel(apiKey:String) {

    private val textGenerativeModel = GenerativeModel(
        modelName = GenerativeAIModelEnum.GEMINI_TEXT.modelName,
        apiKey = apiKey
    )

    private val advanceTextGenerationModel = GenerativeModel(
        modelName = GenerativeAIModelEnum.GEMINI_VISION.modelName,
        apiKey = apiKey
    )

    suspend fun generateTextStreamContent(imageInputList:List<Bitmap> = listOf(), text: String): Flow<String?>{
        val input = content {
            imageInputList.forEach {
                image(it)
            }
            text(text)
        }

        val generationModel = if (imageInputList.isNotEmpty()) advanceTextGenerationModel else textGenerativeModel

        return runCatching { generationModel.generateContentStream(input)}.fold(onSuccess = {
            it.map { generateContentResponse: GenerateContentResponse ->
                generateContentResponse.text
            }
        }, onFailure = {
            flow {
                emit(it.message)
            }
        })
    }

}