package com.cogniheroid.framework.feature.nlpai

import android.content.Context
import android.content.Intent
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer.EquationRecognizerViewModelFactory
import com.cogniheroid.framework.feature.nlpai.ui.generation.advancetextgeneration.AdvanceTextGenerationViewModelFactory
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.nutrient.NutrientAIViewModelFactory
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.FoodRecipeViewModelFactory
import com.cogniheroid.framework.feature.nlpai.ui.textgeneration.TextGenerationViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

object NLPAICore {

    lateinit var advanceTextGenerationViewModelFactory: AdvanceTextGenerationViewModelFactory

    lateinit var textGenerationViewModelFactory: TextGenerationViewModelFactory

    lateinit var nutrientAIViewModelFactory : NutrientAIViewModelFactory

    lateinit var equationRecognizerViewModelFactory : EquationRecognizerViewModelFactory

    lateinit var foodRecipeeViewModelFactory : FoodRecipeViewModelFactory

    internal val imageIntentFlow = MutableStateFlow<Intent?>(null)

    fun init(context : Context, apiKey:String, isDebug:Boolean, scope: CoroutineScope){
        val avengerAIManagerImpl = AvengerAIManager.getInstance(apiKey)
        advanceTextGenerationViewModelFactory =
            AdvanceTextGenerationViewModelFactory(avengerAIManagerImpl)
        textGenerationViewModelFactory =
            TextGenerationViewModelFactory(avengerAIManagerImpl)

        nutrientAIViewModelFactory = NutrientAIViewModelFactory(avengerAIManagerImpl)

        equationRecognizerViewModelFactory = EquationRecognizerViewModelFactory(avengerAIManagerImpl)

        foodRecipeeViewModelFactory = FoodRecipeViewModelFactory(avengerAIManagerImpl)
    }

    fun onImageAdded(intent: Intent){
        imageIntentFlow.value = intent
    }
}