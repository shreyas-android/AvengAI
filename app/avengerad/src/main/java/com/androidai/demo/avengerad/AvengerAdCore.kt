package com.androidai.demo.avengerad

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object AvengerAdCore {

    lateinit var firebaseAnalytics: FirebaseAnalytics

    var isAdDebug = true

    fun init(isDebug:Boolean) {
        firebaseAnalytics = Firebase.analytics
        isAdDebug = isDebug

    }
}