package com.configheroid.framework.core.avengerad.adnetwork.admob

import com.configheroid.framework.core.avengerad.AvengerAdCore


object AdMob {

    private val durationBetweenAd = 1 * 60 * 1000L
    private val loadAdDuration = 3 * 60 * 1000L

    const val TYPE_AD_REWARDED = 1
    const val TYPE_AD_INTERSTITIAL = 2
    const val TYPE_AD_REWARDED_INTERSTITIAL = 3


    private const val TEST_AD_UNIT_ID_APP_OPEN = "ca-app-pub-3940256099942544/3419835294"
    private const val AD_UNIT_APP_OPEN = "ca-app-pub-1164908821267698/5589946094"

    private const val TEST_AD_UNIT_ID_REWARDED = "ca-app-pub-3940256099942544/5224354917"
    private const val AD_UNIT_ID_REWARDED = "ca-app-pub-1164908821267698/2083065487"
    private const val AD_UNIT_ID_REWARDED_2 = "ca-app-pub-1164908821267698/2619203799"
    private const val AD_UNIT_ID_REWARDED_3 = "ca-app-pub-1164908821267698/8299987455"
    private const val AD_UNIT_ID_REWARDED_4 = "ca-app-pub-1164908821267698/6986905782"
    private const val AD_UNIT_ID_REWARDED_5 = "ca-app-pub-1164908821267698/5043268727"

    private const val TEST_AD_UNIT_ID_BANNER = "ca-app-pub-3940256099942544/6300978111"
    private const val AD_UNIT_ID_BANNER_1 = "ca-app-pub-1164908821267698/5246369156"
    private const val AD_UNIT_ID_BANNER_2 = "ca-app-pub-1164908821267698/2922741532"

    private const val TEST_AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
    private const val AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-1164908821267698/9529191101"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "ca-app-pub-1164908821267698/3239232466"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "ca-app-pub-1164908821267698/1052052826"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "ca-app-pub-1164908821267698/6642209289"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "ca-app-pub-1164908821267698/8738971153"

    private const val TEST_AD_UNIT_ID_REWARDED_INTERSTITIAL= "ca-app-pub-3940256099942544/5354046379"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL= "ca-app-pub-1164908821267698/8216109432"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_2= "ca-app-pub-1164908821267698/4360742444"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_3= "ca-app-pub-1164908821267698/7669432067"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_4= "ca-app-pub-1164908821267698/1254436641"
    private const val AD_UNIT_ID_REWARDED_INTERSTITIAL_5= "ca-app-pub-1164908821267698/5329127611"

    private val defaultRewardedAdUnitList = listOf(
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_2,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_3,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_4,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_5
    )

    private val defaultInterstitialAdUnitList = listOf(
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_INTERSTITIAL,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_INTERSTITIAL_2,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_INTERSTITIAL_3,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_INTERSTITIAL_4,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_INTERSTITIAL_5
    )

    private val defaultRewardedInterstitialAdUnitList = listOf(
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_INTERSTITIAL,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_INTERSTITIAL_2,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_INTERSTITIAL_3,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_INTERSTITIAL_4,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_REWARDED_INTERSTITIAL_5
    )


    private fun getRewardedAdUnitId(): String {
        return if (com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.isDebuggable()) {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TEST_AD_UNIT_ID_REWARDED
        } else {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getProductionRewardAdUnitId()
        }
    }

    private fun getProductionRewardAdUnitId(): String {
        return com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.defaultRewardedAdUnitList.random()
    }

    fun getBannerAdUnitId(type: Int): String {
        return if (com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.isDebuggable()) {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TEST_AD_UNIT_ID_BANNER
        } else {
            if (type == 0){
                com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_BANNER_1
            }else{
                com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_ID_BANNER_2
            }
        }
    }

    private fun getInterstitialAdUnitId(): String {
        return if (com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.isDebuggable()) {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TEST_AD_UNIT_ID_INTERSTITIAL
        } else {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getProductionInterstitialAdUnitId()
        }
    }

    private fun getRewardedInterstitialAdUnitId(): String {
        return if (com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.isDebuggable()) {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TEST_AD_UNIT_ID_REWARDED_INTERSTITIAL
        } else {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getProductionRewardedInterstitialAdUnitId()
        }
    }

    private fun getProductionInterstitialAdUnitId(): String {
        return com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.defaultInterstitialAdUnitList.random()
    }

    private fun getProductionRewardedInterstitialAdUnitId(): String {
        return com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.defaultRewardedInterstitialAdUnitList.random()
    }

    private fun isDebuggable(): Boolean {
        return com.configheroid.framework.core.avengerad.AvengerAdCore.isAdDebug

    }

    fun getTypedAdUnitId(type: Int): String {
        return when (type) {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TYPE_AD_REWARDED -> com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getRewardedAdUnitId()
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TYPE_AD_INTERSTITIAL -> com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getInterstitialAdUnitId()
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TYPE_AD_REWARDED_INTERSTITIAL -> com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getRewardedInterstitialAdUnitId()
            else -> com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.getRewardedAdUnitId()
        }
    }


    fun getAppOpenAdUnitId(): String {
        return if (com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.isDebuggable()) {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TEST_AD_UNIT_ID_APP_OPEN
        } else {
            com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.AD_UNIT_APP_OPEN
        }
    }

    fun getAdTypeList() = listOf(
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TYPE_AD_INTERSTITIAL,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TYPE_AD_REWARDED,
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.TYPE_AD_REWARDED_INTERSTITIAL
    )

    fun getDurationBetweenAd() =
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.durationBetweenAd

    fun getLoadAdDuration() =
        com.configheroid.framework.core.avengerad.adnetwork.admob.AdMob.loadAdDuration
}