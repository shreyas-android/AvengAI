package com.androidai.demo.avengerad.adnetwork.ironsource

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.androidai.demo.avengerad.adnetwork.ironsource.IronSource.APP_KEY
import com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics
import com.androidai.demo.avengerad.analytics.toBundle
import com.androidai.demo.avengerad.data.AdLifecycleState
import com.ironsource.mediationsdk.ISBannerSize
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.IronSourceBannerLayout
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.model.Placement
import com.ironsource.mediationsdk.sdk.LevelPlayBannerListener
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener


class IronSourceLoadShow {

    fun init(context: Context) {
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_INIT)
        IronSource.init(
            context,
            APP_KEY,
            {
                AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.IRON_SOURCE_INIT_COMPLETE)
            },
            IronSource.AD_UNIT.OFFERWALL,
            IronSource.AD_UNIT.INTERSTITIAL,
            IronSource.AD_UNIT.REWARDED_VIDEO,
            IronSource.AD_UNIT.BANNER
        )
    }

    fun loadRewardedAd(activity: Activity, placementName: String, callback: (AdLifecycleState) -> Unit) {
        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.IRON_SOURCE_REWARD_AD_LOAD)
        Log.d(
            "CHECKIRONSOURCE",
            "CHEKCING THE APP LOCIB = loadRewardedAd::${IronSource.isRewardedVideoAvailable()}"
        )
        IronSource.setLevelPlayRewardedVideoListener(object : LevelPlayRewardedVideoListener {
            // Indicates that there's an available ad.
            // The adInfo object includes information about the ad that was loaded successfully
            // Use this callback instead of onRewardedVideoAvailabilityChanged(true)
            override fun onAdAvailable(adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdAvailable::$"
                )
                IronSource.showRewardedVideo(placementName);
            }

            // Indicates that no ads are available to be displayed
            // Use this callback instead of onRewardedVideoAvailabilityChanged(false)
            override fun onAdUnavailable() {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdUnavailable::$"
                )
                Toast.makeText(activity, "IronSourceReward failed ad", Toast.LENGTH_SHORT)
                    .show()
                callback(AdLifecycleState.FAILED)
            }

            // The Rewarded Video ad view has opened. Your activity will loose focus
            override fun onAdOpened(adInfo: AdInfo) {
                Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdOpened::$")
                callback(AdLifecycleState.FINISHED)
            }

            // The Rewarded Video ad view is about to be closed. Your activity will regain its focus
            override fun onAdClosed(adInfo: AdInfo) {
                Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdClosed::$")
                callback(AdLifecycleState.CLOSED)
            }

            // The user completed to watch the video, and should be rewarded.
            // The placement parameter will include the reward data.
            // When using server-to-server callbacks, you may ignore this event and wait for the ironSource server callback
            override fun onAdRewarded(placement: Placement?, adInfo: AdInfo?) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdRewarded::$"
                )
                callback(AdLifecycleState.CLOSED)
            }

            // The rewarded video ad was failed to show
            override fun onAdShowFailed(error: IronSourceError, adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdShowFailed::$"
                )
                AdGalaxyAnalytics.logEvent(
                    AdGalaxyAnalytics.IRON_SOURCE_REWARD_AD_LOAD_FAILED,
                    error.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(
                    activity,
                    "IronSourceReward ${error.errorMessage}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                callback(AdLifecycleState.FAILED)
            }

            // Invoked when the video ad was clicked.
            // This callback is not supported by all networks, and we recommend using it
            // only if it's supported by all networks you included in your build
            override fun onAdClicked(placement: Placement?, adInfo: AdInfo?) {
                Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = loadRewardedAd:: onAdClicked::$")
            }
        })

        if (IronSource.isRewardedVideoAvailable()) {
            IronSource.loadRewardedVideo()
        } else {
            AdGalaxyAnalytics.logEvent(
                AdGalaxyAnalytics.IRON_SOURCE_REWARD_AD_LOAD_FAILED,"FAILED".toBundle(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
            Toast.makeText(activity, "IronSourceReward failed ad", Toast.LENGTH_SHORT)
                .show()
            callback(AdLifecycleState.FAILED)
        }
    }

    fun loadInterstitialAd(activity: Activity, placementName: String, callback: (AdLifecycleState) -> Unit) {
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_INTERSTITIAL_AD_LOAD)
        Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = loadInterstitialAd::$")
        IronSource.setLevelPlayInterstitialListener(object : LevelPlayInterstitialListener {
            // Invoked when the interstitial ad was loaded successfully.
            // AdInfo parameter includes information about the loaded ad
            override fun onAdReady(adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdReady::$"
                )
                IronSource.showInterstitial(placementName);

            }

            // Indicates that the ad failed to be loaded
            override fun onAdLoadFailed(error: IronSourceError) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_INTERSTITIAL_AD_LOAD_FAILED,
                    error.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdLoadFailed::$"
                )
                Toast.makeText(
                    activity,
                    "IronSource Interstitial ${error.errorMessage}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                callback(AdLifecycleState.FAILED)
            }

            // Invoked when the Interstitial Ad Unit has opened, and user left the application screen.
            // This is the impression indication.
            override fun onAdOpened(adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdOpened::$"
                )
                callback(AdLifecycleState.FINISHED)
            }

            // Invoked when the interstitial ad closed and the user went back to the application screen.
            override fun onAdClosed(adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdClosed::$"
                )
                callback(AdLifecycleState.CLOSED)
            }

            // Invoked when the ad failed to show
            override fun onAdShowFailed(error: IronSourceError, adInfo: AdInfo) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_INTERSTITIAL_AD_DISPLAY_FAILED,
                    error.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdShowFailed::$"
                )
                Toast.makeText(
                    activity,
                    "IronSourceInterstitial ${error.errorMessage}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                callback(AdLifecycleState.FAILED)
            }

            // Invoked when end user clicked on the interstitial ad
            override fun onAdClicked(adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdClicked::$"
                )
            }

            // Invoked before the interstitial ad was opened, and before the InterstitialOnAdOpenedEvent is reported.
            // This callback is not supported by all networks, and we recommend using it only if
            // it's supported by all networks you included in your build.
            override fun onAdShowSucceeded(adInfo: AdInfo) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdShowSucceeded::$"
                )
                callback(AdLifecycleState.COMPLETED)
            }
        })

        if (IronSource.isInterstitialReady()) {
            IronSource.loadInterstitial()
        } else {
            AdGalaxyAnalytics.logEvent(
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_INTERSTITIAL_AD_LOAD_FAILED,
                "FAILED".toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
            Toast.makeText(
                activity,
                "IronSource Interstitial failed",
                Toast.LENGTH_SHORT
            )
                .show()
            callback(AdLifecycleState.FAILED)
        }
    }

    fun getBannerAd(activity: Activity, placement: String): IronSourceBannerLayout {
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_BANNER_LOAD)
        Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = getBannerAd::$")
        val banner = IronSource.createBanner(activity, ISBannerSize.BANNER)
        banner.levelPlayBannerListener = object : LevelPlayBannerListener {
            override fun onAdLoaded(p0: AdInfo?) {
                Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = getBannerAd:: onAdLoaded::$")
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_BANNER_LOADED)
            }

            override fun onAdLoadFailed(p0: IronSourceError) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.IRON_SOURCE_BANNER_LOAD_FAILED,
                    p0.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = getBannerAd:: onAdLoadFailed::${p0.errorMessage}"
                )
                Toast.makeText(
                    activity,
                    "IronSourceBan ${p0.errorMessage}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onAdClicked(p0: AdInfo?) {
                Log.d("CHECKIRONSOURCE", "CHEKCING THE APP LOCIB = getBannerAd:: onAdClicked::$")
            }

            override fun onAdLeftApplication(p0: AdInfo?) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = getBannerAd:: onAdLeftApplication::$"
                )
            }

            override fun onAdScreenPresented(p0: AdInfo?) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = getBannerAd:: onAdScreenPresented::$"
                )
            }

            override fun onAdScreenDismissed(p0: AdInfo?) {
                Log.d(
                    "CHECKIRONSOURCE",
                    "CHEKCING THE APP LOCIB = getBannerAd:: onAdScreenDismissed::$"
                )
            }

        }

        banner.size.isAdaptive = true

        IronSource.loadBanner(banner, placement)

        return banner

    }

    fun getMrecAd(activity: Activity, placement: String): IronSourceBannerLayout {
        val mrec = IronSource.createBanner(activity, ISBannerSize.RECTANGLE)
        mrec.levelPlayBannerListener = object : LevelPlayBannerListener {
            override fun onAdLoaded(p0: AdInfo?) {

            }

            override fun onAdLoadFailed(p0: IronSourceError) {
                Toast.makeText(
                    activity, "IronSourceMrec ${p0.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAdClicked(p0: AdInfo?) {

            }

            override fun onAdLeftApplication(p0: AdInfo?) {

            }

            override fun onAdScreenPresented(p0: AdInfo?) {
            }

            override fun onAdScreenDismissed(p0: AdInfo?) {
            }

        }

        mrec.size.isAdaptive = true
        IronSource.loadBanner(mrec, placement)

        return mrec
    }
}