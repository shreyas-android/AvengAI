package com.cogniheroid.framework.feature.nlpai.ui.generation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.cogniheroid.framework.feature.nlpai.R
import com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration.AdvanceTextGeneration
import com.cogniheroid.framework.feature.nlpai.ui.generation.textgeneration.TextGenerationScreen
import com.cogniheroid.framework.ui.component.AdUIContainer
import com.cogniheroid.framework.ui.component.CustomButton

enum class AvengerAIRoute(val route: String) {
    GENERATE_TEXT("generateText"),
    GENERATE_ADVANCE_TEXT("generateAdvanceText"),
    HOME("home")
}

@Composable
fun AvengerAIDemoScreen(onAddImage: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    ComposeUITheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = AvengerAIRoute.HOME.route) {

            composable(AvengerAIRoute.HOME.route) {
                AvengAIDemoContainer(navController = navController)
            }

            composable(AvengerAIRoute.GENERATE_TEXT.route) {
                TextGenerationScreen {
                    navController.navigateUp()
                }
            }

            composable(AvengerAIRoute.GENERATE_ADVANCE_TEXT.route) {
                AdvanceTextGeneration(onAddImage){
                    navController.navigateUp()
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun  AvengAIDemoContainer(navController: NavController){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
            Surface(tonalElevation = 3.dp) {
                TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                    Text(
                        text = stringResource(id = R.string.title_cogni_heroid_demo),
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                    )
                })
            }
        }) {


        AdUIContainer(modifier = Modifier
            .fillMaxSize()
            .padding(it), content = { childModifier->
            Column(
                modifier = childModifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomButton(modifier = Modifier.padding(top = 32.dp), label = stringResource(id = R.string.title_text_generation)) {
                    navController.navigate(AvengerAIRoute.GENERATE_TEXT.route)
                }

                CustomButton(label = stringResource(id = R.string.title_advance_text_generation)) {
                    navController.navigate(AvengerAIRoute.GENERATE_ADVANCE_TEXT.route)
                }
            }
        })

    }
}