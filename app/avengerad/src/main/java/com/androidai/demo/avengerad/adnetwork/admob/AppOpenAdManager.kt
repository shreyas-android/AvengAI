package com.androidai.demo.avengerad.adnetwork.admob

import android.app.Activity
import android.content.Context
import com.androidai.demo.avengerad.analytics.AdGalaxyAnalytics
import com.androidai.demo.avengerad.analytics.toBundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

private class AppOpenAdManager {

        /** Request an ad. */
        fun loadAd(context: Context, activity: Activity) {
            val request = AdRequest.Builder().build()
            AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.AD_MOB_LOAD_APP_OPEN_AD)
            AppOpenAd.load(
                context, AdMob.getAppOpenAdUnitId(), request,
                object : AppOpenAd.AppOpenAdLoadCallback() {

                    override fun onAdLoaded(ad: AppOpenAd) {
                        // Called when an app open ad has loaded.
                        AdGalaxyAnalytics.logEvent(AdGalaxyAnalytics.AD_MOB_LOADED_APP_OPEN_AD)
                        ad.show(activity)


                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        // Called when an app open ad has failed to load.

                        AdGalaxyAnalytics.logEvent(
                            AdGalaxyAnalytics.AD_MOB_LOADED_APP_OPEN_AD,
                            loadAdError.message.toBundle(AdGalaxyAnalytics.ERROR_MESSAGE)
                        )
                    }
                })
        }
    }