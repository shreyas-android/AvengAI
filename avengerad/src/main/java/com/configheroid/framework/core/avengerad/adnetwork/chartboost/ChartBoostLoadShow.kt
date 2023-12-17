package com.configheroid.framework.core.avengerad.adnetwork.chartboost

import android.app.Activity
import android.content.Context
import android.util.Log
import com.configheroid.framework.core.avengerad.analytics.toBundle
import com.configheroid.framework.core.avengerad.data.AdLifecycleState
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.ad.ChartboostMediationAdLoadRequest
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAd
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdListener
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdLoadResult
import com.chartboost.heliumsdk.ad.HeliumBannerAd
import com.chartboost.heliumsdk.ad.HeliumBannerAdListener
import com.chartboost.heliumsdk.domain.ChartboostMediationAdException
import com.chartboost.heliumsdk.domain.Keywords
import kotlinx.coroutines.delay


internal class ChartBoostLoadShow {

    fun init(context: Context) {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.CHART_BOOST_INIT)
        HeliumSdk.start(
            context,
            com.configheroid.framework.core.avengerad.adnetwork.chartboost.ChartBoost.APP_ID,
            com.configheroid.framework.core.avengerad.adnetwork.chartboost.ChartBoost.APP_SIGNATURE,
            null,
            object : HeliumSdk.HeliumSdkListener {
                override fun didInitialize(error: Error?) {
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.CHART_BOOST_INIT_COMPLETE,
                        error?.message?.toBundle(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                }

            })
    }

    private suspend fun getFullScreenAd(
        activity: Activity,
        adUnitId: String,
        isReward: Boolean,
        callback: (AdLifecycleState) -> Unit
    ): ChartboostMediationFullscreenAdLoadResult {
        if (isReward) {
            com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.CHART_BOOST_REWARD_AD_LOAD)
        } else {
            com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.CHART_BOOST_INTERSTITIAL_AD_LOAD)
        }
        val adRequest = ChartboostMediationAdLoadRequest(adUnitId, Keywords())
        return HeliumSdk.loadFullscreenAd(activity, adRequest, object :
            ChartboostMediationFullscreenAdListener {
            override fun onAdClicked(ad: ChartboostMediationFullscreenAd) {
                Log.d("CHECKCHARTBOOST", "CHECKING THE CHART BOOST INIT = onAdClicked")
            }

            override fun onAdClosed(
                ad: ChartboostMediationFullscreenAd,
                error: ChartboostMediationAdException?
            ) {
                Log.d("CHECKCHARTBOOST", "CHECKING THE CHART BOOST INIT = onAdClosed")
                callback(AdLifecycleState.CLOSED)
            }

            override fun onAdExpired(ad: ChartboostMediationFullscreenAd) {
                Log.d("CHECKCHARTBOOST", "CHECKING THE CHART BOOST INIT = onAdExpired")
                callback(AdLifecycleState.FAILED)
            }

            override fun onAdImpressionRecorded(ad: ChartboostMediationFullscreenAd) {
                Log.d("CHECKCHARTBOOST", "CHECKING THE CHART BOOST INIT = onAdImpressionRecorded")
                callback(AdLifecycleState.CLOSED)
            }

            override fun onAdRewarded(ad: ChartboostMediationFullscreenAd) {
                Log.d("CHECKCHARTBOOST", "CHECKING THE CHART BOOST INIT = onAdRewarded")
                callback(AdLifecycleState.COMPLETED)
            }
        })

    }

    suspend fun loadInterstitialAd(
        activity: Activity,
        adUnitId: String,
        callback: (AdLifecycleState) -> Unit
    ) {
        val interstitialAd = getFullScreenAd(activity, adUnitId, isReward = false, callback)
        interstitialAd.ad?.show(activity)
        delay(60000)
        callback(AdLifecycleState.COMPLETED)
    }

    suspend fun loadRewardAd(activity: Activity, adUnitId: String, callback: (AdLifecycleState) -> Unit) {
        val rewardAd = getFullScreenAd(activity, adUnitId, isReward = true, callback)
        rewardAd.ad?.show(activity)
        delay(60000)
        callback(AdLifecycleState.COMPLETED)
    }


    fun getBannerAd(context: Context, adUnitId: String): HeliumBannerAd {
        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.CHART_BOOST_BANNER_LOAD)
        val bannerSize = HeliumBannerAd.HeliumBannerSize.LEADERBOARD

        val banner = HeliumBannerAd(context, adUnitId, bannerSize, object : HeliumBannerAdListener {
            override fun onAdCached(
                placementName: String,
                loadId: String,
                winningBidInfo: Map<String, String>,
                error: ChartboostMediationAdException?
            ) {

            }

            override fun onAdClicked(placementName: String) {

            }

            override fun onAdImpressionRecorded(placementName: String) {

            }

        })

        banner.load()

        return banner
    }
}