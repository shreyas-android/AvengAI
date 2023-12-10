package com.androidai.demo.avengerad.adnetwork.unity

object Unity {

    private const val durationBetweenAd = 15 * 1000L
    private const val loadAdDuration = 30 * 1000L

    private const val TYPE_AD_REWARDED = 1
    private const val TYPE_AD_INTERSTITIAL = 2
    private const val TYPE_AD_REWARDED_INTERSTITIAL = 3

    const val GAME_ID = "5410722"

    // Unit ads
    private const val AD_UNIT_ID_INTERSTITIAL = "Interstitial_2"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "Interstitial_4"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "Interstitial_5"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "Interstitial_1"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "Interstitial_6"

    private const val AD_UNIT_ID_REWARDED = "Reward_1"
    private const val AD_UNIT_ID_REWARDED_2 = "Reward_2"
    private const val AD_UNIT_ID_REWARDED_3 = "Reward_3"
    private const val AD_UNIT_ID_REWARDED_4 = "Reward_4"
    private const val AD_UNIT_ID_REWARDED_5 = "Reward_5"

    private const val AD_UNIT_ID_BANNER_1 = "Banner_2"
    private const val AD_UNIT_ID_BANNER_2 = "Banner_3"

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

    fun getAdTypeList() = listOf(TYPE_AD_INTERSTITIAL, TYPE_AD_REWARDED, TYPE_AD_REWARDED_INTERSTITIAL)

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