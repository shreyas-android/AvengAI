package com.configheroid.framework.core.avengerad.adnetwork.applovin

import android.app.Activity
import android.content.Context
import com.configheroid.framework.core.avengerad.AvengerAd
import com.configheroid.framework.core.avengerad.data.AdLifecycleState
import com.configheroid.framework.core.avengerad.data.AdSource
import com.configheroid.framework.core.avengerad.data.SuccessState
import com.configheroid.framework.core.avengerad.delay.TimeDelay
import com.applovin.adview.AppLovinFullscreenActivity
import com.applovin.adview.AppLovinFullscreenThemedActivity
import com.applovin.mediation.ads.MaxAdView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList

data class ApplovinState(val loadedAd:Int, val adShown:Int, val failedLoad:Int, val notReady:Int,
                         val enableLoadButton:Boolean, val remainingAd:Int, val loadInterstitial:Int, val loadRewardAdCount:Int)
internal class ApplovinInitialization(private val coroutineScope: CoroutineScope) {

    private val applovinState = MutableStateFlow(ApplovinState(0,0,0,0,
        false, 0, 0, 0))

    private val failureDelayDuration = 10 * 60 * 1000L
    private val oneHourDuration = 60 * 60 * 1000L

    private var emptyCount = 0

    private val maxAdLoadCount = 100

    private val timeDelay by lazy { TimeDelay(coroutineScope) }

    private val parallelCoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val adSourceStack = LinkedList<AdSource>()

    private val notReadyStack = LinkedList<AdSource>()

    private var isStackRunning = false

    private val applovinLoadShow by lazy { ApplovinLoadShow() }

    fun init(context: Context, callback: () -> Unit) {
        applovinLoadShow.init(context, callback)
    }

    fun runApplovinLoop(activity: Activity, type: Int) {
        emptyCount = 0
        applovinState.value = applovinState.value.copy(enableLoadButton = false)
        onStartLoadApplovinRange(activity, type)

        coroutineScope.launch {
            delay(oneHourDuration)
            runApplovinLoop(activity, type)
            onStartShowingApplovin(activity, type)
        }
    }

    private fun onStartLoadApplovinRange(activity: Activity, type: Int) {
        if (adSourceStack.size <= maxAdLoadCount) {
            onLoadApplovinInterstitialRange(activity, type = type)
        }
    }

    private fun onLoadApplovinInterstitialRange(activity: Activity, type: Int) {
        for (adUnit in Applovin.getInterstitialList(type)) {
            asyncLoadApplovinInterstitialAd(activity, adUnit, type)
        }
    }

    private fun asyncLoadApplovinInterstitialAd(activity: Activity, adUnit: String, type: Int) {
        applovinState.value = applovinState.value.copy(loadInterstitial =  applovinState.value.loadInterstitial+1)
        parallelCoroutineScope.launch {
            applovinLoadShow.onLoadInterstitialAd(activity, adUnit) {
                when (it) {
                    is SuccessState.Failure -> {
                        applovinState.value = applovinState.value.copy(failedLoad =  applovinState.value.failedLoad+1)
                    }

                    is SuccessState.Success -> {
                        onPushApplovinStack(activity, AdSource.ApplovinInterstitial(it.data), type)
                    }
                }
            }
        }
    }

    private fun onLoadApplovinReward() {
        /* for (adUnit in Applovin.rewardList) {
             asyncLoadApplovinRewardAd(activity, adUnit)
         }*/
    }

    private fun asyncLoadApplovinRewardAd(activity: Activity, adUnit: String, type: Int) {
        applovinState.value = applovinState.value.copy(loadRewardAdCount =  applovinState.value.loadRewardAdCount+1)
        parallelCoroutineScope.launch {
            applovinLoadShow.onLoadRewardAd(activity, adUnit) {
                when (it) {
                    is SuccessState.Failure -> {
                        applovinState.value = applovinState.value.copy(failedLoad =  applovinState.value.failedLoad+1)
                    }

                    is SuccessState.Success -> {
                        onPushApplovinStack(activity, AdSource.ApplovinRewarded(it.data), type)
                    }
                }

            }
        }
    }

    private fun onPushApplovinStack(activity: Activity, adSource: AdSource, type: Int) {
        applovinState.value = applovinState.value.copy(loadedAd =  applovinState.value.loadedAd+1)
        val added = adSourceStack.add(adSource)
        if (added) {
            onStartShowingApplovin(activity, type)
        }
    }

    private fun onPushNotReadyStack(adSource: AdSource) {
        applovinState.value = applovinState.value.copy(notReady =  applovinState.value.notReady+1)
        notReadyStack.add(adSource)
    }

    private fun onStartShowingApplovin(activity: Activity, type: Int) {
        if (!isStackRunning) {
            safeShowApplovin(activity, type = type) {
                onStartLoadApplovinRange(activity, type)
            }
        }
    }

    private fun safeShowApplovin(activity: Activity, type: Int, onAdShownCallback: () -> Unit) {
        applovinState.value = applovinState.value.copy(remainingAd = adSourceStack.size)
        checkAndShowApplovin(onStackIsEmpty = {
            isStackRunning = false
            emptyCount++
            onStartLoadApplovinRange(activity, type = type)
        }, onAdShownCallback = { adSource, adState ->
            if (adState == AdLifecycleState.NOT_READY) {
                onPushNotReadyStack(adSource)
            }

            timeDelay.delayLoadAd(getLoadNextAdDuration(), adState) { adSuccessFailureState ->
                handleAdShownCount(adSuccessFailureState)
                onAdShownCallback()
                safeShowApplovin(activity, type, onAdShownCallback)
            }

        })
    }

    private fun checkAndShowApplovin(onStackIsEmpty: () -> Unit,
                                     onAdShownCallback: (AdSource, AdLifecycleState) -> Unit) {
        if (adSourceStack.isNotEmpty()) {
            isStackRunning = true
            when (val adSource = adSourceStack.pop()) {
                is AdSource.ApplovinInterstitial -> {
                    applovinLoadShow.onShowInterstitialAd(adSource.interstitialAd) {
                        onAdShownCallback(adSource, it)
                    }
                }

                is AdSource.ApplovinRewarded -> {
                    applovinLoadShow.onShowRewardAd(adSource.maxRewardedAd) {
                        onAdShownCallback(adSource, it)
                    }
                }
            }
        } else {
            isStackRunning = false
            onStackIsEmpty()
        }
    }

    private fun handleAdShownCount(adSuccessFailureState: com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState) {
        if (adSuccessFailureState == com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.SUCCESS) {
            applovinState.value = applovinState.value.copy(adShown =  applovinState.value.adShown+1)
        }
    }

    fun getDurationBetweenAd(): Long {
        return Applovin.getDurationBetweenAd()
    }

    private fun getLoadNextAdDuration(): Long {
        return Applovin.getLoadAdDuration()

    }

    fun getApplovinState() = applovinState


    fun onApplovinActivityHandling(activity: Activity) {
        when (activity) {
            is AppLovinFullscreenThemedActivity -> {
                // if (count > 1) {
                CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                    delay(getDurationBetweenAd())
                    activity.dismiss()
                }
                // }
            }

            is AppLovinFullscreenActivity -> {
                CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                    delay(getDurationBetweenAd())
                    activity.dismiss()
                }
            }
        }
    }

    fun getApplovinBannerView(activity: Activity, bannerId:String): MaxAdView {
        return applovinLoadShow.getBannerView(activity, bannerId)
    }

    fun getApplovinMRECView(activity: Activity, mRECId: String): MaxAdView {
        return applovinLoadShow.getMRECView(activity, mRECId)
    }

}