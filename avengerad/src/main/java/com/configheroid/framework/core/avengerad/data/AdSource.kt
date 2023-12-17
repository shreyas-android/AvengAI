package com.configheroid.framework.core.avengerad.data

import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd

internal sealed class AdSource {

    data class ApplovinInterstitial(val interstitialAd: MaxInterstitialAd) : AdSource()

    data class ApplovinRewarded(val maxRewardedAd: MaxRewardedAd) : AdSource()
}