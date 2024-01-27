package com.cogniheroid.framework.core.ai

import com.cogniheroid.framework.core.ai.data.enums.AvengerAIModelType
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


internal class AvengerAIManagerImpl(apiKey:String):AvengerAIManager {

    private val textGenerativeModel = GenerativeModel(
        modelName = AvengerAIModelType.GEMINI_TEXT.modelName,
        apiKey = apiKey,
    )

    private val advanceTextGenerationModel = GenerativeModel(
        modelName = AvengerAIModelType.GEMINI_VISION.modelName,
        apiKey = apiKey
    )

   override suspend fun generateConversation(modelInputHistory: List<ModelInput>, modelInput: ModelInput): Flow<String?>{
       val model = if (modelInputHistory.any {
           it is ModelInput.Image
           }){
           advanceTextGenerationModel
       }else{
           textGenerativeModel
       }
        val historyList = mutableListOf<Content>()

            modelInputHistory.forEach {
                when(it){
                    is ModelInput.Image -> {
                        historyList.add(content("user") {
                            image(it.bitmap)
                        })

                    }
                    is ModelInput.Text -> {
                        historyList.add(content("user") {
                            text(it.text)
                        })
                    }
                }
            }

        val prompt = when(modelInput){
            is ModelInput.Image -> {
                content("user") {
                    image(modelInput.bitmap)
                }
            }
            is ModelInput.Text -> {
                content("user") {
                    text(modelInput.text)
                }
            }
        }
        val chat = model.startChat(historyList)
        return runCatching {
            chat.sendMessageStream(prompt)
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
/*
       return chat.sendMessageStream(prompt).map {
           it.text
       }*/

    }

    override suspend fun generateTextStreamContent(modelInputList:List<ModelInput>): Flow<String?>{
        val input = content {
            modelInputList.forEach {
                when(it){
                    is ModelInput.Image -> image(it.bitmap)
                    is ModelInput.Text -> text(it.text)
                }
            }
        }

        val isImageInputPresent = modelInputList.any { it is ModelInput.Image }

        val generationModel = if (isImageInputPresent) advanceTextGenerationModel else textGenerativeModel

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