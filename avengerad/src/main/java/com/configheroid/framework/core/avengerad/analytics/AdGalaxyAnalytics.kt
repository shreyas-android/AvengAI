package com.configheroid.framework.core.avengerad.analytics

import android.os.Bundle
import com.configheroid.framework.core.avengerad.AvengerAdCore

internal object AdGalaxyAnalytics {

    const val AD_GALAXY_AD_INIT = "ad_galaxy_ad_init"

    const val IRON_SOURCE_INIT = "iron_source_init"
    const val LIFT_OFF_INIT = "lift_off_init"
    const val AD_MOB_INIT = "ad_mob_init"
    const val APPLOVIN_INIT = "applovin_init"
    const val CHART_BOOST_INIT = "chart_boost_init"
    const val IN_MOBI_INIT = "in_mobi_init"
    const val UNITY_INIT = "unity_init"

    const val IRON_SOURCE_INIT_COMPLETE = "iron_source_init_complete"
    const val LIFT_OFF_INIT_COMPLETE = "lift_off_init_complete"
    const val AD_MOB_INIT_COMPLETE = "ad_mob_init_complete"
    const val APPLOVIN_INIT_COMPLETE = "applovin_init_complete"
    const val CHART_BOOST_INIT_COMPLETE = "chart_boost_init_complete"
    const val IN_MOBI_INIT_COMPLETE = "in_mobi_init_complete"
    const val UNITY_INIT_COMPLETE = "unity_init_complete"

    const val ERROR_MESSAGE = "error_message"

    const val AD_MOB_LOAD_APP_OPEN_AD = "ad_mob_load_app_open_ad"
    const val AD_MOB_LOADED_APP_OPEN_AD = "ad_mob_loaded_app_open_ad"
    const val AD_MOB_APP_OPEN_CALLED = "ad_mob_app_open_called"

    const val APPLOVIN_APP_OPEN_CALLED = "applovin_app_open_called"
    const val APPLOVIN_LOAD_APP_OPEN_AD = "applovin_load_app_open_ad"
    const val APPLOVIN_LOADED_APP_OPEN_AD = "applovin_loaded_app_open_ad"
    const val APPLOVIN_DISPLAYED_APP_OPEN_AD = "applovin_displayed_app_open_ad"

    const val LOAD_BUTTON_CLICKED = "load_button_clicked"

    const val AD_TYPE = "ad_type"

    const val LOAD_REWARD_AD = "load_reward_ad"
    const val LOAD_INTERSTITIAL_AD = "load_interstitial_ad"
    const val LOAD_REWARD_INTERSTITIAL_AD = "load_reward_interstitial_ad"

    const val AD_MOB_REWARD_AD_LOAD = "ad_mob_reward_ad_load"
    const val AD_MOB_REWARD_AD_LOADED = "ad_mob_reward_ad_loaded"
    const val AD_MOB_REWARD_AD_LOAD_FAILED = "ad_mob_reward_ad_load_failed"

    const val AD_MOB_INTERSTITIAL_AD_LOAD = "ad_mob_interstitial_ad_load"
    const val AD_MOB_INTERSTITIAL_AD_LOADED = "ad_mob_interstitial_ad_loaded"
    const val AD_MOB_INTERSTITIAL_AD_LOAD_FAILED = "ad_mob_interstitial_ad_load_failed"

    const val AD_MOB_REWARD_INTERSTITIAL_AD_LOAD = "ad_mob_reward_interstitial_ad_load"
    const val AD_MOB_REWARD_INTERSTITIAL_AD_LOADED = "ad_mob_reward_interstitial_ad_loaded"
    const val AD_MOB_REWARD_INTERSTITIAL_AD_LOAD_FAILED = "ad_mob_reward_interstitial_ad_load_failed"

    const val AD_MOB_BANNER_LOAD = "ad_mob_banner_load"
    const val AD_MOB_BANNER_LOADED = "ad_mob_banner_loaded"
    const val AD_MOB_BANNER_LOAD_FAILED = "ad_mob_banner_load_failed"

    const val APPLOVIN_REWARD_AD_LOAD = "applovin_reward_ad_load"
    const val APPLOVIN_REWARD_AD_LOADED = "applovin_reward_ad_loaded"
    const val APPLOVIN_REWARD_AD_LOAD_FAILED = "applovin_reward_ad_load_failed"
    const val APPLOVIN_REWARD_AD_DISPLAYED = "applovin_reward_ad_displayed"
    const val APPLOVIN_REWARD_AD_DISPLAY_FAILED = "applovin_reward_ad_display_failed"

    const val APPLOVIN_INTERSTITIAL_AD_LOAD = "applovin_interstitial_ad_load"
    const val APPLOVIN_INTERSTITIAL_AD_LOADED = "applovin_interstitial_ad_loaded"
    const val APPLOVIN_INTERSTITIAL_AD_LOAD_FAILED = "applovin_interstitial_ad_load_failed"
    const val APPLOVIN_INTERSTITIAL_AD_DISPLAYED = "applovin_interstitial_ad_displayed"
    const val APPLOVIN_INTERSTITIAL_AD_DISPLAY_FAILED = "applovin_interstitial_ad_display_failed"

    const val APPLOVIN_REWARD_INTERSTITIAL_AD_LOAD = "applovin_reward_interstitial_ad_load"
    const val APPLOVIN_REWARD_INTERSTITIAL_AD_LOADED = "applovin_reward_interstitial_ad_loaded"
    const val APPLOVIN_REWARD_INTERSTITIAL_AD_LOAD_FAILED = "applovin_reward_interstitial_ad_load_failed"

    const val APPLOVIN_BANNER_LOAD = "applovin_banner_load"
    const val APPLOVIN_BANNER_LOADED = "applovin_banner_loaded"
    const val APPLOVIN_BANNER_LOAD_FAILED = "applovin_banner_load_failed"
    const val APPLOVIN_BANNER_DISPLAYED = "applovin_banner_displayed"
    const val APPLOVIN_BANNER_DISPLAY_FAILED = "applovin_banner_display_failed"

    const val APPLOVIN_MREC_LOADED = "applovin_mrec_loaded"
    const val APPLOVIN_MREC_LOAD_FAILED = "applovin_mrec_load_failed"
    const val APPLOVIN_MREC_LOAD = "applovin_mrec_load"
    const val APPLOVIN_MREC_DISPLAYED = "applovin_mrec_displayed"
    const val APPLOVIN_MREC_DISPLAY_FAILED = "applovin_mrec_display_failed"

    const val CHART_BOOST_REWARD_AD_LOAD = "chart_boost_reward_ad_load"
    const val CHART_BOOST_REWARD_AD_LOADED = "chart_boost_reward_ad_loaded"
    const val CHART_BOOST_REWARD_AD_LOAD_FAILED = "chart_boost_reward_ad_load_failed"
    const val CHART_BOOST_REWARD_AD_DISPLAYED = "chart_boost_reward_ad_displayed"
    const val CHART_BOOST_REWARD_AD_DISPLAY_FAILED = "chart_boost_reward_ad_display_failed"

    const val CHART_BOOST_INTERSTITIAL_AD_LOAD = "chart_boost_interstitial_ad_load"
    const val CHART_BOOST_INTERSTITIAL_AD_LOADED = "chart_boost_interstitial_ad_loaded"
    const val CHART_BOOST_INTERSTITIAL_AD_LOAD_FAILED = "chart_boost_interstitial_ad_load_failed"
    const val CHART_BOOST_INTERSTITIAL_AD_DISPLAYED = "chart_boost_interstitial_ad_displayed"
    const val CHART_BOOST_INTERSTITIAL_AD_DISPLAY_FAILED = "chart_boost_interstitial_ad_display_failed"

    const val CHART_BOOST_REWARD_INTERSTITIAL_AD_LOAD = "chart_boost_reward_interstitial_ad_load"
    const val CHART_BOOST_REWARD_INTERSTITIAL_AD_LOADED = "chart_boost_reward_interstitial_ad_loaded"
    const val CHART_BOOST_REWARD_INTERSTITIAL_AD_LOAD_FAILED = "chart_boost_reward_interstitial_ad_load_failed"
    const val CHART_BOOST_REWARD_INTERSTITIAL_AD_DISPLAYED = "chart_boost_reward_interstitial_ad_displayed"
    const val CHART_BOOST_REWARD_INTERSTITIAL_AD_DISPLAY_FAILED = "chart_boost_reward_interstitial_ad_display_failed"

    const val CHART_BOOST_BANNER_LOAD = "chart_boost_banner_load"
    const val CHART_BOOST_BANNER_LOADED = "chart_boost_banner_loaded"
    const val CHART_BOOST_BANNER_LOAD_FAILED = "chart_boost_banner_load_failed"
    const val CHART_BOOST_BANNER_DISPLAYED = "chart_boost_banner_displayed"
    const val CHART_BOOST_BANNER_DISPLAY_FAILED = "chart_boost_banner_display_failed"

    const val CHART_BOOST_MREC_LOAD = "chart_boost_mrec_load"
    const val CHART_BOOST_MREC_LOADED = "chart_boost_mrec_loaded"
    const val CHART_BOOST_MREC_LOAD_FAILED = "chart_boost_mrec_load_failed"
    const val CHART_BOOST_MREC_DISPLAYED = "chart_boost_mrec_displayed"
    const val CHART_BOOST_MREC_DISPLAY_FAILED = "chart_boost_mrec_display_failed"

    const val IN_MOBI_REWARD_AD_LOAD = "in_mobi_reward_ad_load"
    const val IN_MOBI_REWARD_AD_LOADED = "in_mobi_reward_ad_loaded"
    const val IN_MOBI_REWARD_AD_LOAD_FAILED = "in_mobi_reward_ad_load_failed"
    const val IN_MOBI_REWARD_AD_DISPLAYED = "in_mobi_reward_ad_displayed"
    const val IN_MOBI_REWARD_AD_DISPLAY_FAILED = "in_mobi_reward_ad_display_failed"

    const val IN_MOBI_INTERSTITIAL_AD_LOAD = "in_mobi_interstitial_ad_load"
    const val IN_MOBI_INTERSTITIAL_AD_LOADED = "in_mobi_interstitial_ad_loaded"
    const val IN_MOBI_INTERSTITIAL_AD_LOAD_FAILED = "in_mobi_interstitial_ad_load_failed"
    const val IN_MOBI_INTERSTITIAL_AD_DISPLAYED = "in_mobi_interstitial_ad_displayed"
    const val IN_MOBI_INTERSTITIAL_AD_DISPLAY_FAILED = "in_mobi_interstitial_ad_display_failed"

    const val IN_MOBI_REWARD_INTERSTITIAL_AD_LOAD = "in_mobi_reward_interstitial_ad_load"
    const val IN_MOBI_REWARD_INTERSTITIAL_AD_LOADED = "in_mobi_reward_interstitial_ad_loaded"
    const val IN_MOBI_REWARD_INTERSTITIAL_AD_LOAD_FAILED = "in_mobi_reward_interstitial_ad_load_failed"
    const val IN_MOBI_REWARD_INTERSTITIAL_AD_DISPLAYED = "in_mobi_reward_interstitial_ad_displayed"
    const val IN_MOBI_REWARD_INTERSTITIAL_AD_DISPLAY_FAILED = "in_mobi_reward_interstitial_ad_display_failed"

    const val IN_MOBI_BANNER_LOAD = "in_mobi_banner_load"
    const val IN_MOBI_BANNER_LOADED = "in_mobi_banner_loaded"
    const val IN_MOBI_BANNER_LOAD_FAILED = "in_mobi_banner_load_failed"
    const val IN_MOBI_BANNER_DISPLAYED = "in_mobi_banner_displayed"
    const val IN_MOBI_BANNER_DISPLAY_FAILED = "in_mobi_banner_display_failed"

    const val IN_MOBI_MREC_LOAD = "in_mobi_mrec_load"
    const val IN_MOBI_MREC_LOADED = "in_mobi_mrec_loaded"
    const val IN_MOBI_MREC_LOAD_FAILED = "in_mobi_mrec_load_failed"
    const val IN_MOBI_MREC_DISPLAYED = "in_mobi_mrec_displayed"
    const val IN_MOBI_MREC_DISPLAY_FAILED = "in_mobi_mrec_display_failed"

    const val UNITY_REWARD_AD_LOAD = "unity_reward_ad_load"
    const val UNITY_REWARD_AD_LOADED = "unity_reward_ad_loaded"
    const val UNITY_REWARD_AD_LOAD_FAILED = "unity_reward_ad_load_failed"
    const val UNITY_REWARD_AD_DISPLAYED = "unity_reward_ad_displayed"
    const val UNITY_REWARD_AD_DISPLAY_FAILED = "unity_reward_ad_display_failed"

    const val UNITY_INTERSTITIAL_AD_LOAD = "unity_interstitial_ad_load"
    const val UNITY_INTERSTITIAL_AD_LOADED = "unity_interstitial_ad_loaded"
    const val UNITY_INTERSTITIAL_AD_LOAD_FAILED = "unity_interstitial_ad_load_failed"
    const val UNITY_INTERSTITIAL_AD_DISPLAYED = "unity_interstitial_ad_displayed"
    const val UNITY_INTERSTITIAL_AD_DISPLAY_FAILED = "unity_interstitial_ad_display_failed"

    const val UNITY_BANNER_LOAD = "unity_banner_load"
    const val UNITY_BANNER_LOADED = "unity_banner_loaded"
    const val UNITY_BANNER_LOAD_FAILED = "unity_banner_load_failed"
    const val UNITY_BANNER_DISPLAYED = "unity_banner_displayed"
    const val UNITY_BANNER_DISPLAY_FAILED = "unity_banner_display_failed"

    const val UNITY_MREC_LOAD = "unity_mrec_load"
    const val UNITY_MREC_LOADED = "unity_mrec_loaded"
    const val UNITY_MREC_LOAD_FAILED = "unity_mrec_load_failed"
    const val UNITY_MREC_DISPLAYED = "unity_mrec_displayed"
    const val UNITY_MREC_DISPLAY_FAILED = "unity_mrec_display_failed"

    const val IRON_SOURCE_REWARD_AD_LOAD = "iron_source_reward_ad_load"
    const val IRON_SOURCE_REWARD_AD_LOADED = "iron_source_reward_ad_loaded"
    const val IRON_SOURCE_REWARD_AD_LOAD_FAILED = "iron_source_reward_ad_load_failed"
    const val IRON_SOURCE_REWARD_AD_DISPLAYED = "iron_source_reward_ad_displayed"
    const val IRON_SOURCE_REWARD_AD_DISPLAY_FAILED = "iron_source_reward_ad_display_failed"

    const val IRON_SOURCE_INTERSTITIAL_AD_LOAD = "iron_source_interstitial_ad_load"
    const val IRON_SOURCE_INTERSTITIAL_AD_LOADED = "iron_source_interstitial_ad_loaded"
    const val IRON_SOURCE_INTERSTITIAL_AD_LOAD_FAILED = "iron_source_interstitial_ad_load_failed"
    const val IRON_SOURCE_INTERSTITIAL_AD_DISPLAYED = "iron_source_interstitial_ad_displayed"
    const val IRON_SOURCE_INTERSTITIAL_AD_DISPLAY_FAILED = "iron_source_interstitial_ad_display_failed"

    const val IRON_SOURCE_BANNER_LOAD = "iron_source_banner_load"
    const val IRON_SOURCE_BANNER_LOADED = "iron_source_banner_loaded"
    const val IRON_SOURCE_BANNER_LOAD_FAILED = "iron_source_banner_load_failed"
    const val IRON_SOURCE_BANNER_DISPLAYED = "iron_source_banner_displayed"
    const val IRON_SOURCE_BANNER_DISPLAY_FAILED = "iron_source_banner_display_failed"

    const val IRON_SOURCE_MREC_LOAD = "iron_source_mrec_load"
    const val IRON_SOURCE_MREC_LOADED = "iron_source_mrec_loaded"
    const val IRON_SOURCE_MREC_LOAD_FAILED = "iron_source_mrec_load_failed"
    const val IRON_SOURCE_MREC_DISPLAYED = "iron_source_mrec_displayed"
    const val IRON_SOURCE_MREC_DISPLAY_FAILED = "iron_source_mrec_display_failed"

    const val LIFT_OFF_REWARD_AD_LOAD = "lift_off_reward_ad_load"
    const val LIFT_OFF_REWARD_AD_LOADED = "lift_off_reward_ad_loaded"
    const val LIFT_OFF_REWARD_AD_LOAD_FAILED = "lift_off_reward_ad_display_failed"
    const val LIFT_OFF_REWARD_AD_DISPLAY_FAILED = "lift_off_reward_ad_display_failed"
    const val LIFT_OFF_REWARD_AD_DISPLAYED = "lift_off_reward_ad_displayed"

    const val LIFT_OFF_INTERSTITIAL_AD_LOAD = "lift_off_interstitial_ad_load"
    const val LIFT_OFF_INTERSTITIAL_AD_LOADED = "lift_off_interstitial_ad_loaded"
    const val LIFT_OFF_INTERSTITIAL_AD_LOAD_FAILED = "lift_off_interstitial_ad_load_failed"
    const val LIFT_OFF_INTERSTITIAL_AD_DISPLAYED = "lift_off_interstitial_ad_displayed"
    const val LIFT_OFF_INTERSTITIAL_AD_DISPLAY_FAILED = "lift_off_interstitial_ad_display_failed"


    const val LIFT_OFF_BANNER_LOAD = "lift_off_banner_load"
    const val LIFT_OFF_BANNER_LOADED = "lift_off_banner_loaded"
    const val LIFT_OFF_BANNER_LOAD_FAILED = "lift_off_banner_load_failed"
    const val LIFT_OFF_BANNER_DISPLAYED = "lift_off_banner_displayed"
    const val LIFT_OFF_BANNER_DISPLAY_FAILED = "lift_off_banner_display_failed"

    const val LIFT_OFF_MREC_LOAD = "lift_off_mrec_load"
    const val LIFT_OFF_MREC_LOADED = "lift_off_mrec_loaded"
    const val LIFT_OFF_MREC_LOAD_FAILED = "lift_off_mrec_load_failed"
    const val LIFT_OFF_MREC_DISPLAYED = "lift_off_mrec_displayed"
    const val LIFT_OFF_MREC_DISPLAY_FAILED = "lift_off_mrec_display_failed"



    fun logEvent(name: String, params: Bundle? = null) {
        com.configheroid.framework.core.avengerad.AvengerAdCore.firebaseAnalytics.logEvent(name, params)
    }
}

fun String.toBundle(key: String): Bundle {
    val bundle = Bundle()
    bundle.putString(key, this)
    return bundle
}

fun Int.toBundle(key: String): Bundle {
    val bundle = Bundle()
    bundle.putInt(key, this)
    return bundle
}