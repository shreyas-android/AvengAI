package com.androidai.galaxy.ad

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.androidai.demo.avengerad.AvengerAd
import com.androidai.demo.avengerad.AvengerAdCore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class AdGalaxyApplication : Application(), Application.ActivityLifecycleCallbacks,
    LifecycleObserver {

   lateinit var  pref: SharedPreferences

   lateinit var prefEditor: SharedPreferences.Editor

   private lateinit var avengerAd: AvengerAd


    companion object {
        lateinit var INSTANCE: AdGalaxyApplication

        private val prefName = "adgalaxy_app_pref"

        val prefAdUnitType = "ad_unit_type"
        val prefTime = "time"
    }



    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AvengerAdCore.init(BuildConfig.DEBUG)
        avengerAd = AvengerAd(CoroutineScope(Dispatchers.Main))
        avengerAd.init(this)

        pref = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        prefEditor = pref.edit()

        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

    }


    /** Inner class that loads and shows app open ads. */



    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d("AdGalaxyApplication", "onActivityCreated $activity")
        avengerAd.onApplovinActivityHandling(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}