package com.androidai.demo.avengerad.adnetwork.meta

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import com.androidai.demo.avengerad.data.AdLifecycleState
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAdListener
import com.facebook.ads.RewardedInterstitialAdListener
import com.facebook.ads.RewardedVideoAd
import com.facebook.ads.RewardedVideoAdListener

class MetaAudienceLoadShow {

    fun init(activity: Activity){
        AudienceNetworkAds.initialize(activity)
    }

    private fun loadRewardAd(activity: Activity, adUnitId: String, callback:(AdLifecycleState)->Unit) {
        // load the ad
        val  rewardedVideoAd = RewardedVideoAd(activity, adUnitId)
        rewardedVideoAd.loadAd(
            rewardedVideoAd.buildLoadAdConfig().withAdListener(object : RewardedVideoAdListener {
                override fun onError(p0: Ad?, p1: AdError?) {

                }

                override fun onAdLoaded(p0: Ad?) {
                    if (!rewardedVideoAd.isAdLoaded || rewardedVideoAd.isAdInvalidated) {
                        Toast.makeText(activity, "Failed to load FB Reward ad", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    Toast.makeText(activity, "FB Reward ad WAS LOADED", Toast.LENGTH_SHORT).show()
                    // Show the ad
                    rewardedVideoAd.show()
                }

                override fun onAdClicked(p0: Ad?) {
                }

                override fun onLoggingImpression(p0: Ad?) {
                }

                override fun onRewardedVideoCompleted() {

                }

                override fun onRewardedVideoClosed() {
                }

            }).build()
        )
    }

    private fun loadInterstitialAd(activity: Activity, adUnitId: String, callback:(AdLifecycleState)->Unit) {
        // load the ad
       val interstitialAd = com.facebook.ads.InterstitialAd(
            activity,adUnitId)
        interstitialAd.loadAd(
            interstitialAd.buildLoadAdConfig().withAdListener(object : InterstitialAdListener {
                override fun onError(p0: Ad?, p1: AdError?) {
                }

                override fun onAdLoaded(p0: Ad?) {
                    if (!interstitialAd.isAdLoaded || interstitialAd.isAdInvalidated) {
                        Toast.makeText(
                            activity,
                            "fAILED TO LOAD FB Interstitial ad",
                            Toast.LENGTH_SHORT
                        ).show()

                        return
                    }
                    Toast.makeText(activity, "FB Interstitial ad WAS LOADED", Toast.LENGTH_SHORT)
                        .show()
                    // Show the ad
                    interstitialAd.show()
                }

                override fun onAdClicked(p0: Ad?) {
                }

                override fun onLoggingImpression(p0: Ad?) {
                }

                override fun onInterstitialDisplayed(p0: Ad?) {

                }

                override fun onInterstitialDismissed(p0: Ad?) {
                }

            }).build()
        )

    }

    private fun loadRewardInterstitialAd(activity: Activity, adUnitId: String, callback:(AdLifecycleState)->Unit) {
        val rewardInterstitialAd = com.facebook.ads.RewardedInterstitialAd(activity, adUnitId)
        rewardInterstitialAd.loadAd(
            rewardInterstitialAd.buildLoadAdConfig()
                .withAdListener(object : RewardedInterstitialAdListener {
                    override fun onError(p0: Ad?, p1: AdError?) {
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        if (!rewardInterstitialAd.isAdLoaded || rewardInterstitialAd.isAdInvalidated) {
                            Toast.makeText(
                                activity,
                                "Failed to load FB Interstitial ad",
                                Toast.LENGTH_SHORT
                            ).show()

                            return
                        }

                        Toast.makeText(
                            activity,
                            "FB Rewarded Interstitial ad WAS LOADED",
                            Toast.LENGTH_SHORT
                        ).show()
                        rewardInterstitialAd.show(
                            rewardInterstitialAd.buildShowAdConfig()
                                .withAppOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .build()
                        )
                    }

                    override fun onAdClicked(p0: Ad?) {
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                    }

                    override fun onRewardedInterstitialCompleted() {

                    }

                    override fun onRewardedInterstitialClosed() {
                    }

                })
                .build()
        )

    }
}