package com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate

import android.graphics.Bitmap

sealed class ObjectDetectionUIEvent {
    data class DetectObject(val image: Bitmap, val defaultErrorMessage:String) : ObjectDetectionUIEvent()

    object OnOpenImagePicker : ObjectDetectionUIEvent()

    data class RemoveImage(val image: Bitmap) : ObjectDetectionUIEvent()

    data class AddImage(val image: Bitmap?) : ObjectDetectionUIEvent()
}