package com.androidai.demo.avengerad.adnetwork.chartboost

object ChartBoost {

    private const val durationBetweenAd = 15 * 1000L
    private const val loadAdDuration = 30 * 1000L

    const val APP_ID = "6505d2e52ba67a72809e49ff"
    const val APP_SIGNATURE = "f93585ba2d6a54e50c6be4858c1adf12200951bb"

    const val TYPE_AD_REWARDED = 1
    const val TYPE_AD_INTERSTITIAL = 2

    private const val AD_UNIT_ID_INTERSTITIAL = "gameScreen"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "homeScreen"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "levelStart"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "mainMenu"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "startup"

    private const val AD_UNIT_ID_REWARDED = "gameOver"
    private const val AD_UNIT_ID_REWARDED_2 = "levelComplete"
    private const val AD_UNIT_ID_REWARDED_3 = "Reward_3"
    private const val AD_UNIT_ID_REWARDED_4 = "Reward_4"
    private const val AD_UNIT_ID_REWARDED_5 = "Reward_5"

    private const val AD_UNIT_ID_BANNER = "Banner1"
    private const val AD_UNIT_ID_BANNER_2 = "Banner2"

    val rewardList = listOf(
        AD_UNIT_ID_REWARDED,
        AD_UNIT_ID_REWARDED_2,
        AD_UNIT_ID_REWARDED_3,
        AD_UNIT_ID_REWARDED_4,
        AD_UNIT_ID_REWARDED_5
    )

    val interstitialList = listOf(
        AD_UNIT_ID_INTERSTITIAL,
        AD_UNIT_ID_INTERSTITIAL_2,
        AD_UNIT_ID_INTERSTITIAL_3,
        AD_UNIT_ID_INTERSTITIAL_4,
        AD_UNIT_ID_INTERSTITIAL_5
    )

    fun getAdTypeList() = listOf(TYPE_AD_INTERSTITIAL, TYPE_AD_REWARDED)

    fun getBannerAdUnitId(type: Int): String {
        return if (type == 0) {
            AD_UNIT_ID_BANNER
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