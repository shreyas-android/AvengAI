package com.androidai.galaxy.stressbuster

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.androidai.galaxy.stressbuster.ui.theme.StressBusterTheme
import com.cogniheroid.android.BaseActivity
import com.cogniheroid.android.R
import com.cogniheroid.framework.feature.imageai.ImageAICore
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.ObjectDetectionAIScreen
import com.cogniheroid.framework.feature.nlpai.NLPAICore
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.NutriChefScreen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StressBusterActivity : BaseActivity() {


    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it?.resultCode == RESULT_OK) {
            itemIntent.value = it.data
        }
    }

    private val cameraRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }

            if(!granted) {
                checkPermission{

                }
            }
        }

    private val itemIntent = MutableStateFlow<Intent?>(null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StressBusterTheme {
                NutriChefScreen {
                    checkPermission {
                        val chooser = getCameraGalleryCombinedIntent(true)
                        launcher.launch(chooser)
                    }
                }
            }
        }

        lifecycleScope.launch {
            itemIntent.collectLatest {
                it?.let {
                    NLPAICore.onImageAdded(it)
                }
            }
        }
    }

    private fun checkPermission(callback:() -> Unit){
        // Here, this is the current activity
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                        this, android.Manifest.permission.CAMERA)) {

                Toast.makeText(this, resources.getString(
                    R.string
                    .placeholder_camera_permission_settings), Toast.LENGTH_LONG).show()

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.setData(uri)
                startActivity(intent)
            } else {
                cameraRequestPermissionLauncher.launch(arrayOf(android.Manifest.permission
                    .CAMERA))
            }
        }else{
            callback()
        }
    }

    private fun getCameraGalleryCombinedIntent(isCameraPermissionGranted:Boolean) : Intent {
        val imageIntent = Intent().apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_GET_CONTENT
        }

        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE).apply {
            action = android.provider.MediaStore.ACTION_IMAGE_CAPTURE
        }

        val intentArray = if(isCameraPermissionGranted){
            arrayOf(cameraIntent)
        }else{
            arrayOf()
        }

        val chooser = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, imageIntent);
            putExtra(Intent.EXTRA_TITLE, "Select from:");
            putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        }

        return chooser
    }

    private fun getCameraGalleryCombinedIntent() : Intent {
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