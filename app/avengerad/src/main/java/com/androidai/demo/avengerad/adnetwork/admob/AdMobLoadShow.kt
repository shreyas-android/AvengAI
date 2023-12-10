package com.androidai.demo.avengerad.adnetwork.admob

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.androidai.demo.avengerad.analytics.toBundle
import com.androidai.demo.avengerad.data.AdLifecycleState
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.vungle.ads.internal.AdInternal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdMobLoadShow(private val coroutineScope: CoroutineScope) {

    val tag = "AdMobLoadShow"

    fun init(context: Context) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_INIT)
        MobileAds.initialize(context) {
            com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_INIT_COMPLETE)
        }
    }

    fun loadRewardedAd(activity: Activity, adUnitId: String, callback: (AdLifecycleState) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_REWARD_AD_LOAD)
        RewardedAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_REWARD_AD_LOAD_FAILED, adError.message.toBundle(
                            com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                    Toast.makeText(
                        activity, "Reward ${adError.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    callback(AdLifecycleState.FAILED)
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_REWARD_AD_LOADED)
                    ad.fullScreenContentCallback = getFullScreenListener()
                    showRewardAd(activity, ad) {
                        callback(AdLifecycleState.COMPLETED)
                    }

                }
            })
    }

    fun loadInterstitialAd(activity: Activity, adUnitId: String, callback: (AdLifecycleState) -> Unit) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_INTERSTITIAL_AD_LOAD)
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_INTERSTITIAL_AD_LOAD_FAILED,
                        adError.message.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                    Toast.makeText(
                        activity,
                        "Interstitial ${adError.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    callback(AdLifecycleState.FAILED)

                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_INTERSTITIAL_AD_LOADED)
                    p0.fullScreenContentCallback = getFullScreenListener()

                    showInterstitialAd(activity, p0) {
                        callback(AdLifecycleState.COMPLETED)
                    }


                }
            })
    }

    fun loadRewardInterstitialAd(
        activity: Activity,
        adUnitId: String,
        callback: (AdLifecycleState) -> Unit
    ) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_REWARD_INTERSTITIAL_AD_LOAD)
        RewardedInterstitialAd.load(activity, adUnitId,
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_REWARD_INTERSTITIAL_AD_LOADED)
                    ad.fullScreenContentCallback = getFullScreenListener()

                    showRewardInterstitialAd(activity, ad) {
                        callback(AdLifecycleState.COMPLETED)
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_REWARD_INTERSTITIAL_AD_LOAD_FAILED,
                        adError.message.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                    Toast.makeText(
                        activity,
                        "Reward interstitial ${adError.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    callback(AdLifecycleState.FAILED)

                }
            })
    }

    fun showRewardAd(activity: Activity, rewardedAd: RewardedAd, onDismiss: () -> Unit) {
        rewardedAd.show(activity) {
            onDismiss()
        }
    }

    fun showInterstitialAd(
        activity: Activity,
        interstitialAd: InterstitialAd,
        onDismiss: () -> Unit
    ) {
        interstitialAd.show(activity)
        requestDelay(10000)
        onDismiss()
    }

    fun showRewardInterstitialAd(
        activity: Activity,
        rewardedInterstitialAd: RewardedInterstitialAd,
        onDismiss: () -> Unit
    ) {
        rewardedInterstitialAd.show(activity) {
            onDismiss()
        }
    }

    fun getFullScreenListener(): FullScreenContentCallback {
        return object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(tag, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(tag, "Ad dismissed fullscreen content.")
                //  rewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(tag, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(tag, "Ad showed fullscreen ")

            }
        }
    }

    fun getBannerView(context: Context, adUnitId: String): AdView {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_BANNER_LOAD)
        val adView = AdView(context)
        adView.setAdSize(AdSize.FULL_BANNER)
        adView.adUnitId = adUnitId


        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_BANNER_LOAD_FAILED, p0.message.toBundle(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE))
                Toast.makeText(
                    context, "AdmobBan ${p0.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.AD_MOB_BANNER_LOADED)
            }
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        return adView
    }

    private fun requestDelay(millis: Long) {
        coroutineScope.launch {
            delay(millis)
        }
    }

}