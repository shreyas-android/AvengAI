package com.cogniheroid.framework.feature.chat.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import coil.transform.CircleCropTransformation

object DisplayUtils {

   suspend fun getBitmap(context: Context, uri:String): Bitmap? {
        val imageRequestBuilder = ImageRequest.Builder(context)
        val imageLoader = ImageLoader.Builder(context).build()

       val imageRequest = imageRequestBuilder
           .data(uri)
           .size(width = 720, height = 480)
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

    suspend fun getCircularBitmap(context: Context, size:Int, uri:String): Bitmap? {
        val imageRequestBuilder = ImageRequest.Builder(context)
        val imageLoader = ImageLoader.Builder(context).build()

        val imageRequest = imageRequestBuilder
            .data(uri)
            .size(size).transformations(CircleCropTransformation())
            .precision(Precision.EXACT)
            .build()

        return try {
            val result = imageLoader.execute(imageRequest)
            if (result is SuccessResult) {
                result.drawable.toBitmap(size, size)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }

    fun getImageUri(context: Context, resourceId:Int): Uri? {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.resources.getResourcePackageName(resourceId))
            .appendPath(context.resources.getResourceTypeName(resourceId))
            .appendPath(context.resources.getResourceEntryName(resourceId))
            .build()
    }
}