package com.cogniheroid.framework.feature.nlpai.ui.nutrichef

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cogniheroid.android.ad.ui.theme.ComposeUITheme
import com.cogniheroid.framework.feature.nlpai.R
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.nutrient.NutrientAIScreen
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.FoodRecipeScreen
import com.cogniheroid.framework.ui.component.AdUIContainer
import com.cogniheroid.framework.ui.component.CustomButton

enum class NutriChefRoute(val route : String) { NUTRIENT("nutrients"),
    RECIPE("recipe"),
    HOME("home")
}

@Composable
fun NutriChefScreen(onAddImage : () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    ComposeUITheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = NutriChefRoute.HOME.route) {

            composable(NutriChefRoute.HOME.route) {
                NutriChefContainer(navController = navController)
            }

            composable(NutriChefRoute.NUTRIENT.route) {
                NutrientAIScreen(onAddImage) {
                    navController.navigateUp()
                }
            }

            composable(NutriChefRoute.RECIPE.route) {
                FoodRecipeScreen(onAddImage) {
                    navController.navigateUp()
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NutriChefContainer(navController : NavController) {

    Scaffold(containerColor = MaterialTheme.colorScheme.background, topBar = {
        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_nutri_chef), fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface)
            })
        }
    }) {

        val context = LocalContext.current

        AdUIContainer(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            content = { modifier ->
                Column(
                    modifier = modifier
                        .padding(top = 24.dp)
                        .verticalScroll(rememberScrollState())
                    , horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    CustomButton(
                        modifier = Modifier.padding(top = 32.dp),
                        label = stringResource(id = R.string.title_nutrient_ai)) {
                        navController.navigate(NutriChefRoute.NUTRIENT.route)
                    }

                    CustomButton(label = stringResource(id = R.string.title_food_recipe)) {
                        navController.navigate(NutriChefRoute.RECIPE.route)
                    }
                }
            })
    }
}