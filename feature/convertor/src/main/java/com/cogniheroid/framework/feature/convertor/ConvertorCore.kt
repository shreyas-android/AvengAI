package com.cogniheroid.framework.feature.convertor

import android.content.Context
import com.configheroid.framework.core.avengerad.AvengerAdCore

//import com.configheroid.framework.feature.applovinscreen.ApplovinScreenCore
import com.configheroid.framework.core.avengerad.data.model.AvengerAdData
import kotlinx.coroutines.CoroutineScope

object ConvertorCore {

    fun init(context: Context, isDebug:Boolean, avengerAdData: AvengerAdData, scope:CoroutineScope) {
       AvengerAdCore.initAvengerAdCore(isDebug = isDebug, avengerAdData = avengerAdData, scope)
    }

}