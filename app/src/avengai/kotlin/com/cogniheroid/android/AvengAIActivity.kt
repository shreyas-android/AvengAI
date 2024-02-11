package com.cogniheroid.android

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.cogniheroid.framework.feature.avengai.AvengerAICore
import com.cogniheroid.framework.feature.avengai.ui.generation.AvengerAIDemoScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File


class AvengAIActivity : BaseActivity() {

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

        setContent {
            AvengerAIDemoScreen {
                launcher.launch(chooser)
            }
        }

        lifecycleScope.launch {
            itemIntent.collectLatest {
                it?.let {
                    AvengerAICore.onImageAdded(it)
                }
            }
        }

        Log.d("CHECKPREFERENCE","CHEKCIG THE PREFERENCE = ${Build.MANUFACTURER} AND ${Build.BRAND}")
    }

   private fun getCameraGalleryCombinedIntent(): Intent {
        val imageIntent = Intent().apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_GET_CONTENT
        }

        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE).apply {
            action = android.provider.MediaStore.ACTION_IMAGE_CAPTURE
        }

        val intentArray = arrayOf(cameraIntent)

        val chooser = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, imageIntent);
            putExtra(Intent.EXTRA_TITLE, "Select from:");
            putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        }

        return chooser
    }

}
