package com.cogniheroid.framework.core.ai.data.model

import android.graphics.Rect

data class ObjectDetectionInfo(val rect: Rect,
                               val trackingId:Int?, val labelDescription:String)