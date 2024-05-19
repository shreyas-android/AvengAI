package com.jackson.framework.feature.convertor

import android.app.Activity
import android.content.Context
import android.util.Log

//import com.configheroid.framework.feature.applovinscreen.ApplovinScreenCore
import com.sparrow.framework.core.avengerad.data.model.AvengerAdData
import com.sparrow.framework.feature.avengeradscreen.AvengerAdScreenCore
import kotlinx.coroutines.CoroutineScope

object ConvertorCore {

    fun init(
            context : Context, isDebug : Boolean, avengerAdData : AvengerAdData,
            scope : CoroutineScope) {
        Log.d("CHECKINIT","CHECKI THE INIT ConvertorCore init:: = $avengerAdData")
        AvengerAdScreenCore.init(context, isDebug, avengerAdData, scope)

    }

    fun onAvengerAdActivityHandling(activity : Activity, scope : CoroutineScope){
        AvengerAdScreenCore.onAvengerAdActivityHandling(activity, scope)
    }


    fun updateAvengerAdData(activity : Activity, avengerAdData : AvengerAdData, scope : CoroutineScope){
        AvengerAdScreenCore.updateAvengerAdData(activity, avengerAdData, scope)
    }

}