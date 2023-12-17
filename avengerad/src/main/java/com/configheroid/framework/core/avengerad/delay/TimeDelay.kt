package com.configheroid.framework.core.avengerad.delay

import android.util.Log
import com.configheroid.framework.core.avengerad.AvengerAd
import com.configheroid.framework.core.avengerad.data.AdLifecycleState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class TimeDelay(private val coroutineScope: CoroutineScope) {

    fun manualDelay(millis: Long, callback: () -> Unit) {
        coroutineScope.launch {
            delay(millis)
            callback()
        }
    }

    private suspend fun asyncDelayLoadAd(
        millis: Long,
        adLifecycleState: AdLifecycleState,
        callback: (com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState) -> Unit
    ) {

        Log.d("CHECKNOTREADY", "CHECKI THE NOT READY delayLoadAd:: = $adLifecycleState")
        when (adLifecycleState) {
            AdLifecycleState.FAILED -> {
                callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.FAILURE)
            }

            AdLifecycleState.FINISHED -> {
                delay(millis)
                Log.d(
                    "CHECKNOTREADY",
                    "CHECKI THE NOT READY delayLoadAd:: FINISHED:: = $adLifecycleState"
                )
                callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.SUCCESS)
            }

            AdLifecycleState.LOADED -> {

            }

            AdLifecycleState.COMPLETED -> {
                callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.SUCCESS)
            }

            AdLifecycleState.CLOSED -> {
                // callback()
            }

            AdLifecycleState.NOT_READY -> {
                Log.d("CHECKNOTREADY", "CHECKI THE NOT READY = $millis")
                delay(3000)
                Log.d("CHECKNOTREADY", "CHECKI THE NOT READY after = $millis")
                callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.FAILURE)
            }
        }

    }

    fun delayLoadAd(
        millis: Long,
        adLifecycleState: AdLifecycleState,
        callback: (com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState) -> Unit
    ) {
        coroutineScope.launch {
            when (adLifecycleState) {
                AdLifecycleState.FAILED -> {
                    callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.FAILURE)
                }

                AdLifecycleState.FINISHED -> {
                    delay(millis)
                    callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.SUCCESS)
                }

                AdLifecycleState.LOADED -> {

                }

                AdLifecycleState.COMPLETED -> {
                    callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.SUCCESS)
                }

                AdLifecycleState.CLOSED -> {
                    // callback()
                }

                AdLifecycleState.NOT_READY -> {
                    delay(3000)
                    callback(com.configheroid.framework.core.avengerad.AvengerAd.AdSuccessFailureState.FAILURE)
                }
            }
        }
    }
}