package com.cogniheroid.framework.ui.component

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun <T : View> AvengBannerLayout(modifier : Modifier, factory: (Context) -> T){
    Box(modifier = modifier) {
        AndroidView(factory = {
            factory(it)
        })
    }
}