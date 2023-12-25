package com.cogniheroid.android

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.cogniheroid.framework.feature.gemini.ui.CogniHeroidAIDemoScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CogniHeroidAIActivity : ComponentActivity() {

    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult?> {
            if (it?.resultCode == RESULT_OK) {
                val singleData = it.data?.data
                val clipData = it.data?.clipData
                if (singleData != null) {
                    itemUri.value = itemUri.value + listOf(singleData)
                } else if (clipData != null) {
                    val list = mutableListOf<Uri>()
                    for (i in 0 until clipData.itemCount) {
                        list.add(clipData.getItemAt(i).uri)
                    }
                    itemUri.value = itemUri.value + list

                }
            }
        })

    val imageIntent = Intent().apply {
        setType("image/*")
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        setAction(Intent.ACTION_GET_CONTENT)
    }

    val itemUri = MutableStateFlow<List<Uri>>(listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        setContent {
            CogniHeroidAIDemoScreen { onImageAdded ->
                launcher.launch(imageIntent)

                lifecycleScope.launch {
                    itemUri.collectLatest {
                        if (it.isNotEmpty()) {
                            onImageAdded(it)
                            itemUri.value = listOf()
                        }
                    }
                }
            }
        }
    }


}
