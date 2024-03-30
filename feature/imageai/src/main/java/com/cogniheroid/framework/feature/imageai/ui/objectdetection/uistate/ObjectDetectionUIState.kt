package com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate

import android.graphics.Bitmap
import com.cogniheroid.framework.core.ai.data.UIResult
import com.cogniheroid.framework.core.ai.data.model.ObjectDetectionInfo

data class ObjectDetectionUIState(
    val isFileUploaded:Boolean,
    val outputUIResult:UIResult<List<ObjectDetectionInfo>>?,
    val isGenerating:Boolean,
    val image: Bitmap?,
){

    companion object{
        fun getDefault() : ObjectDetectionUIState {
            return ObjectDetectionUIState(false, null,false, null)
        }
    }
}