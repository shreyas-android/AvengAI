package com.androidai.demo.avengerad.adnetwork.unity

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.androidai.demo.avengerad.AvengerAdCore
import com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics
import com.androidai.demo.avengerad.analytics.toBundle
import com.androidai.demo.avengerad.data.AdLifecycleState
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsShowOptions
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

class UnityLoadShow {

    val tag = "UnityLoadShow"

    fun init(context: Context) {
        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.UNITY_INIT)
        UnityAds.initialize(
            context, Unity.GAME_ID,
            AvengerAdCore.isAdDebug, object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.UNITY_INIT_COMPLETE)
                }

                override fun onInitializationFailed(
                    error: UnityAds.UnityAdsInitializationError?,
                    message: String?
                ) {
                    AdGalaxyAnalytics.logEvent(
                        AdGalaxyAnalytics.UNITY_INIT_COMPLETE,
                        message?.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                }

            }
        )
    }

    fun loadInterstitialAd(activity: Activity, adUnitId: String, callback:(AdLifecycleState)->Unit) {
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LOAD_INTERSTITIAL_AD)
        UnityAds.load(adUnitId, object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String?) {
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_INTERSTITIAL_AD_LOADED)
                UnityAds.show(
                    activity,
                    adUnitId,
                    UnityAdsShowOptions(),
                    object : IUnityAdsShowListener {
                        override fun onUnityAdsShowFailure(
                            placementId: String?,
                            error: UnityAds.UnityAdsShowError?,
                            message: String?
                        ) {
                            AdGalaxyAnalytics.logEvent(
                                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_INTERSTITIAL_AD_DISPLAY_FAILED,
                                message?.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                            )
                            Toast.makeText(
                                activity,
                                "UnityInterstitial ${message}",
                                Toast.LENGTH_SHORT
                            ).show()

                            callback(AdLifecycleState.FAILED)
                        }

                        override fun onUnityAdsShowStart(placementId: String?) {
                            callback(AdLifecycleState.FINISHED)
                            AdGalaxyAnalytics.logEvent(
                                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_INTERSTITIAL_AD_DISPLAYED
                            )

                        }

                        override fun onUnityAdsShowClick(placementId: String?) {
                        }

                        override fun onUnityAdsShowComplete(
                            placementId: String?,
                            state: UnityAds.UnityAdsShowCompletionState?
                        ) {
                            callback(AdLifecycleState.CLOSED)
                        }

                    }
                )

            }

            override fun onUnityAdsFailedToLoad(
                placementId: String?,
                error: UnityAds.UnityAdsLoadError?,
                message: String?
            ) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_INTERSTITIAL_AD_LOAD_FAILED,
                    message?.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(activity, "UnityInterstitial $message" , Toast.LENGTH_SHORT)
                    .show()

                callback(AdLifecycleState.FAILED)
            }

        })
    }


    fun loadRewardAd(activity: Activity, adUnitId: String, callback:(AdLifecycleState)->Unit) {
        Log.d("AD_GALAXY_REWARD", "loadUnityRewardAd: $adUnitId")
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LOAD_REWARD_AD)
        UnityAds.load(adUnitId, object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String?) {
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_REWARD_AD_LOADED)
                UnityAds.show(
                    activity,
                    adUnitId,
                    UnityAdsShowOptions(),
                    object : IUnityAdsShowListener {
                        override fun onUnityAdsShowFailure(
                            placementId: String?,
                            error: UnityAds.UnityAdsShowError?,
                            message: String?
                        ) {
                            AdGalaxyAnalytics.logEvent(
                                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_REWARD_AD_DISPLAYED,
                                message?.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                            )
                            Toast.makeText(
                                activity,
                                "UnityRewarded ${message}",
                                Toast.LENGTH_SHORT
                            ).show()

                            callback(AdLifecycleState.FAILED)

                        }

                        override fun onUnityAdsShowStart(placementId: String?) {
                            AdGalaxyAnalytics.logEvent(
                                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_REWARD_AD_DISPLAYED
                            )
                            Log.d(
                                tag,
                                "CHECING THE UNITY ID =onUnityAdsShowStart:: $placementId"
                            )
                            callback(AdLifecycleState.FINISHED)

                        }

                        override fun onUnityAdsShowClick(placementId: String?) {
                            Log.d(
                                tag,
                                "CHECING THE UNITY ID =onUnityAdsShowClick:: $placementId"
                            )
                        }

                        override fun onUnityAdsShowComplete(
                            placementId: String?,
                            state: UnityAds.UnityAdsShowCompletionState?
                        ) {
                            Log.d(
                                tag,
                                "CHECING THE UNITY ID = onUnityAdsShowComplete:: $placementId"
                            )
                            callback(AdLifecycleState.CLOSED)
                        }

                    })
            }

            override fun onUnityAdsFailedToLoad(
                placementId: String?,
                error: UnityAds.UnityAdsLoadError?,
                message: String?
            ) {
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_REWARD_AD_LOADED,
                    message?.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(activity, "UnityRewarded $message", Toast.LENGTH_SHORT)
                    .show()

                callback(AdLifecycleState.FAILED)

            }

        })
    }

    fun getBannerView(activity: Activity, placement: String): BannerView {
        AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_BANNER_LOAD)
        val bannerView = BannerView(
            activity, placement,
            UnityBannerSize(320, 50)
        )
        bannerView.listener =object : BannerView.Listener() {
            override fun onBannerFailedToLoad(bannerAdView: BannerView,
                                              errorInfo: BannerErrorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo)
                AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_BANNER_LOAD_FAILED,
                    errorInfo.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(activity, "UnityBan ${errorInfo.errorMessage}",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onBannerLoaded(bannerAdView: BannerView?) {
                super.onBannerLoaded(bannerAdView)
                AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.UNITY_BANNER_LOADED)
            }
        }
        bannerView.load()
        return bannerView
    }
}
