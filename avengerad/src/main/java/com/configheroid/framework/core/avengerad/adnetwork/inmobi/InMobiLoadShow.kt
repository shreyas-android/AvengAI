package com.configheroid.framework.core.avengerad.adnetwork.inmobi

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.configheroid.framework.core.avengerad.analytics.toBundle
import com.configheroid.framework.core.avengerad.data.AdLifecycleState
import com.inmobi.ads.AdMetaInfo
import com.inmobi.ads.InMobiAdRequestStatus
import com.inmobi.ads.InMobiBanner
import com.inmobi.ads.InMobiInterstitial
import com.inmobi.ads.listeners.BannerAdEventListener
import com.inmobi.ads.listeners.InterstitialAdEventListener
import com.inmobi.sdk.InMobiSdk
import org.json.JSONException
import org.json.JSONObject


internal class InMobiLoadShow {

    fun init(context: Context) {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INIT)
        val consentObject = JSONObject()
        try {
            // Provide correct consent value to sdk which is obtained by User
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true)
            // Provide 0 if GDPR is not applicable and 1 if applicable
            consentObject.put("gdpr", "1")
            // Provide user consent in IAB format
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_IAB, "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        InMobiSdk.init(context, InMobi.APP_ID, consentObject) {
            Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = int::${it?.message}")
            com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INIT_COMPLETE, it?.message?.toBundle(
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
        }
    }


    fun loadInterstitialAd(activity: Activity, adUnitId: Long, callback:(AdLifecycleState)->Unit) {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INTERSTITIAL_AD_LOAD)
        Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd::$")
        val interstitialAd = InMobiInterstitial(activity, adUnitId, object :
            InterstitialAdEventListener() {
            override fun onAdDisplayFailed(p0: InMobiInterstitial) {
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INTERSTITIAL_AD_DISPLAYED, "FAILED".toBundle(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.AD_TYPE))
                super.onAdDisplayFailed(p0)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdDisplayFailed::$")
                Toast.makeText(
                    activity,
                    "InMobiInterstitial ad failed to load",
                    Toast.LENGTH_SHORT
                )
                    .show()

                callback(AdLifecycleState.FAILED)
            }

            override fun onAdFetchFailed(p0: InMobiInterstitial, p1: InMobiAdRequestStatus) {
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INTERSTITIAL_AD_LOADED, p1.message.toBundle(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                super.onAdFetchFailed(p0, p1)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdFetchFailed::$")
                Toast.makeText(
                    activity,
                    "InMobiInterstitial ${p1.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()

                callback(AdLifecycleState.FAILED)
            }

            override fun onAdDisplayed(p0: InMobiInterstitial, p1: AdMetaInfo) {
                super.onAdDisplayed(p0, p1)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INTERSTITIAL_AD_DISPLAYED)
            }

            override fun onAdLoadSucceeded(p0: InMobiInterstitial, p1: AdMetaInfo) {
                super.onAdLoadSucceeded(p0, p1)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INTERSTITIAL_AD_LOADED)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdLoadSucceeded::$")
                p0.show()
            }

            override fun onAdDismissed(p0: InMobiInterstitial) {
                super.onAdDismissed(p0)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdDismissed::$")
                callback(AdLifecycleState.CLOSED)
            }

            override fun onAdWillDisplay(p0: InMobiInterstitial) {
                super.onAdWillDisplay(p0)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdWillDisplay::$")
            }

            override fun onAdImpression(p0: InMobiInterstitial) {
                super.onAdImpression(p0)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdImpression::$")
            }

            override fun onAdFetchSuccessful(p0: InMobiInterstitial, p1: AdMetaInfo) {
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadInterstitialAd:: onAdFetchSuccessful::$")
            }

        })
        if (interstitialAd.isReady){
            interstitialAd.load()
        }else{
            com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_INTERSTITIAL_AD_DISPLAYED, "FAILED".toBundle(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.AD_TYPE))
            Toast.makeText(
                activity,
                "InMobiInterstitial ad failed to load",
                Toast.LENGTH_SHORT
            )
                .show()
            callback(AdLifecycleState.FAILED)
        }

    }


    fun loadRewardedAd(activity: Activity, adUnitId: Long, callback:(AdLifecycleState)->Unit) {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_REWARD_AD_LOAD)
        Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd::$")
        val rewardedAd = InMobiInterstitial(activity, adUnitId, object :
            InterstitialAdEventListener() {
            override fun onRewardsUnlocked(p0: InMobiInterstitial, p1: MutableMap<Any, Any>?) {
                super.onRewardsUnlocked(p0, p1)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd:: onRewardsUnlocked::$")
                callback(AdLifecycleState.CLOSED)
            }

            override fun onAdDisplayFailed(p0: InMobiInterstitial) {
                super.onAdDisplayFailed(p0)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_REWARD_AD_DISPLAYED, "FAILED".toBundle(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.AD_TYPE))
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd:: onAdDisplayFailed::$")
                Toast.makeText(
                    activity,
                    "InMobi Reward ad failed to load",
                    Toast.LENGTH_SHORT
                )
                    .show()

                callback(AdLifecycleState.FAILED)
            }

            override fun onAdFetchFailed(p0: InMobiInterstitial, p1: InMobiAdRequestStatus) {
                super.onAdFetchFailed(p0, p1)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_REWARD_AD_LOADED, p1.message.toBundle(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd:: onAdFetchFailed::$")
                Toast.makeText(
                    activity,
                    "InMobiRewarded ${p1.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()

                callback(AdLifecycleState.FAILED)
            }

            override fun onAdDisplayed(p0: InMobiInterstitial, p1: AdMetaInfo) {
                super.onAdDisplayed(p0, p1)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_REWARD_AD_DISPLAYED)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd:: onAdDisplayed::$")
            }

            override fun onAdLoadSucceeded(p0: InMobiInterstitial, p1: AdMetaInfo) {
                super.onAdLoadSucceeded(p0, p1)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_REWARD_AD_LOADED)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd:: onAdLoadSucceeded::$")
                p0.show()
            }

            override fun onAdDismissed(p0: InMobiInterstitial) {
                super.onAdDismissed(p0)
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd::  onAdDismissed::$")
                callback(AdLifecycleState.FINISHED)
            }

            override fun onAdWillDisplay(p0: InMobiInterstitial) {
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd::  onAdWillDisplay::$")
                super.onAdWillDisplay(p0)
            }

            override fun onAdImpression(p0: InMobiInterstitial) {
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd::  onAdImpression::$")
                super.onAdImpression(p0)
            }

            override fun onAdFetchSuccessful(p0: InMobiInterstitial, p1: AdMetaInfo) {
                Log.d("CHECKINMOBI","CHEKCING THE APP LOCIB = loadRewardedAd::  onAdFetchSuccessful::$")
            }

        })

        if (rewardedAd.isReady){
            rewardedAd.load()
        }else{
            com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_REWARD_AD_DISPLAYED, "FAILED".toBundle(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.AD_TYPE))
            Toast.makeText(
                activity,
                "InMobiReward failed",
                Toast.LENGTH_SHORT
            )
                .show()
            callback(AdLifecycleState.FAILED)
        }
    }

    fun getBannerView(activity: Activity, id: Long): InMobiBanner {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_BANNER_LOAD)
        val bannerAd = InMobiBanner(activity, id)
        bannerAd.setBannerSize(320, 320)
        bannerAd.setAnimationType(InMobiBanner.AnimationType.ROTATE_HORIZONTAL_AXIS)
        bannerAd.setRefreshInterval(5)
        bannerAd.setListener(object : BannerAdEventListener() {
            override fun onAdFetchFailed(p0: InMobiBanner, p1: InMobiAdRequestStatus) {
                super.onAdFetchFailed(p0, p1)
                com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.IN_MOBI_BANNER_LOADED, p1.message.toBundle(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                Toast.makeText(
                    activity,
                    "InMobiBan ${p1.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })

        bannerAd.load()
        return bannerAd

    }
}