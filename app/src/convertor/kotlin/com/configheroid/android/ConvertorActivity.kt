package com.configheroid.android

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.configheroid.android.ad.ui.theme.AdGalaxyTheme
import com.configheroid.framework.feature.convertor.ui.ConvertorScreen

class ConvertorActivity : BaseActitivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvertorScreen()
        }

    }
}