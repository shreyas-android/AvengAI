package com.cogniheroid.android

import android.os.Bundle
import androidx.activity.compose.setContent
import com.androidai.galaxy.ad.ui.theme.AdGalaxyTheme
import com.cogniheroid.framework.feature.inspireai.ui.InspireAIScreen

class InspireAIActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdGalaxyTheme {
                InspireAIScreen()
            }
        }
    }
}
