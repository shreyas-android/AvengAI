package com.androidai.demo.avengerad.data

import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd

internal sealed class AdSource {

    data class ApplovinInterstitial(val interstitialAd: MaxInterstitialAd) : AdSource()

    data class ApplovinRewarded(val maxRewardedAd: MaxRewardedAd) : AdSource()
}