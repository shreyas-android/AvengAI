package com.configheroid.framework.core.avengerad

import android.app.Activity
import android.content.Context
import android.view.View
import com.applovin.mediation.ads.MaxAdView
import com.configheroid.framework.core.avengerad.adnetwork.admob.AdMobLoadShow
import com.configheroid.framework.core.avengerad.adnetwork.applovin.ApplovinInitialization
import com.configheroid.framework.core.avengerad.adnetwork.chartboost.ChartBoostLoadShow
import com.configheroid.framework.core.avengerad.adnetwork.inmobi.InMobiLoadShow
import com.configheroid.framework.core.avengerad.adnetwork.ironsource.IronSourceLoadShow
import com.configheroid.framework.core.avengerad.adnetwork.liftoff.LiftOffLoadShow
import com.configheroid.framework.core.avengerad.adnetwork.unity.UnityLoadShow
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

        val defaultType = com.configheroid.framework.core.avengerad.AvengerAd.Companion.TYPE_1
    }


   private val adMobLoadShow by lazy { AdMobLoadShow(coroutineScope) }

    private val applovinInitialization by lazy { ApplovinInitialization(coroutineScope) }

    private  val chartBoostLoadShow by lazy { ChartBoostLoadShow() }

    private  val inMobiLoadShow by lazy { InMobiLoadShow() }

    private val ironSourceLoadShow by lazy { IronSourceLoadShow() }

    private val liftOffLoadShow by lazy { LiftOffLoadShow() }

    private val unityLoadShow by lazy { UnityLoadShow() }

    private val shouldEnableLoadButton = MutableStateFlow(true)

    fun initAvengerAd(context: Context) {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.AD_GALAXY_AD_INIT)
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

    fun runApplovinLoop(activity: Activity){
        applovinInitialization.runApplovinLoop(activity,
            com.configheroid.framework.core.avengerad.AvengerAd.Companion.TYPE_1
        )
    }

    fun getApplovinState() = applovinInitialization.getApplovinState()


    fun onApplovinActivityHandling(activity: Activity) {
        applovinInitialization.onApplovinActivityHandling(activity)
    }

    fun getApplovinBannerView(activity: Activity, bannerId:String): View {
        return applovinInitialization.getApplovinBannerView(activity, bannerId)
    }

    fun getApplovinMRECView(activity: Activity, mRECId:String): View {
        return applovinInitialization.getApplovinMRECView(activity, mRECId)
    }
}