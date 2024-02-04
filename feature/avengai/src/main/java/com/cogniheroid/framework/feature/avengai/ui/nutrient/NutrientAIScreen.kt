package com.cogniheroid.framework.feature.avengai.ui.nutrient

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import com.cogniheroid.framework.feature.avengai.AvengerAICore
import com.cogniheroid.framework.feature.avengai.R
import com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate.NutrientUIEffect
import com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate.NutrientUIEvent
import com.cogniheroid.framework.feature.avengai.ui.nutrient.uistate.NutrientUIState
import com.cogniheroid.framework.ui.component.CustomButton
import com.cogniheroid.framework.ui.theme.Dimensions
import com.cogniheroid.framework.util.ContentUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NutrientAIScreen(onAddImage: () -> Unit) {

    val viewModel = viewModel<NutrientAIViewModel>(factory = AvengerAICore.nutrientAIViewModelFactory)

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = AvengerAICore.imageIntentFlow) {
        AvengerAICore.imageIntentFlow.collectLatest { intent ->
            if (intent != null) {
                val data = intent.extras?.get("data")
                if (data is Bitmap) {
                    val imageRequest = imageRequestBuilder
                        .data(data)
                        .size(size = 480)
                        .precision(Precision.EXACT)
                        .build()

                    coroutineScope.launch {
                        val bitmap = try {
                            val result = imageLoader.execute(imageRequest)
                            if (result is SuccessResult) {
                                (result.drawable as BitmapDrawable).bitmap
                            } else {
                                null
                            }
                        } catch (e: Exception) {
                            null
                        }

                        viewModel.performIntent(NutrientUIEvent.AddImage(bitmap))
                    }
                } else {
                    val singleData = intent.data
                    val clipData = intent.clipData
                    val tempList: List<Uri> = if (singleData != null) {
                        listOf(singleData)
                    } else if (clipData != null) {
                        val list = mutableListOf<Uri>()
                        for (i in 0 until clipData.itemCount) {
                            list.add(clipData.getItemAt(i).uri)
                        }
                        list
                    } else {
                        listOf()
                    }

                    tempList.forEach { uri ->
                        coroutineScope.launch {
                            val imageRequest = imageRequestBuilder
                                .data(uri)
                                .size(size = 480)
                                .precision(Precision.EXACT)
                                .build()

                            val bitmap = try {
                                val result = imageLoader.execute(imageRequest)
                                if (result is SuccessResult) {
                                    (result.drawable as BitmapDrawable).bitmap
                                } else {
                                    null
                                }
                            } catch (e: Exception) {
                                null
                            }
                            viewModel.performIntent(NutrientUIEvent.AddImage(bitmap))
                        }
                    }
                }
            }

        }

    }


    LaunchedEffect(key1 = Unit) {
        viewModel.nutrientUIEffectFlow.collectLatest {
            when (it) {
                is NutrientUIEffect.UploadImagePicker -> {
                    onAddImage()
                }
            }
        }
    }

    val nutrientUIState = viewModel.nutrientUIStateFlow.collectAsState(NutrientUIState.getDefault()).value
    Scaffold(containerColor = MaterialTheme.colorScheme.background, topBar = {
        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_nutrient_ai), fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface)
            })
        }
    }) {

        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Text(
                modifier = Modifier.padding(top = Dimensions.padding24,
                    start = Dimensions.defaultPadding, end = Dimensions.defaultPadding),
                text = stringResource(id = R.string.placeholder_choose_an_image), fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface)

            Button(modifier = Modifier.padding(top = Dimensions.defaultPadding), onClick = {
                viewModel.performIntent(NutrientUIEvent.OnOpenImagePicker)
            }) {
                Icon(
                    modifier = Modifier.padding(end = Dimensions.defaultPaddingHalf),
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondary)

                androidx.compose.material.Text(
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = stringResource(id = R.string.placeholder_upload_file), fontSize = 16.sp)
            }

            if(nutrientUIState.image != null) {
                ConstraintLayout(
                    modifier = Modifier.wrapContentSize().padding(end = 12.dp,
                        top = Dimensions.padding24)) {
                    val (bitmap, close) = createRefs()
                    val context = LocalContext.current

                    Image(
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp).constrainAs(bitmap) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }, bitmap = nutrientUIState.image.asImageBitmap(),
                        contentDescription = "")

                    Box(modifier = Modifier.size(20.dp).constrainAs(close) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }.background(
                            shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(2.dp)) {
                        Icon(
                            modifier = Modifier.clickable {
                                viewModel.performIntent(NutrientUIEvent.RemoveImage(nutrientUIState
                                    .image))
                            }, painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                    }

                }
            }


            val defaultErrorMessage = stringResource(id = R.string.message_default_error_ai)
            if(nutrientUIState.image != null) {
                CustomButton(label = stringResource(id = R.string.placeholder_button_nutrient)) {
                    viewModel.performIntent(NutrientUIEvent.GenerateText(nutrientUIState.image,
                        defaultErrorMessage))
                }
            }

            val context = LocalContext.current
            if(nutrientUIState.outputText.isNotEmpty() || nutrientUIState.isGenerating) {

                if(!nutrientUIState.isGenerating) {
                    Text(
                        text = stringResource(id = R.string.label_generated_by_gemini),
                        fontSize = 16.sp, modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface)
                }

                val cardColors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface)

                Card(
                    modifier = Modifier.padding(16.dp), colors = cardColors,
                    elevation = CardDefaults.outlinedCardElevation(defaultElevation = 3.dp)) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxWidth()) {
                        val (text, share, copy) = createRefs()

                        if(!nutrientUIState.isGenerating) {
                            Icon(modifier = Modifier
                                .size(28.dp)
                                .padding(top = 8.dp, end = 8.dp)
                                .constrainAs(share) {
                                    top.linkTo(parent.top)
                                    end.linkTo(copy.start)

                                }
                                .clickable {
                                    ContentUtils.shareContent(
                                        context = context, data = nutrientUIState.outputText)
                                }, painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = "", tint = MaterialTheme.colorScheme.primary)

                            Icon(modifier = Modifier
                                .size(28.dp)
                                .padding(top = 8.dp, end = 8.dp)
                                .constrainAs(copy) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                }
                                .clickable {
                                    ContentUtils.copyAndShowToast(
                                        context = context, result = nutrientUIState.outputText)
                                }, painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        }

                        val result = if(nutrientUIState.isGenerating) {
                            stringResource(id = R.string.placeholder_advance_generate_text)
                        } else {
                            getAnnotatedString(nutrientUIState.outputText).toString()
                        }

                        Text(text = result, fontSize = 16.sp,
                            modifier = Modifier
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(share.start)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.fillToConstraints
                                }
                                .combinedClickable(onClick = {
                                    ContentUtils.copyAndShowToast(
                                        context = context, result = nutrientUIState.outputText)
                                }, onLongClick = {
                                    ContentUtils.copyAndShowToast(
                                        context = context, result = nutrientUIState.outputText)
                                })
                                .padding(16.dp))
                    }
                }
            }
        }
    }
}

fun getAnnotatedString(text: String): AnnotatedString {
    val boldRegex = Regex("(?<!\\*)\\*\\*(?!\\*).*?(?<!\\*)\\*\\*(?!\\*)")

    val boldKeywords: Sequence<MatchResult> = boldRegex.findAll(text)

    val boldIndexes = mutableListOf<Pair<Int, Int>>()
    boldKeywords.map {
        boldIndexes.add(Pair(it.range.first, it.range.last - 2))
    }

    val newText = text.replace("**", "")

    return buildAnnotatedString {
        append(newText)

        // Add bold style to keywords that has to be bold
        boldIndexes.forEach {
            addStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimensions.primaryFontSize

                ),
                start = it.first,
                end = it.second
            )

        }
    }
}
