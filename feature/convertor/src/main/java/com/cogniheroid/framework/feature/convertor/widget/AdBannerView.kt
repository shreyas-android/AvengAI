package com.cogniheroid.framework.feature.convertor.widget

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.configheroid.framework.core.avengerad.AvengerAd

@Composable
fun AdBannerView(modifier: Modifier, activity: Activity, avengerAd: AvengerAd, bannerId:String, mRECId:String){
    Column(modifier = modifier) {
        AdBannerLayout(factory = {
            avengerAd.getApplovinBannerView(activity, bannerId)
        })

        AdBannerLayout(factory = {
            avengerAd.getApplovinMRECView(activity, mRECId)
        })
    }
}

@Composable
internal fun <T : View> AdBannerLayout(factory: (Context) -> T){
    AndroidView(factory = {
        factory(it)
    })
}