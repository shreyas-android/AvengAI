package com.cogniheroid.framework.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.IOException

object ImageUtils {


    //TODO takes URI of the image and returns bitmap
    fun uriToBitmap(context: Context, selectedFileUri: Uri?): Bitmap? {
        selectedFileUri?.let {
            try {
                val parcelFileDescriptor: ParcelFileDescriptor? =
                    context.contentResolver.openFileDescriptor(selectedFileUri, "r")
                val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                var image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                image= image.copy(Bitmap.Config.ARGB_8888, true);
                image.width = 768
                image.height = 768
                parcelFileDescriptor?.close()
                return image
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}