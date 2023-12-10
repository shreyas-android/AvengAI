package com.androidai.demo.avengerad.adnetwork.applovin


internal object Applovin {

    private val durationBetweenAd = 15 * 1000L
    private val loadNextAdDuration = 18 * 1000L

    private const val AD_UNIT_ID_INTERSTITIAL = "18685dc7f35c28d4"
    private const val AD_UNIT_ID_INTERSTITIAL_2 = "bab5c7f1a250eb13"
    private const val AD_UNIT_ID_INTERSTITIAL_3 = "2523ecd53d12ae66"
    private const val AD_UNIT_ID_INTERSTITIAL_4 = "f7116858e73c1e15"
    private const val AD_UNIT_ID_INTERSTITIAL_5 = "c0fef33aa10d2e27"
    private const val AD_UNIT_ID_INTERSTITIAL_6 = "e035bb7deecae546"
    private const val AD_UNIT_ID_INTERSTITIAL_7 = "57e649dec31cb9a6"
    private const val AD_UNIT_ID_INTERSTITIAL_8 = "9f3bd7772ca9cab3"
    private const val AD_UNIT_ID_INTERSTITIAL_9 = "508d3ae63e79a36c"
    private const val AD_UNIT_ID_INTERSTITIAL_10 = "f71b4a22708b27d5"

    private const val AD_UNIT_ID_INTERSTITIAL_11 = "88208c123609d814"
    private const val AD_UNIT_ID_INTERSTITIAL_12 = "86e21d4c3ad08781"
    private const val AD_UNIT_ID_INTERSTITIAL_13 = "3cd8915e604cb9e6"
    private const val AD_UNIT_ID_INTERSTITIAL_14 = "ad65f35b78ade45e"
    private const val AD_UNIT_ID_INTERSTITIAL_15 = "d4ac890dda447e99"
    private const val AD_UNIT_ID_INTERSTITIAL_16 = "51ea242b40ce7a43"
    private const val AD_UNIT_ID_INTERSTITIAL_17 = "66526f58b64526a1"
    private const val AD_UNIT_ID_INTERSTITIAL_18 = "1557e72e07fedabd"
    private const val AD_UNIT_ID_INTERSTITIAL_19 = "cb739e59e4814487"
    private const val AD_UNIT_ID_INTERSTITIAL_20 = "4eeae4c92918f82d"

    private const val AD_UNIT_ID_INTERSTITIAL_21 = "03d6225237004d03"
    private const val AD_UNIT_ID_INTERSTITIAL_22 = "e1931f65412fd795"
    private const val AD_UNIT_ID_INTERSTITIAL_23 = "ef1fc79e77b23a3f"
    private const val AD_UNIT_ID_INTERSTITIAL_24 = "02573b0ed28bacc2"
    private const val AD_UNIT_ID_INTERSTITIAL_25 = "54b1ac760c80d63c"
    private const val AD_UNIT_ID_INTERSTITIAL_26 = "abc30d0d99a7932b"
    private const val AD_UNIT_ID_INTERSTITIAL_27 = "de2472563df8b99d"
    private const val AD_UNIT_ID_INTERSTITIAL_28 = "51cf9ffa36a5d347"
    private const val AD_UNIT_ID_INTERSTITIAL_29 = "9902eb3df1b5500c"
    private const val AD_UNIT_ID_INTERSTITIAL_30 = "9b01901544e8124f"

    private const val AD_UNIT_ID_INTERSTITIAL_31 = "010165dd67e88374"
    private const val AD_UNIT_ID_INTERSTITIAL_32 = "9593614a125ac3a5"
    private const val AD_UNIT_ID_INTERSTITIAL_33 = "0f8854d0dec66af7"
    private const val AD_UNIT_ID_INTERSTITIAL_34 = "321c683d07dc3976"
    private const val AD_UNIT_ID_INTERSTITIAL_35 = "8566f0efec93d2f1"
    private const val AD_UNIT_ID_INTERSTITIAL_36 = "edd928e873c7d504"
    private const val AD_UNIT_ID_INTERSTITIAL_37 = "a40c2e517d92e8a1"
    private const val AD_UNIT_ID_INTERSTITIAL_38 = "c86fa949dcc1e78b"
    private const val AD_UNIT_ID_INTERSTITIAL_39 = "87fca1d529355100"
    private const val AD_UNIT_ID_INTERSTITIAL_40 = "fe77c40a94fe26b4"

    private const val AD_UNIT_ID_INTERSTITIAL_41 = "dd0b768cbdabbc58"
    private const val AD_UNIT_ID_INTERSTITIAL_42 = "106bf90960fbecb5"
    private const val AD_UNIT_ID_INTERSTITIAL_43 = "834fc5320f016831"
    private const val AD_UNIT_ID_INTERSTITIAL_44 = "d0af1ebb64c7464d"
    private const val AD_UNIT_ID_INTERSTITIAL_45 = "d27dcdc955e7e28f"
    private const val AD_UNIT_ID_INTERSTITIAL_46 = "6518d32c1b89df02"
    private const val AD_UNIT_ID_INTERSTITIAL_47 = "c27dd871bb5122fa"
    private const val AD_UNIT_ID_INTERSTITIAL_48 = "843779a4aad65fcc"
    private const val AD_UNIT_ID_INTERSTITIAL_49 = "f777cd6114031ebc"
    private const val AD_UNIT_ID_INTERSTITIAL_50 = "2e959c63f9c96263"

    private const val AD_UNIT_ID_INTERSTITIAL_51 = "ed32ea02dd0fecb1"
    private const val AD_UNIT_ID_INTERSTITIAL_52 = "67288496a10599b9"
    private const val AD_UNIT_ID_INTERSTITIAL_53 = "f0bb8c7a9a2234b8"
    private const val AD_UNIT_ID_INTERSTITIAL_54 = "77410dccc8faf0a7"
    private const val AD_UNIT_ID_INTERSTITIAL_55 = "684d59e56e944267"
    private const val AD_UNIT_ID_INTERSTITIAL_56 = "0aee4c2a1d27349e"
    private const val AD_UNIT_ID_INTERSTITIAL_57 = "f5a77d6310f446b9"
    private const val AD_UNIT_ID_INTERSTITIAL_58 = "35a2cba5d0e468e8"
    private const val AD_UNIT_ID_INTERSTITIAL_59 = "2f8662a9019821b5"
    private const val AD_UNIT_ID_INTERSTITIAL_60 = "97a9793384ee5679"


    private const val AD_UNIT_ID_BANNER_1 = "2dac67aba566d7f0"
    private const val AD_UNIT_ID_BANNER_2 = "71fcb260730c5a5e"

    private const val AD_UNIT_ID_MREC_1 = "df8cc6fbd99768eb"
    private const val AD_UNIT_ID_MREC_2 = "134ca499c4cbfdec"

    private const val AD_UNIT_ID_APP_OPEN = "36d8380c71c05902"

    private val interstitialList_1 = listOf(
        AD_UNIT_ID_INTERSTITIAL,
        AD_UNIT_ID_INTERSTITIAL_2,
        AD_UNIT_ID_INTERSTITIAL_3,
        AD_UNIT_ID_INTERSTITIAL_4,
        AD_UNIT_ID_INTERSTITIAL_5,
        AD_UNIT_ID_INTERSTITIAL_6,
        AD_UNIT_ID_INTERSTITIAL_7,
        AD_UNIT_ID_INTERSTITIAL_8,
        AD_UNIT_ID_INTERSTITIAL_9,
        AD_UNIT_ID_INTERSTITIAL_10,
    )

    private val interstitialList_2 = listOf(
        AD_UNIT_ID_INTERSTITIAL_11,
        AD_UNIT_ID_INTERSTITIAL_12,
        AD_UNIT_ID_INTERSTITIAL_13,
        AD_UNIT_ID_INTERSTITIAL_14,
        AD_UNIT_ID_INTERSTITIAL_15,
        AD_UNIT_ID_INTERSTITIAL_16,
        AD_UNIT_ID_INTERSTITIAL_17,
        AD_UNIT_ID_INTERSTITIAL_18,
        AD_UNIT_ID_INTERSTITIAL_19,
        AD_UNIT_ID_INTERSTITIAL_20,

        )

    private val interstitialList_3 = listOf(
        AD_UNIT_ID_INTERSTITIAL_21,
        AD_UNIT_ID_INTERSTITIAL_22,
        AD_UNIT_ID_INTERSTITIAL_23,
        AD_UNIT_ID_INTERSTITIAL_24,
        AD_UNIT_ID_INTERSTITIAL_25,
        AD_UNIT_ID_INTERSTITIAL_26,
        AD_UNIT_ID_INTERSTITIAL_27,
        AD_UNIT_ID_INTERSTITIAL_28,
        AD_UNIT_ID_INTERSTITIAL_29,
        AD_UNIT_ID_INTERSTITIAL_30,
    )

    private val interstitialList_4 = listOf(
        AD_UNIT_ID_INTERSTITIAL_31,
        AD_UNIT_ID_INTERSTITIAL_32,
        AD_UNIT_ID_INTERSTITIAL_33,
        AD_UNIT_ID_INTERSTITIAL_34,
        AD_UNIT_ID_INTERSTITIAL_35,
        AD_UNIT_ID_INTERSTITIAL_36,
        AD_UNIT_ID_INTERSTITIAL_37,
        AD_UNIT_ID_INTERSTITIAL_38,
        AD_UNIT_ID_INTERSTITIAL_39,
        AD_UNIT_ID_INTERSTITIAL_40,
    )

    private val interstitialList_5 = listOf(
        AD_UNIT_ID_INTERSTITIAL_41,
        AD_UNIT_ID_INTERSTITIAL_42,
        AD_UNIT_ID_INTERSTITIAL_43,
        AD_UNIT_ID_INTERSTITIAL_44,
        AD_UNIT_ID_INTERSTITIAL_45,
        AD_UNIT_ID_INTERSTITIAL_46,
        AD_UNIT_ID_INTERSTITIAL_47,
        AD_UNIT_ID_INTERSTITIAL_48,
        AD_UNIT_ID_INTERSTITIAL_49,
        AD_UNIT_ID_INTERSTITIAL_50,
    )

    private val interstitialList_6 = listOf(
        AD_UNIT_ID_INTERSTITIAL_51,
        AD_UNIT_ID_INTERSTITIAL_52,
        AD_UNIT_ID_INTERSTITIAL_53,
        AD_UNIT_ID_INTERSTITIAL_54,
        AD_UNIT_ID_INTERSTITIAL_55,
        AD_UNIT_ID_INTERSTITIAL_56,
        AD_UNIT_ID_INTERSTITIAL_57,
        AD_UNIT_ID_INTERSTITIAL_58,
        AD_UNIT_ID_INTERSTITIAL_59,
        AD_UNIT_ID_INTERSTITIAL_60,
    )

    fun getInterstitialList(type: Int): List<String> {
        return when(type){
            AdGalaxyAd.TYPE_1 -> return interstitialList_1
            AdGalaxyAd.TYPE_2 -> return interstitialList_2
            AdGalaxyAd.TYPE_3 -> return interstitialList_3
            AdGalaxyAd.TYPE_4 -> return interstitialList_4
            AdGalaxyAd.TYPE_5 -> return interstitialList_5
            AdGalaxyAd.TYPE_6 -> return interstitialList_6
            else -> {
                interstitialList_1
            }
        }
    }

    fun getBannerAdUnitId(type: Int): String {
        return if (type == 0) {
            AD_UNIT_ID_BANNER_1
        } else {
            AD_UNIT_ID_BANNER_2
        }
    }

    fun getMrecAdUnitId(type: Int): String {
        return if (type == 0) {
            AD_UNIT_ID_MREC_1
        } else {
            AD_UNIT_ID_MREC_2
        }
    }

    fun getInterstitialAdUnitId(): String {
        return interstitialList_1.random()
    }


    fun getAppOpenAdUnitId(): String {
        return AD_UNIT_ID_APP_OPEN
    }

    fun getDurationBetweenAd(): Long {
        return durationBetweenAd
    }

    fun getLoadAdDuration(): Long {
        return loadNextAdDuration
    }

}