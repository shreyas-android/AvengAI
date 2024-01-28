package com.configheroid.framework.core.avengerad

import com.configheroid.framework.core.avengerad.data.model.AvengerAdData
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope

object AvengerAdCore {

    lateinit var firebaseAnalytics: FirebaseAnalytics

    var isAdDebug = true

    private var avengerAdData: AvengerAdData? = null

    private var avengerAd: AvengerAd? = null

    fun initAvengerAdCore(isDebug:Boolean, avengerAdData: AvengerAdData, scope: CoroutineScope) {
        firebaseAnalytics = Firebase.analytics
        isAdDebug = isDebug
        AvengerAdCore.avengerAdData = avengerAdData
        avengerAd = AvengerAd(scope)

    }

    fun getAvengerAdData(): AvengerAdData? {
        return avengerAdData
    }

    fun getAvengerAd(scope: CoroutineScope): AvengerAd {
        return if (avengerAd == null){
            AvengerAd(scope)
        }else{
            avengerAd!!
        }
    }
}