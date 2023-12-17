package com.configheroid.framework.core.avengerad.adnetwork.applovin

import android.content.Context
import com.configheroid.framework.core.avengerad.analytics.toBundle
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAppOpenAd

private class ApplovinAppOpenManager {

        fun loadAd(context: Context, adOpenUnitId:String) {
            com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_LOAD_APP_OPEN_AD)
            val appOpenAd = MaxAppOpenAd(adOpenUnitId, context)
            appOpenAd.setListener(object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd) {
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_LOADED_APP_OPEN_AD)
                    appOpenAd.showAd()
                }

                override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_LOADED_APP_OPEN_AD,
                        error.message.toBundle(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                }

                override fun onAdDisplayed(ad: MaxAd) {}
                override fun onAdClicked(ad: MaxAd) {}
                override fun onAdHidden(ad: MaxAd) {

                }

                override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                    com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.logEvent(
                        com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.APPLOVIN_DISPLAYED_APP_OPEN_AD,
                        error.message.toBundle(com.configheroid.framework.core.avengerad.analytics.AdGalaxyAnalytics.ERROR_MESSAGE)
                    )
                }
            })
            appOpenAd.loadAd()
        }
    }