package com.cogniheroid.framework.feature.chat.callback

import android.content.Context
import android.net.Uri

interface ExternalTextCallback {

    fun onAttachmentAdded(context: Context, fileUriList:List<Uri>)
}