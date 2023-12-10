package com.androidai.demo.avengerad.adnetwork.ironsource

object IronSource {

    private const val durationBetweenAd = 15 * 1000L
    private const val loadAdDuration = 30 * 1000L

    const val APP_KEY = "1b789031d"

    private const val TYPE_AD_REWARDED = 1
    private const val TYPE_AD_INTERSTITIAL = 2

    private const val AD_UNIT_ID_INTERSTITIAL = "DefaultInterstitial"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "Startup"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "Home_Screen"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "Main_Menu"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "Game_Screen"

    private const val AD_UNIT_ID_REWARDED = "DefaultRewardedVideo"
    private const val AD_UNIT_ID_REWARDED_2 = "Home_Screen"
    private const val AD_UNIT_ID_REWARDED_3 = "Main_Menu"
    private const val AD_UNIT_ID_REWARDED_4 = "Game_Screen"
    private const val AD_UNIT_ID_REWARDED_5 = "Startup"

    private const val AD_UNIT_ID_BANNER_1 = "DefaultBanner"
    private const val AD_UNIT_ID_BANNER_2 = "Home_Screen"

    private val rewardList = listOf(
        AD_UNIT_ID_REWARDED,
        AD_UNIT_ID_REWARDED_2,
        AD_UNIT_ID_REWARDED_3,
        AD_UNIT_ID_REWARDED_4,
        AD_UNIT_ID_REWARDED_5
    )

    private val interstitialList = listOf(
        AD_UNIT_ID_INTERSTITIAL,
        AD_UNIT_ID_INTERSTITIAL_2,
        AD_UNIT_ID_INTERSTITIAL_3,
        AD_UNIT_ID_INTERSTITIAL_4,
        AD_UNIT_ID_INTERSTITIAL_5
    )

    fun getAdTypeList() = listOf(TYPE_AD_INTERSTITIAL, TYPE_AD_REWARDED)

    fun getBannerAdUnitId(type: Int): String {
        return if (type == 0) {
            AD_UNIT_ID_BANNER_1
        } else {
            AD_UNIT_ID_BANNER_2
        }
    }

    fun getInterstitialAdUnitId(): String {
        return interstitialList.random()
    }

    fun getRewardedAdUnitId(): String {
        return rewardList.random()
    }

    fun getDurationBetweenAd() = durationBetweenAd

    fun getLoadAdDuration() = loadAdDuration
}