package com.configheroid.framework.core.avengerad.adnetwork.applovin

import com.configheroid.framework.core.avengerad.AvengerAd
import com.configheroid.framework.core.avengerad.AvengerAdCore


internal object Applovin {

    private const val durationBetweenAd = 15 * 1000L
    private const val loadNextAdDuration = 18 * 1000L


    private val originalList = AvengerAdCore.getAvengerAdData()?.applovinInterstitialList ?: listOf()

    private val interstitialList_1 = checkAndGetSubList(originalList, 0, 9)

    private val interstitialList_2 = checkAndGetSubList(originalList, 10, 19)

    private val interstitialList_3 = checkAndGetSubList(originalList, 20, 29)

    private val interstitialList_4 = checkAndGetSubList(originalList, 30, 39)

    private val interstitialList_5 = checkAndGetSubList(originalList, 40, 49)

    private val interstitialList_6 = checkAndGetSubList(originalList, 50, 59)

    fun getInterstitialList(type: Int): List<String> {
        return when(type){
            AvengerAd.TYPE_1 -> return interstitialList_1
            AvengerAd.TYPE_2 -> return interstitialList_2
            AvengerAd.TYPE_3 -> return interstitialList_3
            AvengerAd.TYPE_4 -> return interstitialList_4
            AvengerAd.TYPE_5 -> return interstitialList_5
            AvengerAd.TYPE_6 -> return interstitialList_6
            else -> {
                interstitialList_1
            }
        }
    }

    private fun checkAndGetSubList(list: List<String>, fromIndex:Int, toIndex:Int): List<String> {
        return if (list.isEmpty()){
            list
        }else{
            if (fromIndex >= list.size) {
                list
            }else{
                if (toIndex >= list.size){
                    list.subList(fromIndex, list.size)
                }else{
                    list.subList(fromIndex, toIndex)
                }
            }
        }
    }

    fun getDurationBetweenAd(): Long {
        return durationBetweenAd
    }

    fun getLoadAdDuration(): Long {
        return loadNextAdDuration
    }

}