package com.cogniheroid.framework.feature.chat.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision

object DisplayUtils {

   suspend fun getBitmap(context: Context, uri:String): Bitmap? {
        val imageRequestBuilder = ImageRequest.Builder(context)
        val imageLoader = ImageLoader.Builder(context).build()

       val imageRequest = imageRequestBuilder
           .data(uri)
           .size(size = 480)
           .precision(Precision.EXACT)
           .build()

        return try {
            val result = imageLoader.execute(imageRequest)
            if (result is SuccessResult) {
                (result.drawable as BitmapDrawable).bitmap
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }
}