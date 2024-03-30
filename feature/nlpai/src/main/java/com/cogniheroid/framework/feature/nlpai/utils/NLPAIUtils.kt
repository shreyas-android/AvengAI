package com.cogniheroid.framework.feature.nlpai.utils

import com.sparrow.framework.core.avengerad.AvengerAdCore

object NLPAIUtils {

    private const val TEST_AD_UNIT_ID_BANNER = "ca-app-pub-3940256099942544/6300978111"

    private const val NUTRI_CHEF_BANNER_1 = "ca-app-pub-7235511025002252/8908722774"
    private const val NUTRI_CHEF_BANNER_2 = "ca-app-pub-7235511025002252/6579385989"
    private const val NUTRI_CHEF_BANNER_3 = "ca-app-pub-7235511025002252/1828309108"

    private const val EQUATION_BANNER_1 = "ca-app-pub-7235511025002252/3995307449"
    private const val EQUATION_BANNER_2 = "ca-app-pub-7235511025002252/2682225777"

    private const val AVENG_AI_BANNER_1 = "ca-app-pub-7235511025002252/6118073750"
    private const val AVENG_AI_BANNER_2 = "ca-app-pub-7235511025002252/1847656255"
    private const val AVENG_AI_BANNER_3 = "ca-app-pub-7235511025002252/1282240548"




    private fun isDebuggable(): Boolean {
        return AvengerAdCore.isAdDebug

    }

    fun getNutriChefBannerAd1() : String {
       return if(isDebuggable()){
           TEST_AD_UNIT_ID_BANNER
        }else{
           NUTRI_CHEF_BANNER_1
        }
    }

    fun getNutriChefBannerAd2() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            NUTRI_CHEF_BANNER_2
        }
    }

    fun getNutriChefBannerAd3() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            NUTRI_CHEF_BANNER_3
        }
    }

    fun getEquationBannerAd1() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            EQUATION_BANNER_1
        }
    }

    fun getEquationBannerAd2() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            EQUATION_BANNER_2
        }
    }

    fun getAvengAIBannerAd1() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            AVENG_AI_BANNER_1
        }
    }

    fun getAvengAIBannerAd2() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            AVENG_AI_BANNER_2
        }
    }

    fun getAvengAIBannerAd3() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            AVENG_AI_BANNER_3
        }
    }
}