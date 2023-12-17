package com.configheroid.framework.feature.applovinscreen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cogniheroid.framework.feature.applovinscreen.R
import com.configheroid.framework.core.avengerad.AvengerAdCore
import com.configheroid.framework.core.avengerad.adnetwork.applovin.ApplovinState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun ApplovinRunScreen(activity:Activity) {

    val avengerAd= remember {
        com.configheroid.framework.core.avengerad.AvengerAdCore.getAvengerAd(CoroutineScope(Dispatchers.Main))
    }
    val applovinState: State<ApplovinState> = avengerAd.getApplovinState().collectAsState()

    val count = remember {
        mutableStateOf(0)
    }
    ConstraintLayout(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (banner1, button, container, textContainer, _) = createRefs()
        Column(modifier = Modifier
            .constrainAs(banner1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }) {

        }


        ButtonContainer(
            isEnabled = applovinState.value.enableLoadButton,
            Modifier
                .padding(top = 50.dp)
                .constrainAs(button) {
                    top.linkTo(banner1.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){
            avengerAd.runApplovinLoop(activity)
        }

        CounterView(applovinState.value.adShown,
            modifier = Modifier
                .constrainAs(container) {
                    top.linkTo(button.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth(),
            onAddClick = { count.value += 1 },
            onMinusClick = { count.value -= 1 })

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
                    applovinState.value.loadedAd
                ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(
                    id = R.string.desc_remaining_ad_count,
                    applovinState.value.remainingAd
                ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(
                    id = R.string.desc_not_ready_ad,
                    applovinState.value.notReady
                ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(
                    id = R.string.desc_load_failed,
                    applovinState.value.failedLoad
                ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(
                    id = R.string.desc_load_interstitial_count,
                    applovinState.value.loadInterstitial
                ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ButtonContainer(isEnabled:Boolean, modifier: Modifier, onClick:()->Unit) {
    val buttonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

        TextButton(
            enabled = isEnabled,
            colors = buttonColors, onClick = {
                onClick()
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