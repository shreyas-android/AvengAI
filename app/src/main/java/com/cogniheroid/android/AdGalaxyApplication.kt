package com.cogniheroid.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.cogniheroid.framework.feature.nlpai.NLPAICore
import com.cogniheroid.framework.feature.chat.ChatCore
import com.cogniheroid.framework.feature.imageai.ImageAICore
import com.cogniheroid.framework.feature.inspireai.InspireAICore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class AdGalaxyApplication : Application(), Application.ActivityLifecycleCallbacks,
    LifecycleObserver {

   private lateinit var  pref: SharedPreferences

   private lateinit var prefEditor: SharedPreferences.Editor


    companion object {
        lateinit var INSTANCE: AdGalaxyApplication

        private val prefName = "adgalaxy_app_pref"

    }


    private val globalDefaultScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        pref = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        prefEditor = pref.edit()

        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        ImageAICore.init(BuildConfig.cogni_heroid_ai_api_key)
        NLPAICore.init(this, BuildConfig.cogni_heroid_ai_api_key,  BuildConfig.DEBUG,
            globalDefaultScope)

        ChatCore.init(BuildConfig.cogni_heroid_ai_api_key, this)

        InspireAICore.init(BuildConfig.cogni_heroid_ai_api_key)
    }


    /** Inner class that loads and shows app open ads. */



    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {

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