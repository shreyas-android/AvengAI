package com.cogniheroid.framework.core.ai.data.model

import android.graphics.Bitmap

sealed class ModelInput {

    data class Image(val bitmap: Bitmap): ModelInput()

    data class Text(val text: String): ModelInput()
}