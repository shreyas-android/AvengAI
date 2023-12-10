package com.androidai.demo.avengerad

import android.app.Activity
import android.content.Context
import com.androidai.demo.avengerad.adnetwork.admob.AdMobLoadShow
import com.androidai.demo.avengerad.adnetwork.applovin.ApplovinInitialization
import com.androidai.demo.avengerad.adnetwork.applovin.ApplovinLoadShow
import com.androidai.demo.avengerad.adnetwork.chartboost.ChartBoostLoadShow
import com.androidai.demo.avengerad.adnetwork.inmobi.InMobiLoadShow
import com.androidai.demo.avengerad.adnetwork.ironsource.IronSourceLoadShow
import com.androidai.demo.avengerad.adnetwork.liftoff.LiftOffLoadShow
import com.androidai.demo.avengerad.adnetwork.unity.UnityLoadShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow


class AvengerAd(private val coroutineScope: CoroutineScope) {

    enum class AdSuccessFailureState {
        SUCCESS,
        FAILURE,
        STOPPED,
    }

    companion object {
        const val TYPE_ADMOB = 1
        const val TYPE_APPLOVIN = 2
        const val TYPE_CHARTBOOST = 3
        const val TYPE_INMOBI = 4
        const val TYPE_IRON_SOURCE = 5
        const val TYPE_LIFTOFF = 6
        const val TYPE_UNITY = 8

        const val TYPE_1 = 1
        const val TYPE_2 = 2
        const val TYPE_3 = 3
        const val TYPE_4 = 4
        const val TYPE_5 = 5
        const val TYPE_6 = 6

        val defaultType = TYPE_1
    }


    val adMobLoadShow by lazy { AdMobLoadShow(coroutineScope) }

    private val applovinInitialization by lazy { ApplovinInitialization(coroutineScope) }

    val chartBoostLoadShow by lazy { ChartBoostLoadShow() }

    val inMobiLoadShow by lazy { InMobiLoadShow() }

    val ironSourceLoadShow by lazy { IronSourceLoadShow() }

    val liftOffLoadShow by lazy { LiftOffLoadShow() }

    val unityLoadShow by lazy { UnityLoadShow() }

    val shouldEnableLoadButton = MutableStateFlow(true)

    fun init(context: Context) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_GALAXY_AD_INIT)
        //  ironSourceLoadShow.init(context)
        //  liftOffLoadShow.init(context)
        //  adMobLoadShow.init(context)
        shouldEnableLoadButton.value = false
        applovinInitialization.init(context) {
            shouldEnableLoadButton.value = true
        }
        //  inMobiLoadShow.init(context)
        //  unityLoadShow.init(context)
        //  chartBoostLoadShow.init(context)
    }

    fun applovinAdSelected(activity: Activity){
        applovinInitialization.onApplovinSelected(activity)
    }

    fun getApplovinState() = applovinInitialization.getApplovinState()


    fun onApplovinActivityHandling(activity: Activity) {
        applovinInitialization.onApplovinActivityHandling(activity)
    }
}