package com.jackson.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import co.notix.interstitial.InterstitialLoader
import co.notix.interstitial.NotixInterstitial
import com.jackson.framework.feature.convertor.ConvertorCore
import com.sparrow.framework.core.avengerad.data.model.AvengerAdData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AdGalaxyApplication : Application(), Application.ActivityLifecycleCallbacks, LifecycleObserver {

    private lateinit var pref : SharedPreferences

    private lateinit var prefEditor : SharedPreferences.Editor

    var interstitialLoader : InterstitialLoader? = null

    companion object {

        lateinit var INSTANCE : AdGalaxyApplication

        private val prefName = "adgalaxy_app_pref"

    }

    val globalDefaultScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        pref = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        prefEditor = pref.edit()

        registerActivityLifecycleCallbacks(this)

        interstitialLoader = NotixInterstitial.createLoader(7405513)
        interstitialLoader?.startLoading()

        ConvertorCore.init(
            this, BuildConfig.DEBUG, AvengerAdData(
                applovinKey = BuildConfig.versa_convertor_applovin_sdk_key,
                chartBoostAppId = BuildConfig.versa_convertor_helium_app_id,
                chartBoostAppSignature = BuildConfig.versa_convertor_helium_app_signature,
                startAppId = BuildConfig.versa_convertor_start_io_app_id,
                notixAppId = BuildConfig.versa_convertor_notix_app_id,
                notixAppToken = BuildConfig.versa_convertor_notix_app_token,
                applovinInterstitialList = listOf(
                    "03b7a0a222f9fe6e", "0811951a4a637908", "117772125159cc4f", "1feb8eb7ef84d565",
                    "31e03cbdbd710861", "32954122b91ae55b", "33a69d20daddf0bd", "46f76565c241677c",
                    "5b21bb42ea5c9d77", "7384ae9806c5cf3b", "7c21743130508f1d", "8a5eb68d9356288f",
                    "8fe5377f81f1004a", "9b25df11797a297b", "a1162d62956ddd9a", "ad410a46b53989e4",
                    "bac27a700f426f6e", "d601ed49687e80c5", "d7cf8a6ebedf2536", "efbd16dab3e8ea35"),
                moneTagInterstitialList = listOf(
                    "7405513", "7405534", "7405509", "7405510", "7405521", "7405535", "7405524",
                    "7405518", "7405512", "7405530", "7405519", "7405537", "7322350", "7405517",
                    "7405511", "7405527", "7405523", "7405532", "7405536", "7405533"),
                chartBoostMediationInterstitialList = listOf(
                    "startup", "Interstitial_10", "Interstitial_11", "Interstitial_12",
                    "Interstitial_13", "Interstitial_14", "Interstitial_15", "Interstitial_16",
                    "Interstitial_17", "Interstitial_18", "Interstitial_19", "Interstitial_20",
                    "Interstitial_8", "Interstitial_9", "gameOver", "gameScreen", "homeScreen",
                    "levelComplete", "levelStart", "mainMenu", "startup"),
                chartBoostInterstitialList = listOf(
                    "Interstitial", "Interstitial_2", "Interstitial_3", "Interstitial_4",
                    "Interstitial_5", "Interstitial_6", "Interstitial_7", "Interstitial_8",
                    "Interstitial_9", "Interstitial_10", "Interstitial_11", "Interstitial_12",
                    "Interstitial_13", "Interstitial_14", "Interstitial_15", "Interstitial_16",
                    "Interstitial_17", "Interstitial_18", "Interstitial_19", "Interstitial_20",
                    "Interstitial_21", "Interstitial_22", "Interstitial_23", "Interstitial_24",
                    "Interstitial_25", "Interstitial_26","Interstitial_27","Interstitial_28",
                    "Interstitial_29","Interstitial_30",
                ), startAppInterstitial = listOf(
                    "03b7a0a222f9fe6e", "0811951a4a637908", "117772125159cc4f", "1feb8eb7ef84d565",
                    "31e03cbdbd710861")), globalDefaultScope)

    }

    /** Inner class that loads and shows app open ads. */

    override fun onActivityCreated(activity : Activity, savedInstanceState : Bundle?) {
        Log.d("CHECKACTIVITY", "CHECKIGN YHRR ACTIVITY onActivityCreated:: = $activity")
    }

    override fun onActivityStarted(activity : Activity) {
        Log.d("CHECKACTIVITY", "CHECKIGN YHRR ACTIVITY onActivityStarted:: = $activity")
    }

    override fun onActivityResumed(activity : Activity) {
        Log.d(
            "CHECKACTIVITY",
            "CHECKIGN YHRR ACTIVITY onActivityResumed:: = ${activity.javaClass.name}")
        ConvertorCore.onAvengerAdActivityHandling(activity, globalDefaultScope)
    }

    override fun onActivityPaused(activity : Activity) {
    }

    override fun onActivityStopped(activity : Activity) {
    }

    override fun onActivitySaveInstanceState(activity : Activity, outState : Bundle) {
    }

    override fun onActivityDestroyed(activity : Activity) {
    }

}