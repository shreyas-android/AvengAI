package com.androidai.demo.avengerad.delay

import android.util.Log
import com.androidai.demo.avengerad.AvengerAd
import com.androidai.demo.avengerad.data.AdLifecycleState
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
        callback: (AvengerAd.AdSuccessFailureState) -> Unit
    ) {

        Log.d("CHECKNOTREADY", "CHECKI THE NOT READY delayLoadAd:: = $adLifecycleState")
        when (adLifecycleState) {
            AdLifecycleState.FAILED -> {
                callback(AvengerAd.AdSuccessFailureState.FAILURE)
            }

            AdLifecycleState.FINISHED -> {
                delay(millis)
                Log.d(
                    "CHECKNOTREADY",
                    "CHECKI THE NOT READY delayLoadAd:: FINISHED:: = $adLifecycleState"
                )
                callback(AvengerAd.AdSuccessFailureState.SUCCESS)
            }

            AdLifecycleState.LOADED -> {

            }

            AdLifecycleState.COMPLETED -> {
                callback(AvengerAd.AdSuccessFailureState.SUCCESS)
            }

            AdLifecycleState.CLOSED -> {
                // callback()
            }

            AdLifecycleState.NOT_READY -> {
                Log.d("CHECKNOTREADY", "CHECKI THE NOT READY = $millis")
                delay(3000)
                Log.d("CHECKNOTREADY", "CHECKI THE NOT READY after = $millis")
                callback(AvengerAd.AdSuccessFailureState.FAILURE)
            }
        }

    }

    fun delayLoadAd(
        millis: Long,
        adLifecycleState: AdLifecycleState,
        callback: (AvengerAd.AdSuccessFailureState) -> Unit
    ) {
        coroutineScope.launch {
            when (adLifecycleState) {
                AdLifecycleState.FAILED -> {
                    callback(AvengerAd.AdSuccessFailureState.FAILURE)
                }

                AdLifecycleState.FINISHED -> {
                    delay(millis)
                    callback(AvengerAd.AdSuccessFailureState.SUCCESS)
                }

                AdLifecycleState.LOADED -> {

                }

                AdLifecycleState.COMPLETED -> {
                    callback(AvengerAd.AdSuccessFailureState.SUCCESS)
                }

                AdLifecycleState.CLOSED -> {
                    // callback()
                }

                AdLifecycleState.NOT_READY -> {
                    delay(3000)
                    callback(AvengerAd.AdSuccessFailureState.FAILURE)
                }
            }
        }
    }
}