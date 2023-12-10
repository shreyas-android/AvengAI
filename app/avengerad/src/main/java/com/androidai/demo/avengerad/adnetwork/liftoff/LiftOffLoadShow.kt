package com.androidai.demo.avengerad.adnetwork.liftoff

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff
import com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics
import com.androidai.demo.avengerad.analytics.toBundle
import com.androidai.demo.avengerad.data.AdLifecycleState
import com.vungle.ads.AdConfig
import com.vungle.ads.BannerAd
import com.vungle.ads.BannerAdSize
import com.vungle.ads.BaseAd
import com.vungle.ads.BaseAdListener
import com.vungle.ads.InitializationListener
import com.vungle.ads.InterstitialAd
import com.vungle.ads.RewardedAd
import com.vungle.ads.VungleAds
import com.vungle.ads.VungleError

class LiftOffLoadShow {

    fun init(context: Context) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INIT)
        Log.d("CHECKVUNGLE", "Vungle Banner INIT ")
        VungleAds.init(context, com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.APP_ID, object : InitializationListener {
            override fun onSuccess() {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INIT_COMPLETE)
                Toast.makeText(context, "Vungle init success", Toast.LENGTH_SHORT).show()
                Log.d("CHECKVUNGLE", "Vungle Banner INIT success ")
            }

            override fun onError(vungleError: VungleError) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INIT_COMPLETE,
                    vungleError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(
                    context, "VungleInit ${vungleError.errorMessage}", Toast.LENGTH_SHORT
                ).show()

                Log.d("CHECKVUNGLE", "Vungle Banner INIT failed  ${vungleError.errorMessage}")
            }
        })
    }


    fun loadInterstitialAd(activity: Activity, placementId: String, callback: (AdLifecycleState) -> Unit) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INTERSTITIAL_AD_LOAD)
        val interstitialAd = InterstitialAd(activity, placementId, AdConfig())
        interstitialAd.adListener = object : BaseAdListener {
            override fun onAdClicked(baseAd: BaseAd) {

            }

            override fun onAdEnd(baseAd: BaseAd) {
                callback(AdLifecycleState.CLOSED)
            }

            override fun onAdFailedToLoad(baseAd: BaseAd, adError: VungleError) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INTERSTITIAL_AD_LOAD_FAILED,
                    adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(
                    activity, "VungleInterstitial ${adError.errorMessage}", Toast.LENGTH_SHORT
                ).show()
                callback(AdLifecycleState.FAILED)
            }

            override fun onAdFailedToPlay(baseAd: BaseAd, adError: VungleError) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INTERSTITIAL_AD_DISPLAY_FAILED,
                    adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Toast.makeText(
                    activity, "VungleInterstitial ${adError.errorMessage}", Toast.LENGTH_SHORT
                ).show()
                callback(AdLifecycleState.FAILED)
            }

            override fun onAdImpression(baseAd: BaseAd) {
            }

            override fun onAdLeftApplication(baseAd: BaseAd) {
                callback(AdLifecycleState.FINISHED)
            }

            override fun onAdLoaded(baseAd: BaseAd) {
                interstitialAd.play()
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INTERSTITIAL_AD_LOADED)
            }

            override fun onAdStart(baseAd: BaseAd) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_INTERSTITIAL_AD_DISPLAYED,)
                callback(AdLifecycleState.FINISHED)
            }
        }

        interstitialAd.load()
    }

    fun loadRewardedAd(activity: Activity, placementId: String, callback: (AdLifecycleState) -> Unit) {
        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_REWARD_AD_LOAD)
        val rewardedAd = RewardedAd(activity, placementId, AdConfig())
        rewardedAd.adListener = object : BaseAdListener {
            override fun onAdClicked(baseAd: BaseAd) {
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdClicked:: ")
            }

            override fun onAdEnd(baseAd: BaseAd) {
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdEnd:: ")
                callback(AdLifecycleState.CLOSED)
            }

            override fun onAdFailedToLoad(baseAd: BaseAd, adError: VungleError) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_REWARD_AD_LOAD_FAILED,
                    adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdFailedToLoad:: ")
                Toast.makeText(
                    activity, "VungleRewarded ${adError.errorMessage}", Toast.LENGTH_SHORT
                ).show()
                callback(AdLifecycleState.FAILED)
            }

            override fun onAdFailedToPlay(baseAd: BaseAd, adError: VungleError) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_REWARD_AD_DISPLAY_FAILED,
                    adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                )
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdFailedToPlay:: ")
                Toast.makeText(
                    activity, "VungleRewarded ${adError.errorMessage}", Toast.LENGTH_SHORT
                ).show()
                callback(AdLifecycleState.FAILED)
            }

            override fun onAdImpression(baseAd: BaseAd) {
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdImpression:: ")
            }

            override fun onAdLeftApplication(baseAd: BaseAd) {
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdLeftApplication:: ")
            }

            override fun onAdLoaded(baseAd: BaseAd) {
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_REWARD_AD_LOADED)
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdLoaded:: ")
                rewardedAd.play()
            }

            override fun onAdStart(baseAd: BaseAd) {
                Log.d("CHECKVUNGLE", "Vungle Rewarded Ad clickeD onAdStart:: ")
                com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_REWARD_AD_DISPLAYED)
                callback(AdLifecycleState.FINISHED)
            }
        }
        rewardedAd.load()
    }

    private fun getBannerOrMRECAd(activity: Activity, placementId: String, type: Int): BannerAd {
        val bannerSize = if (type == com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.TYPE_AD_BANNER) {
            com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_BANNER_LOAD)
            BannerAdSize.BANNER
        } else {
            com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_MREC_LOAD)
            BannerAdSize.VUNGLE_MREC
        }

        val bannerAd = BannerAd(activity, placementId, bannerSize)
        bannerAd.adListener = object : BaseAdListener {
            override fun onAdClicked(baseAd: BaseAd) {

            }

            override fun onAdEnd(baseAd: BaseAd) {

            }

            override fun onAdFailedToLoad(baseAd: BaseAd, adError: VungleError) {
                if (type == com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.TYPE_AD_BANNER) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_BANNER_LOAD_FAILED,
                        adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                } else {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_MREC_LOAD_FAILED,
                        adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                }
                Toast.makeText(
                    activity, "VungleBan ${adError.errorMessage}", Toast.LENGTH_SHORT
                ).show()

                Log.d("CHECKVUNGLE", "Vungle Banner getBannerOrMRECAd  ${adError.errorMessage}")
            }

            override fun onAdFailedToPlay(baseAd: BaseAd, adError: VungleError) {
                if (type == com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.TYPE_AD_BANNER) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_BANNER_DISPLAY_FAILED,
                        adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                } else {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_MREC_DISPLAY_FAILED,
                        adError.errorMessage.toBundle(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                }
                Toast.makeText(
                    activity, "VungleBan ${adError.errorMessage}", Toast.LENGTH_SHORT
                ).show()
                Log.d("CHECKVUNGLE", "Vungle Banner ${adError.errorMessage}")
            }

            override fun onAdImpression(baseAd: BaseAd) {
            }

            override fun onAdLeftApplication(baseAd: BaseAd) {
            }

            override fun onAdLoaded(baseAd: BaseAd) {
                if (type == com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.TYPE_AD_BANNER) {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_BANNER_LOADED)
                } else {
                    com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics.LIFT_OFF_MREC_LOADED)
                }
                Log.d("CHECKVUNGLE", "Vungle Banner onAdLoaded")
            }

            override fun onAdStart(baseAd: BaseAd) {
            }
        }
        bannerAd.load()

        return bannerAd
    }

    fun getBannerAd(activity: Activity, placementId: String): BannerAd {
        return getBannerOrMRECAd(activity, placementId, com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.TYPE_AD_BANNER)
    }

    fun getMRECAd(activity: Activity, placementId: String): BannerAd {
        return getBannerOrMRECAd(activity, placementId, com.androidai.demo.avengerad.adnetwork.liftoff.LiftOff.TYPE_AD_MREC)
    }
}