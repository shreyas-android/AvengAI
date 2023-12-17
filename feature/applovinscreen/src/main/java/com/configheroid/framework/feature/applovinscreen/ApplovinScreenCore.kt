package com.configheroid.framework.feature.applovinscreen

import android.content.Context
import com.configheroid.framework.core.avengerad.AvengerAdCore
import com.configheroid.framework.core.avengerad.data.model.AvengerAdData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object ApplovinScreenCore {

    fun init(context: Context, isDebug:Boolean, avengerAdData: AvengerAdData){
        com.configheroid.framework.core.avengerad.AvengerAdCore.initCore(isDebug, avengerAdData,scope = CoroutineScope(Dispatchers.Main))
        com.configheroid.framework.core.avengerad.AvengerAdCore.getAvengerAd(CoroutineScope(Dispatchers.Main)).initAvengerAd(context)
    }
}