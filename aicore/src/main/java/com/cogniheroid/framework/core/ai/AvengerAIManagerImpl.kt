package com.cogniheroid.framework.core.ai

import android.graphics.Bitmap
import com.cogniheroid.framework.core.ai.data.UIResult
import com.cogniheroid.framework.core.ai.data.enums.AvengerAIModelType
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.core.ai.data.model.ObjectDetectionInfo
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


internal class AvengerAIManagerImpl(apiKey: String) : AvengerAIManager {

    private val options by lazy {
        ObjectDetectorOptions.Builder().setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects().enableClassification()  // Optional
            .build()

    }

    private val objectDetector by lazy {
        ObjectDetection.getClient(options)
    }

    private val textGenerativeModel = GenerativeModel(
        modelName = AvengerAIModelType.GEMINI_TEXT.modelName,
        apiKey = apiKey,
    )

    private val advanceTextGenerationModel = GenerativeModel(
        modelName = AvengerAIModelType.GEMINI_VISION.modelName,
        apiKey = apiKey
    )

    override suspend fun generateConversation(
        modelInputHistory: List<ModelInput>, modelInput: ModelInput,
        defaultErrorMessage:String): Flow<String?> {
        val model = if (modelInputHistory.any {
                it is ModelInput.Image
            }) {
            advanceTextGenerationModel
        } else {
            textGenerativeModel
        }
        val historyList = mutableListOf<Content>()

        modelInputHistory.forEach {
            when (it) {
                is ModelInput.Image -> {
                    val role = if (it.isUser){
                        "user"
                    }else{
                        "model"
                    }
                    historyList.add(content(role) {
                        image(it.bitmap)
                    })

                }

                is ModelInput.Text -> {
                    val role = if (it.isUser){
                        "user"
                    }else{
                        "model"
                    }
                    historyList.add(content(role) {
                        text(it.text)
                    })
                }
            }
        }

        val prompt = when (modelInput) {
            is ModelInput.Image -> {
                val role = if (modelInput.isUser){
                    "user"
                }else{
                    "model"
                }
                content(role) {
                    image(modelInput.bitmap)
                }
            }

            is ModelInput.Text -> {
                val role = if (modelInput.isUser){
                    "user"
                }else{
                    "model"
                }
                content(role) {
                    text(modelInput.text)
                }
            }
        }
        val chat = model.startChat(historyList)
        return runCatching {
            chat.sendMessage(prompt)
        }.fold(onSuccess = {
            flow {
                emit(it)
            }.catch {
                val generateContentResponse = GenerateContentResponse(listOf(), null)
                emit(generateContentResponse)
            }.map { generateContentResponse: GenerateContentResponse ->
                val text = generateContentResponse.text
                if (text.isNullOrEmpty()) {
                    defaultErrorMessage
                } else {
                    text
                }            }
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

    override suspend fun generateTextStreamContent(modelInputList: List<ModelInput>,
                                                   defaultErrorMessage:String): Flow<String?> {
        val input = content {
            modelInputList.forEach {
                when (it) {
                    is ModelInput.Image -> image(it.bitmap)
                    is ModelInput.Text -> text(it.text)
                }
            }
        }

        val isImageInputPresent = modelInputList.any { it is ModelInput.Image }

        val generationModel =
            if (isImageInputPresent) advanceTextGenerationModel else textGenerativeModel

        return runCatching {
            generationModel.generateContentStream(input)
        }.fold(onSuccess = {
            it.catch {
                val generateContentResponse = GenerateContentResponse(listOf(), null)
                emit(generateContentResponse)
            }.map { generateContentResponse: GenerateContentResponse ->
                val text = generateContentResponse.text
                if (text.isNullOrEmpty()) {
                    defaultErrorMessage
                } else {
                    text
                }
            }
        }, onFailure = {
            flow {
                emit(it.message)
            }
        })
    }

    override suspend fun detectImage(bitmap: Bitmap, defaultErrorMessage : String): Flow<UIResult<List<ObjectDetectionInfo>>> {
        val objectDetectionInfoItemsFlow = MutableStateFlow<UIResult<List<ObjectDetectionInfo>>>(UIResult.Loading)
        val image = InputImage.fromBitmap(bitmap, 0)
        objectDetector.process(image).addOnSuccessListener {detectedObjects ->
            val list = arrayListOf<ObjectDetectionInfo>()
            for (detectedObject in detectedObjects) {
                val boundingBox = detectedObject.boundingBox
                val trackingId = detectedObject.trackingId
                val label = detectedObject.labels.maxByOrNull {
                    it.confidence
                }
                list.add(ObjectDetectionInfo(boundingBox, trackingId, label?.text ?: ""))
            }

            objectDetectionInfoItemsFlow.value = UIResult.Success(list)

        }.addOnFailureListener {
            objectDetectionInfoItemsFlow.value = UIResult.Error(it.message ?:
            defaultErrorMessage)
        }

        return  objectDetectionInfoItemsFlow
    }


}