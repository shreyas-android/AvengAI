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

    private var avengerAd: com.configheroid.framework.core.avengerad.AvengerAd? = null

    fun initCore(isDebug:Boolean, avengerAdData: AvengerAdData, scope: CoroutineScope) {
        com.configheroid.framework.core.avengerad.AvengerAdCore.firebaseAnalytics = Firebase.analytics
        com.configheroid.framework.core.avengerad.AvengerAdCore.isAdDebug = isDebug
        com.configheroid.framework.core.avengerad.AvengerAdCore.avengerAdData = avengerAdData
        com.configheroid.framework.core.avengerad.AvengerAdCore.avengerAd =
            com.configheroid.framework.core.avengerad.AvengerAd(scope)

    }

    fun getAvengerAdData(): AvengerAdData? {
        return com.configheroid.framework.core.avengerad.AvengerAdCore.avengerAdData
    }

    fun getAvengerAd(scope: CoroutineScope): com.configheroid.framework.core.avengerad.AvengerAd {
        return if (com.configheroid.framework.core.avengerad.AvengerAdCore.avengerAd == null){
            com.configheroid.framework.core.avengerad.AvengerAd(scope)
        }else{
            com.configheroid.framework.core.avengerad.AvengerAdCore.avengerAd!!
        }
    }
}