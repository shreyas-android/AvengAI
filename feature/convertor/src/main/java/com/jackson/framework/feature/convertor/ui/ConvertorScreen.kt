package com.jackson.framework.feature.convertor.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jackson.framework.feature.convertor.R
import com.sparrow.framework.ui.component.CustomButton
import com.sparrow.framework.ui.theme.ComposeUITheme
import com.jackson.framework.feature.convertor.ui.datetimeconvertor.DateTimeConvertorScreen
import com.jackson.framework.feature.convertor.ui.urlendecoder.URLEncoderDecoderScreen
import com.jackson.framework.feature.convertor.utils.ConvertorUtils
import com.jackson.framework.feature.convertor.widget.AdBannerView
import com.sparrow.framework.core.avengerad.AvengerAd
import com.sparrow.framework.core.avengerad.AvengerAdCore
import com.sparrow.framework.feature.avengeradscreen.AvengerAdNavigation
import com.sparrow.framework.feature.avengeradscreen.AvengerAdRunScreen

enum class ConvertorRoute(val route: String) {
    URL_ENCODER_DECODER("urlEncoderDecoder"),
    DATE_TIME_CONVERTOR("dateTimeConvertor"),
    APPLOVIN_SCREEN("applovinRunScreen"),
    HOME("home")
}
@Composable
fun ConvertorScreen(activity: Activity) {

    val coroutineScope = rememberCoroutineScope()
    val avengerAd = remember {
        AvengerAdCore.getAvengerAd(coroutineScope)
    }
    ComposeUITheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ConvertorRoute.HOME.route) {

            composable(ConvertorRoute.HOME.route) {
                ConvertorContainer(activity, avengerAd, navController = navController)
            }

            composable(ConvertorRoute.URL_ENCODER_DECODER.route) {
                URLEncoderDecoderScreen(navController, activity, avengerAd) {
                    navController.navigateUp()
                }
            }

            composable(ConvertorRoute.DATE_TIME_CONVERTOR.route) {
                DateTimeConvertorScreen(navController, activity, avengerAd) {
                    navController.navigateUp()
                }
            }

            composable(ConvertorRoute.APPLOVIN_SCREEN.route){
                AvengerAdNavigation(activity = activity)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertorContainer(activity: Activity, avengerAd: AvengerAd, navController: NavController){
    val context = LocalContext.current
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
            Surface(tonalElevation = 3.dp) {
                TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                    Text(
                        text = stringResource(id = R.string.title_convertor),
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                    )
                })
            }
        }) {

        ConstraintLayout(modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(it)) {
            val (banner1, container, banner2) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(container) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 24.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                AdBannerView(
                    modifier = Modifier.padding(top = 16.dp, bottom = 100.dp), activity = activity,
                    avengerAd = avengerAd, bannerId = ConvertorUtils.BANNER_AD_UNIT_ID_1,
                    mRECId = ConvertorUtils.MREC_AD_UNIT_ID_1)

                CustomButton(
                    modifier = Modifier.padding(top = 32.dp),
                    label = stringResource(id = R.string.title_url_encoder_decoder)) {
                    navController.navigate(ConvertorRoute.URL_ENCODER_DECODER.route)
                }

                CustomButton(label = stringResource(id = R.string.title_date_time_converter)) {
                    navController.navigate(ConvertorRoute.DATE_TIME_CONVERTOR.route)
                }

                AdBannerView(
                    modifier = Modifier.padding(top = 100.dp), activity = activity,
                    avengerAd = avengerAd, bannerId = ConvertorUtils.BANNER_AD_UNIT_ID_2,
                    mRECId = ConvertorUtils.MREC_AD_UNIT_ID_2)
            }


        }
    }
}

