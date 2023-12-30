package com.cogniheroid.android

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.cogniheroid.framework.feature.gemini.CogniHeroidAICore
import com.cogniheroid.framework.feature.gemini.ui.CogniHeroidAIDemoScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CogniHeroidAIActivity : AppCompatActivity() {

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
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        setContent {
            CogniHeroidAIDemoScreen {
                launcher.launch(chooser)
            }
        }

        lifecycleScope.launch {
            itemIntent.collectLatest {
                it?.let {
                    CogniHeroidAICore.onImageAdded(it)
                }
            }
        }
    }

    fun getCameraGalleryCombinedIntent(): Intent {
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
