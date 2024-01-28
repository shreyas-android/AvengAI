package com.cogniheroid.framework.core.ai.data.model

import android.graphics.Bitmap

sealed class ModelInput {

    data class Image(val isUser:Boolean, val bitmap: Bitmap): ModelInput()

    data class Text(val isUser:Boolean, val text: String): ModelInput()
}