package com.androidai.demo.avengerad.adnetwork.inmobi

object InMobi {

    private const val durationBetweenAd = 15 * 1000L
    private const val loadAdDuration = 30 * 1000L

    private const val TYPE_AD_REWARDED = 1
    private const val TYPE_AD_INTERSTITIAL = 2

    const val APP_ID = "e27b7ed5b7bd4c7991e89865bd0582de"

    private const val AD_UNIT_ID_INTERSTITIAL = 1692290266047
    private const val AD_UNIT_ID_INTERSTITIAL_2 = 1694437543408
    private const val AD_UNIT_ID_INTERSTITIAL_3 = 1695079097654
    private const val AD_UNIT_ID_INTERSTITIAL_4 = 1692576711469
    private const val AD_UNIT_ID_INTERSTITIAL_5 = 1696411934016

    private const val AD_UNIT_ID_REWARDED = 1692812478205
    private const val AD_UNIT_ID_REWARDED_2 = 1694625379011
    private const val AD_UNIT_ID_REWARDED_3 = 1694450716715
    private const val AD_UNIT_ID_REWARDED_4 = 1694554217242
    private const val AD_UNIT_ID_REWARDED_5 = 1695462723044

    private const val AD_UNIT_ID_BANNER_1 = 1695779736432
    private const val AD_UNIT_ID_BANNER_2 = 1694699949834

    private const val AD_UNIT_ID_OFF_STREAM = 1695248086501

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


    fun getBannerAdUnitId(type: Int): Long {
        return if (type == 0) {
            AD_UNIT_ID_BANNER_1
        } else {
            AD_UNIT_ID_BANNER_2
        }
    }

    fun getOffStreamAdUnitId(): Long {
        return AD_UNIT_ID_OFF_STREAM
    }

    fun getInterstitialAdUnitId(): Long {
        return interstitialList.random()
    }

    fun getRewardedAdUnitId(): Long {
        return rewardList.random()
    }

    fun getDurationBetweenAd() = durationBetweenAd

    fun getLoadAdDuration() = loadAdDuration

}