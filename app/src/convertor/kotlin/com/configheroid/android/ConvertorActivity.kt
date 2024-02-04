package com.configheroid.android

import android.os.Bundle
import androidx.activity.compose.setContent
import com.cogniheroid.android.BaseActivity
import com.cogniheroid.framework.feature.convertor.ui.ConvertorScreen

class ConvertorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvertorScreen(this@ConvertorActivity)
        }

    }
}