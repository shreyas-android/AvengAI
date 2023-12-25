package com.cogniheroid.android

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.cogniheroid.android.ad.ui.theme.AdGalaxyTheme
import com.cogniheroid.framework.feature.convertor.ui.ConvertorScreen


class MainActivity : ComponentActivity() {

    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT


        setContent {
            AdGalaxyTheme {
                ConvertorScreen()
            }
        }

    }
}

/*fun updatePreference(){
    val timeInMillis = AdGalaxyApplication.INSTANCE.pref.getLong(AdGalaxyApplication.prefTime, 1L)
    val type = AdGalaxyApplication.INSTANCE.pref.getInt(AdGalaxyApplication.prefAdUnitType, AdGalaxyAd.defaultType)
    val calendar = CalendarUtils.getCalendar()
    Log.d("CHECKPREF","CHECKIGN THE PREF = ${calendar.timeInMillis} and $timeInMillis")
    if (timeInMillis == 1L){
        val calendar = CalendarUtils.getCalendar()
        AdGalaxyApplication.INSTANCE.prefEditor.putLong(AdGalaxyApplication.prefTime, calendar.timeInMillis)
        AdGalaxyApplication.INSTANCE.prefEditor.putInt(AdGalaxyApplication.prefAdUnitType, AdGalaxyAd.TYPE_2)
        AdGalaxyApplication.INSTANCE.prefEditor.apply()
    }else if (timeInMillis>calendar.timeInMillis){
        if (type == AdGalaxyAd.TYPE_1){
            AdGalaxyApplication.INSTANCE.prefEditor.putInt(AdGalaxyApplication.prefAdUnitType, AdGalaxyAd.TYPE_2)
        }else{
            AdGalaxyApplication.INSTANCE.prefEditor.putInt(AdGalaxyApplication.prefAdUnitType, AdGalaxyAd.TYPE_1)
        }
        AdGalaxyApplication.INSTANCE.prefEditor.putLong(AdGalaxyApplication.prefTime, calendar.timeInMillis)
        AdGalaxyApplication.INSTANCE.prefEditor.apply()
    }
}


private fun getAndUpdateType(): Int {
    val type = AdGalaxyApplication.INSTANCE.pref.getInt(
        AdGalaxyApplication.prefAdUnitType,
        AdGalaxyAd.defaultType
    )

    val updatedType = checkAndGetType(type)

    AdGalaxyApplication.INSTANCE.prefEditor.putInt(
        AdGalaxyApplication.prefAdUnitType,
        updatedType
    )
    AdGalaxyApplication.INSTANCE.prefEditor.apply()

    return updatedType
}

private fun checkAndGetType(type:Int): Int {
    return when(type){
        AdGalaxyAd.TYPE_1 -> {
            AdGalaxyAd.TYPE_2
        }
        AdGalaxyAd.TYPE_2 -> {
            AdGalaxyAd.TYPE_3
        }
        AdGalaxyAd.TYPE_3 -> {
            AdGalaxyAd.TYPE_4
        }

        AdGalaxyAd.TYPE_4 -> {
            AdGalaxyAd.TYPE_5
        }

        AdGalaxyAd.TYPE_5 -> {
            AdGalaxyAd.TYPE_6
        }

        AdGalaxyAd.TYPE_6 -> {
            AdGalaxyAd.TYPE_1
        }

        else -> {
            AdGalaxyAd.defaultType
        }
    }
}
*/
