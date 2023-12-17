package com.cogniheroid.framework.feature.gemini

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
import com.cogniheroid.framework.feature.gemini.textgeneration.TextGenerationScreen
import com.cogniheroid.framework.ui.component.ConvertorButton

enum class GeminiAIRoute(val route: String) {
    GENERATE_TEXT("generateText"),
    HOME("home")
}

@Composable
fun CogniHeroidAIDemoScreen() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = GeminiAIRoute.HOME.route){

        composable(GeminiAIRoute.HOME.route){
            CogniHeroidAIDemoContainer(navController = navController)
        }

        composable(GeminiAIRoute.GENERATE_TEXT.route){
           TextGenerationScreen {
                navController.navigateUp()
           }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun  CogniHeroidAIDemoContainer(navController: NavController){
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
        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ConvertorButton(label = stringResource(id = R.string.title_text_generation)) {
                navController.navigate(GeminiAIRoute.GENERATE_TEXT.route)
            }
        }
    }
}