package com.configheroid.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner


class AdGalaxyApplication : Application(), Application.ActivityLifecycleCallbacks,
    LifecycleObserver {

   private lateinit var  pref: SharedPreferences

   private lateinit var prefEditor: SharedPreferences.Editor


    companion object {
        lateinit var INSTANCE: AdGalaxyApplication

        private val prefName = "adgalaxy_app_pref"

        val prefAdUnitType = "ad_unit_type"
        val prefTime = "time"
    }



    override fun onCreate() {
        super.onCreate()
        INSTANCE = this


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
      //  avengerAd.onApplovinActivityHandling(activity)
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