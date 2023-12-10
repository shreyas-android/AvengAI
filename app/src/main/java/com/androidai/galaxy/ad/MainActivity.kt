package com.androidai.galaxy.ad

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.ViewModelProvider
import com.androidai.galaxy.ad.utils.CalendarUtils
import com.androidai.galaxy.ad.ui.theme.AdGalaxyTheme


class MainActivity : ComponentActivity() {

    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdGalaxyTheme {
                // A surface container using the 'background' color from the theme

                ConstraintLayout(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val (banner1, button, container, textContainer, banner2) = createRefs()
                    Column(modifier = Modifier
                        .constrainAs(banner1) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }) {

                    }


                    ButtonContainer(
                        Modifier
                            .padding(top = 50.dp)
                            .constrainAs(button) {
                                top.linkTo(banner1.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })

                    CounterView(AdGalaxyApplication.adGalaxyAd.adShownCount.collectAsState().value,
                        modifier = Modifier
                            .constrainAs(container) {
                                top.linkTo(button.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .fillMaxWidth(),
                        onAddClick = { mainViewModel.count.value += 1 },
                        onMinusClick = { mainViewModel.count.value -= 1 })

                    Column(modifier = Modifier
                        .constrainAs(textContainer) {
                            top.linkTo(container.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp)) {

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.desc_ad_loaded_count,
                                AdGalaxyApplication.adGalaxyAd.adSourceStackSize.collectAsState().value
                            ),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.desc_remaining_ad_count,
                                AdGalaxyApplication.adGalaxyAd.remainingLoadedAd.collectAsState().value
                            ),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.desc_not_ready_ad,
                                AdGalaxyApplication.adGalaxyAd.notReadySourceSize.collectAsState().value
                            ),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.desc_load_failed,
                                AdGalaxyApplication.adGalaxyAd.failedLoadCount.collectAsState().value
                            ),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.desc_load_interstitial_count,
                                AdGalaxyApplication.adGalaxyAd.loadInterstitialCount.collectAsState().value
                            ),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.desc_load_reward_count,
                                AdGalaxyApplication.adGalaxyAd.loadRewardCount.collectAsState().value
                            ),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )


                    }

                    Column(modifier = Modifier.constrainAs(banner2) {
                        bottom.linkTo(parent.bottom)
                    }) {

                    }
                }
            }
        }
    }

    @Composable
    fun ButtonContainer(modifier: Modifier) {
        val buttonColors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

            TextButton(
                enabled = AdGalaxyApplication.adGalaxyAd.shouldEnableLoadButton.collectAsState().value,
                colors = buttonColors, onClick = {
                    AdGalaxyApplication.adGalaxyAd.onApplovinSelected(this@MainActivity,
                        getAndUpdateType())

                },
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = stringResource(id = R.string.run_applovin),
                    fontSize = 20.sp
                )
            }

        }
    }

    @Composable
    fun CounterView(
        count: Int,
        modifier: Modifier,
        onAddClick: () -> Unit,
        onMinusClick: () -> Unit
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Icon(
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        onMinusClick()
                    }
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.ic_remove),
                contentDescription = "minus",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = count.toString(),
                fontSize = 64.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        onAddClick()
                    }
                    .padding(16.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = "add",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }


    }

    override fun onResume() {
        super.onResume()
       // updatePreference()
    }
}

fun updatePreference(){
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

