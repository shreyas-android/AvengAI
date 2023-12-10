package com.androidai.demo.avengerad.adnetwork.meta

object MetaAudience {

    const val TYPE_AD_REWARDED = 1
    const val TYPE_AD_INTERSTITIAL = 2
    const val TYPE_AD_REWARDED_INTERSTITIAL = 3


    private const val AD_UNIT_ID_INTERSTITIAL = "Interstitial_Android"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "Interstitial_Android"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "Interstitial_Android"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "Interstitial_Android"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "Interstitial_Android"

    private const val AD_UNIT_ID_REWARDED = "Rewarded_Android"
    private const val AD_UNIT_ID_REWARDED_2 = "Rewarded_Android"
    private const val AD_UNIT_ID_REWARDED_3 = "Rewarded_Android"
    private const val AD_UNIT_ID_REWARDED_4 = "Rewarded_Android"
    private const val AD_UNIT_ID_REWARDED_5 = "Rewarded_Android"

    private const val AD_UNIT_ID_BANNER = "AndroidBanner"

    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL= "ca-app-pub-1164908821267698/8334447364"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_2= "ca-app-pub-1164908821267698/3687845091"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_3= "ca-app-pub-1164908821267698/2374763428"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_4= "ca-app-pub-1164908821267698/8485672360"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_5= "ca-app-pub-1164908821267698/9494705541"

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

    val rewardedInterstitialList = listOf(
        AD_UNIT_ID_REWARDED_INTERSTITIAL,
        AD_UNIT_ID_REWARDED_INTERSTITIAL_2,
        AD_UNIT_ID_REWARDED_INTERSTITIAL_3,
        AD_UNIT_ID_REWARDED_INTERSTITIAL_4,
        AD_UNIT_ID_REWARDED_INTERSTITIAL_5
    )

    fun getAdTypeList() = listOf(TYPE_AD_INTERSTITIAL, TYPE_AD_REWARDED, TYPE_AD_REWARDED_INTERSTITIAL)

}