package com.cogniheroid.android

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.cogniheroid.framework.feature.avengai.AvengerAICore
import com.cogniheroid.framework.feature.avengai.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIEvent
import com.cogniheroid.framework.feature.chat.ChatScreen
import com.cogniheroid.framework.feature.chat.callback.ChatExternalCallback
import com.cogniheroid.framework.feature.chat.callback.ExternalTextCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ChatWithAIActivity : BaseActivity() {

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it?.resultCode == RESULT_OK) {
            itemIntent.value = it.data
        }
    }

    private val chooser = getCameraGalleryCombinedIntent()

    private val itemIntent = MutableStateFlow<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CHECKBUILDMAN","CHECKING THE BUILD = ${Build.MANUFACTURER} AND BRAND = ${Build.BRAND}")
        setContent {
            val chatExternalCallback = object :ChatExternalCallback{
                override fun onAddAttachment(externalTextCallback: ExternalTextCallback) {
                    launcher.launch(chooser)

                    lifecycleScope.launch {
                        itemIntent.collectLatest {
                            externalTextCallback.onAttachmentAdded(this@ChatWithAIActivity,
                                getUriList(it))
                        }
                    }
                }

            }
            ChatScreen(chatExternalCallback)
        }

    }


    private fun getCameraGalleryCombinedIntent(): Intent {
        val imageIntent = Intent().apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_GET_CONTENT
        }

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            action = MediaStore.ACTION_IMAGE_CAPTURE
        }

        val intentArray = arrayOf(cameraIntent)

        val chooser = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, imageIntent);
            putExtra(Intent.EXTRA_TITLE, "Select from:");
            putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        }

        return chooser
    }

    fun getUriList(intent:Intent?):List<Uri>{
        return if (intent != null) {
            val uri = intent.data
            Log.d("CHECKTHEURI","CHEKCIG  THE URI = $uri")
            val data = intent.extras?.get("data")
            if (data is Bitmap) {
                listOf()
            } else {
                val singleData = intent.data
                val clipData = intent.clipData
                val tempList: List<Uri> = if (singleData != null) {
                    listOf(singleData)
                } else if (clipData != null) {
                    val list = mutableListOf<Uri>()
                    for (i in 0 until clipData.itemCount) {
                        list.add(clipData.getItemAt(i).uri)
                    }
                    list
                } else {
                    listOf()
                }

                tempList
            }

        }else{
            listOf()
        }


    }
}
