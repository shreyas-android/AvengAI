package com.androidai.demo.avengerad.adnetwork.liftoff

object LiftOff {

    private const val durationBetweenAd = 15 * 1000L
    private const val loadAdDuration = 30 * 1000L

    const val APP_ID = "64f33685aeaa53940aa417eb"

    private const val TYPE_AD_REWARDED = 1
    private const val TYPE_AD_INTERSTITIAL = 2
    const val TYPE_AD_BANNER = 3
    const val TYPE_AD_MREC = 4

    private const val AD_UNIT_ID_INTERSTITIAL = "INTERSTITIAL-2754080"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "INTERSTITIAL_2-8471168"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "INTERSTITIAL_3-3357493"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "INTERSTITIAL_4-0723447"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "INTERSTITIAL_5-3270393"

    private const val AD_UNIT_ID_REWARDED = "REWARDED-5953336"
    private const val AD_UNIT_ID_REWARDED_2 = "REWARDED_2-8477853"
    private const val AD_UNIT_ID_REWARDED_3 = "REWARDED_3-84505104"
    private const val AD_UNIT_ID_REWARDED_4 = "REWARDED_4-7551477"
    private const val AD_UNIT_ID_REWARDED_5 = "REWARDED_5-9066295"

    private const val AD_UNIT_ID_BANNER_1 = "BANNER-1640649"
    private const val AD_UNIT_ID_BANNER_2 = "BANNER_2-3140062"

    private const val AD_UNIT_ID_MREC_1 = "MREC-7446790"
    private const val AD_UNIT_ID_MREC_2 = "MREC_2-0983979"

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

    fun getMrecAdUnitId(type: Int): String {
        return if (type == 0) {
            AD_UNIT_ID_MREC_1
        } else {
            AD_UNIT_ID_MREC_2
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