package com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cogniheroid.framework.core.ai.AvengerAIManager
import com.cogniheroid.framework.core.ai.data.model.ModelInput
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.uistate.FoodRecipeUIEffect
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.uistate.FoodRecipeUIEvent
import com.cogniheroid.framework.feature.nlpai.ui.nutrichef.recipe.uistate.FoodRecipeUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodRecipeViewModel(private val avengerAIManager:AvengerAIManager): ViewModel() {

    private val inputField = MutableStateFlow("")

    private var result = MutableStateFlow("")

    private var isModelStartedGeneratingText = MutableStateFlow(false)

    private var imageListFlow:MutableStateFlow<List<Bitmap>> = MutableStateFlow(listOf())

    val foodRecipeUIState = combine(inputField, result, isModelStartedGeneratingText, imageListFlow) { inputText, outputText, isGenerating, bitmapList ->
        FoodRecipeUIState(inputText, outputText, isGenerating, bitmapList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        FoodRecipeUIState("", "", false, listOf())
    )

    private val foodRecipeUIEffectChannel = Channel<FoodRecipeUIEffect>()
    val foodRecipeUIEffectFlow = foodRecipeUIEffectChannel.receiveAsFlow()

    fun performIntent(foodRecipeUIEvent: FoodRecipeUIEvent){
        when(foodRecipeUIEvent){
            is FoodRecipeUIEvent.GenerateText -> {
                isModelStartedGeneratingText.value = true
                clearResult()

                val inputText = getDefaultPrompt(foodRecipeUIEvent.text)
                generateTextAndUpdateResult(imageListFlow.value, inputText,
                    foodRecipeUIEvent.defaultErrorMessage)
            }
            is FoodRecipeUIEvent.InputText -> {
                inputField.value = foodRecipeUIEvent.text
            }
            FoodRecipeUIEvent.ClearText -> {
                inputField.value = ""
            }

            is FoodRecipeUIEvent.RemoveImage -> {
                val mutableList = imageListFlow.value.toMutableList()
                mutableList.remove(foodRecipeUIEvent.image)
                imageListFlow.value = mutableList

            }

            is FoodRecipeUIEvent.AddImage -> {
                foodRecipeUIEvent.image?.let {
                    val  mutableList = imageListFlow.value.toMutableList()
                    mutableList.add(foodRecipeUIEvent.image)
                    imageListFlow.value = mutableList
                }

            }

            is FoodRecipeUIEvent.OnOpenImagePicker -> {
                setSideEffect(FoodRecipeUIEffect.ShowImagePicker)
            }
        }
    }

    private fun generateTextAndUpdateResult(images: List<Bitmap>, text:String, defaultErrorMessage:String) {
        viewModelScope.launch {

            avengerAIManager.generateTextStreamContent(getModelInputList(images, text),
                defaultErrorMessage)
                .collectLatest {
                    Log.d("CHECKGENERATE","CHECKIGN THE GENERATE = $it")
                    result.value += it ?: ""
                    isModelStartedGeneratingText.value = false
                }
        }
    }

    private fun getModelInputList(images: List<Bitmap>, text:String):List<ModelInput>{
        val modelInputList = mutableListOf<ModelInput>()
        images.forEach {
            modelInputList.add(ModelInput.Image(true, it))
        }
        modelInputList.add(ModelInput.Text(true, text))
        return modelInputList
    }

    private fun clearResult(){
        result.value = ""
    }

    private fun setSideEffect(foodRecipeUIEffect: FoodRecipeUIEffect){
        viewModelScope.launch {
            foodRecipeUIEffectChannel.send(foodRecipeUIEffect)
        }
    }

    private fun getDefaultPrompt(input:String) : String {
        return """
          You are an expert in nutritionist where you need to accurately see the food items from the image if exists or in this input $input , identify the food in the image accurately 
          if exists and give the recipe, ingredients, perfect quantity of ingredients and step by step procedure to do that food. Your data should be more accurate. It should match the value in internet. Also
           provide the output as below format. Please follow the below format for generated text
          
          Item 1 name(Bold)
      
          Ingredients : 
          
          1.  
          2.  
          ---
          
          Procedure : 
          
          Step 1 - 
          Step 2 -
          Step 3 - 
          ----
          
          If another food exist
          
          Item 2 name(Bold) 
      
          Ingredients : 
          
          1.  
          2.  
          ---
          
          Procedure : 
          
          Step 1 - 
          Step 2 -
          Step 3 - 
          ----
          
        """.trimIndent()

    }
}