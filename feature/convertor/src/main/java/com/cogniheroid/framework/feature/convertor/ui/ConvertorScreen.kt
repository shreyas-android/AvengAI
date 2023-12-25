package com.cogniheroid.framework.feature.convertor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cogniheroid.android.ad.ui.theme.ComposeUITheme
import com.cogniheroid.framework.feature.convertor.R
import com.cogniheroid.framework.ui.component.CustomButton
import com.cogniheroid.framework.feature.convertor.ui.datetimeconvertor.DateTimeConvertorScreen
import com.cogniheroid.framework.feature.convertor.ui.urlendecoder.URLEncoderDecoderScreen


enum class ConvertorRoute(val route: String) {
    URL_ENCODER_DECODER("urlEncoderDecoder"),
    DATE_TIME_CONVERTOR("dateTimeConvertor"),
    HOME("home")
}
@Composable
fun ConvertorScreen() {

    ComposeUITheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ConvertorRoute.HOME.route) {

            composable(ConvertorRoute.HOME.route) {
                ConvertorContainer(navController = navController)
            }

            composable(ConvertorRoute.URL_ENCODER_DECODER.route) {
                URLEncoderDecoderScreen {
                    navController.navigateUp()
                }
            }

            composable(ConvertorRoute.DATE_TIME_CONVERTOR.route) {
                DateTimeConvertorScreen {
                    navController.navigateUp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertorContainer(navController: NavController){
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
        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomButton(modifier = Modifier.padding(top = 32.dp),
                label = stringResource(id = R.string.title_url_encoder_decoder)) {
                navController.navigate(ConvertorRoute.URL_ENCODER_DECODER.route)
            }

            CustomButton(label = stringResource(id = R.string.title_date_time_converter)) {
                navController.navigate(ConvertorRoute.DATE_TIME_CONVERTOR.route)
            }
        }
    }
}