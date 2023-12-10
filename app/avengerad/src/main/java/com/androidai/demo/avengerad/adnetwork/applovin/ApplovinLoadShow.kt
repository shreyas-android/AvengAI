package com.androidai.demo.avengerad.adnetwork.applovin

import android.app.Activity
import android.content.Context
import android.util.Log
import com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics
import com.androidai.demo.avengerad.analytics.toBundle
import com.androidai.demo.avengerad.data.AdLifecycleState
import com.androidai.demo.avengerad.data.SuccessState
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.sdk.AppLovinSdk


internal class ApplovinLoadShow {

    fun init(context: Context, initializedCallback:() -> Unit) {
        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_INIT)
        // Make sure to set the mediation provider value to "max" to ensure proper functionality

        AppLovinSdk.getInstance(context).mediationProvider = "max"
        AppLovinSdk.initializeSdk(context) {
            AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_INIT_COMPLETE)
            initializedCallback()
            Log.d("CHECKAPPLOVIN", "CHEKCING THE APP LOCIB = ${it.isTestModeEnabled}")
        }
    }


    fun onLoadInterstitialAd(
        activity: Activity,
        adUnitId: String,
        callback: (SuccessState<MaxInterstitialAd>) -> Unit
    ) {
        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_LOAD)
        val interstitialAd = MaxInterstitialAd(adUnitId, activity)
        interstitialAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_LOADED)
                callback(SuccessState.Success(interstitialAd))
            }

            override fun onAdDisplayed(p0: MaxAd?) {

            }

            override fun onAdHidden(p0: MaxAd?) {

            }

            override fun onAdClicked(p0: MaxAd?) {

            }

            override fun onAdLoadFailed(p0: String?, p1: MaxError) {
                AdGalaxyAnalytics.logEvent(
                    AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_LOAD_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                callback(SuccessState.Failure(adUnitId, Throwable(p1.message)))

            }

            override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError) {
            }
        })

        interstitialAd.loadAd()
    }

    fun onShowInterstitialAd(interstitialAd: MaxInterstitialAd, callback: (AdLifecycleState) -> Unit) {
        Log.d("CHECKAPPLOVINSHOW","CHEKCIG  THE APPLOVIN SHOW = $interstitialAd")
        interstitialAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd?) {

            }

            override fun onAdDisplayed(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD onShowInterstitialAd:: = onAdDisplayed:: "
                )
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_DISPLAYED)
                callback(AdLifecycleState.FINISHED)
            }

            override fun onAdHidden(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD onShowInterstitialAd:: = onAdHidden:: "
                )
            }

            override fun onAdClicked(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD onShowInterstitialAd:: = onAdClicked:: "
                )
            }

            override fun onAdLoadFailed(p0: String?, p1: MaxError) {

            }

            override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD onShowInterstitialAd:: = onAdDisplayFailed:: $p0 and error = ${p1.message}"
                )
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_DISPLAY_FAILED, p1
                        .message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                callback(AdLifecycleState.FAILED)
            }

        })

        if (interstitialAd.isReady) {
            interstitialAd.showAd()
        } else {
            callback(AdLifecycleState.NOT_READY)
        }
    }


    fun loadInterstitialAd(activity: Activity, adUnitId: String, callback: (AdLifecycleState) -> Unit) {
        Log.d("CHECKAPPLOVIN", "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = $adUnitId")
        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_LOAD)
        val interstitialAd = MaxInterstitialAd(adUnitId, activity)

        interstitialAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = onAdLoaded:: "
                )
                interstitialAd.showAd()
                AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_LOADED)
            }

            override fun onAdDisplayed(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = onAdDisplayed:: "
                )
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_DISPLAYED)
                callback(AdLifecycleState.FINISHED)
            }

            override fun onAdHidden(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = onAdHidden:: "
                )
            }

            override fun onAdClicked(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = onAdClicked:: "
                )
            }

            override fun onAdLoadFailed(p0: String?, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = onAdLoadFailed:: $p0 and error = ${p1.message} "
                )
                AdGalaxyAnalytics.logEvent(
                    AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_LOAD_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                callback(AdLifecycleState.FAILED)
            }

            override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadInterstitialAd:: = onAdDisplayFailed:: $p0 and error = ${p1.message}"
                )

                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_INTERSTITIAL_AD_DISPLAY_FAILED, p1
                        .message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                callback(AdLifecycleState.FAILED)
            }

        })

        interstitialAd.loadAd()
    }

    fun onLoadRewardAd(activity: Activity, adUnitId: String, callback: (SuccessState<MaxRewardedAd>) -> Unit) {
        Log.d("CHECKAPPLOVIN", "CHEKCING THE APPLOVIN REWARDED AD onLoadRewardAd:: = $adUnitId")
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_LOAD)
        val rewardedAd = MaxRewardedAd.getInstance(adUnitId, activity)
        rewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_LOADED)
                callback(SuccessState.Success(rewardedAd))
            }

            override fun onAdDisplayed(p0: MaxAd?) {

            }

            override fun onAdHidden(p0: MaxAd?) {

            }

            override fun onAdClicked(p0: MaxAd?) {

            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD onLoadRewardAd:: = onAdLoadFailed:: $p0 and error = ${p1.message}"
                )
                AdGalaxyAnalytics.logEvent(
                    AdGalaxyAnalytics.APPLOVIN_REWARD_AD_LOAD_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                callback(SuccessState.Failure(adUnitId, Throwable(p1.message)))
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {

            }

            override fun onUserRewarded(p0: MaxAd?, p1: MaxReward?) {

            }

            override fun onRewardedVideoStarted(p0: MaxAd?) {

            }

            override fun onRewardedVideoCompleted(p0: MaxAd?) {

            }
        })

        rewardedAd.loadAd()
    }

    fun onShowRewardAd(rewardedAd: MaxRewardedAd, callback: (AdLifecycleState) -> Unit) {
        Log.d("CHECKAPPLOVINSHOW","CHEKCIG  THE APPLOVIN SHOW onShowRewardAd:: = $rewardedAd")
        rewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onAdLoaded(p0: MaxAd?) {

            }

            override fun onAdDisplayed(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdDisplayed::"
                )
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_DISPLAYED)
            }

            override fun onAdHidden(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdHidden::"
                )
            }

            override fun onAdClicked(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdClicked::"
                )
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {

            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdDisplayFailed:: $p0 and error = ${p1.message}"
                )
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_DISPLAY_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                callback(AdLifecycleState.FAILED)
            }

            override fun onUserRewarded(p0: MaxAd?, p1: MaxReward?) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onUserRewarded:: $p0 and error = ${p1}"
                )
              //  callback(AdLifecycleState.CLOSED)
            }

            override fun onRewardedVideoStarted(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onRewardedVideoStarted:: $p0 and error ="
                )
                callback(AdLifecycleState.FINISHED)
            }

            override fun onRewardedVideoCompleted(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVINSHOW",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onRewardedVideoCompleted:: $p0 and error ="
                )
              //  callback(AdLifecycleState.CLOSED)
            }

        })

        Log.d("CHECKAPPLOVINSHOW","CHEKCIG  THE APPLOVIN SHOW onShowRewardAd:: = $rewardedAd")
        if (rewardedAd.isReady) {
            rewardedAd.showAd()
        } else {
            callback(AdLifecycleState.NOT_READY)
        }
    }

    fun loadRewardedAd(activity: Activity, adUnitId: String, callback: (AdLifecycleState) -> Unit) {
        Log.d("CHECKAPPLOVIN", "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = $adUnitId")
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_LOAD)
        val rewardedAd = MaxRewardedAd.getInstance(adUnitId, activity)
        rewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdLoaded::"
                )
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_LOADED)
                rewardedAd.showAd()
            }

            override fun onAdDisplayed(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdDisplayed::"
                )
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_DISPLAYED)
            }

            override fun onAdHidden(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdHidden::"
                )
            }

            override fun onAdClicked(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdClicked::"
                )
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdLoadFailed:: $p0 and error = ${p1.message}"
                )
                AdGalaxyAnalytics.logEvent(
                    AdGalaxyAnalytics.APPLOVIN_REWARD_AD_LOAD_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )

                callback(AdLifecycleState.FAILED)
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onAdDisplayFailed:: $p0 and error = ${p1.message}"
                )
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_REWARD_AD_DISPLAY_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )

                callback(AdLifecycleState.FAILED)
            }

            override fun onUserRewarded(p0: MaxAd?, p1: MaxReward?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onUserRewarded:: $p0 and error = ${p1}"
                )
                callback(AdLifecycleState.CLOSED)
            }

            override fun onRewardedVideoStarted(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onRewardedVideoStarted:: $p0 and error ="
                )
                callback(AdLifecycleState.FINISHED)
            }

            override fun onRewardedVideoCompleted(p0: MaxAd?) {
                Log.d(
                    "CHECKAPPLOVIN",
                    "CHEKCING THE APPLOVIN REWARDED AD loadRewardedAd:: = onRewardedVideoCompleted:: $p0 and error ="
                )
                callback(AdLifecycleState.CLOSED)
            }

        })

        rewardedAd.loadAd()
    }

    fun getBannerView(activity: Activity, adUnitId: String): MaxAdView {
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_BANNER_LOAD)
        val adView = MaxAdView(adUnitId, activity)
        adView.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_BANNER_LOADED)
            }

            override fun onAdDisplayed(p0: MaxAd?) {
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_BANNER_DISPLAYED)

            }

            override fun onAdHidden(p0: MaxAd?) {

            }

            override fun onAdClicked(p0: MaxAd?) {

            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_BANNER_LOAD_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )

            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_BANNER_DISPLAY_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
            }

            override fun onAdExpanded(p0: MaxAd?) {

            }

            override fun onAdCollapsed(p0: MaxAd?) {

            }

        })

        adView.loadAd()
        return adView
    }

    fun getMRECView(activity: Activity, adUnitId: String): MaxAdView {
        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_MREC_LOAD)
        val adView = MaxAdView(adUnitId, MaxAdFormat.MREC, activity)
        adView.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_MREC_LOADED)
            }

            override fun onAdDisplayed(p0: MaxAd?) {
                AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.APPLOVIN_MREC_DISPLAYED)

            }

            override fun onAdHidden(p0: MaxAd?) {

            }

            override fun onAdClicked(p0: MaxAd?) {

            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                AdGalaxyAnalytics.logEvent(
                    AdGalaxyAnalytics.APPLOVIN_MREC_LOAD_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_MREC_DISPLAY_FAILED,
                    p1.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                )
            }

            override fun onAdExpanded(p0: MaxAd?) {

            }

            override fun onAdCollapsed(p0: MaxAd?) {

            }

        })

        adView.loadAd()
        return adView
    }

}

