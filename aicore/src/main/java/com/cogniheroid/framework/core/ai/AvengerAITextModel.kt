package com.cogniheroid.framework.core.ai

import android.graphics.Bitmap
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.Candidate
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.TextPart
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception


class AvengerAITextModel(apiKey:String) {

    private val textGenerativeModel = GenerativeModel(
        modelName = GenerativeAIModelEnum.GEMINI_TEXT.modelName,
        apiKey = apiKey,
        safetySettings = listOf(SafetySetting(HarmCategory.UNKNOWN, BlockThreshold.NONE))
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

        return runCatching {
            generationModel.generateContentStream(input)
        }.fold(onSuccess = {
            it.catch {
                val generateContentResponse = GenerateContentResponse(listOf(), null)
                emit(generateContentResponse)
            }.map { generateContentResponse: GenerateContentResponse ->
                    generateContentResponse.text
                }
        }, onFailure = {
            flow {
                emit(it.message)
            }
        })
    }

}