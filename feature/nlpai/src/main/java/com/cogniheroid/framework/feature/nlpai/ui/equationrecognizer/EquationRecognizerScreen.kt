package com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer

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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import com.cogniheroid.framework.feature.nlpai.NLPAICore
import com.cogniheroid.framework.feature.nlpai.R
import com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer.uistate.EquationRecognizerUIEffect
import com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer.uistate.EquationRecognizerUIEvent
import com.cogniheroid.framework.feature.nlpai.ui.equationrecognizer.uistate.EquationRecognizerUIState
import com.cogniheroid.framework.feature.nlpai.utils.NLPAIUtils
import com.cogniheroid.framework.feature.nlpai.utils.getAnnotatedString
import com.cogniheroid.framework.util.ContentUtils
import com.sparrow.framework.core.avengerad.AvengerAdCore
import com.sparrow.framework.ui.component.AdUIContainer
import com.sparrow.framework.ui.component.CustomButton
import com.sparrow.framework.ui.theme.Dimensions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EquationRecognizerAIScreen(onAddImage : () -> Unit) {

    val coroutineScope = rememberCoroutineScope()
    val avengerAd = remember {
        AvengerAdCore.getAvengerAd(coroutineScope)
    }

    val viewModel = viewModel<EquationRecognizerViewModel>(
        factory = NLPAICore.equationRecognizerViewModelFactory)

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    LaunchedEffect(key1 = NLPAICore.imageIntentFlow) {
        NLPAICore.imageIntentFlow.collectLatest { intent ->
            if(intent != null) {
                val data = intent.extras?.get("data")
                if(data is Bitmap) {
                    val imageRequest =
                        imageRequestBuilder.data(data).size(size = 480).precision(Precision.EXACT)
                            .build()

                    coroutineScope.launch {
                        val bitmap = try {
                            val result = imageLoader.execute(imageRequest)
                            if(result is SuccessResult) {
                                (result.drawable as BitmapDrawable).bitmap
                            } else {
                                null
                            }
                        } catch(e : Exception) {
                            null
                        }

                        viewModel.performIntent(EquationRecognizerUIEvent.AddImage(bitmap))
                    }
                } else {
                    val singleData = intent.data
                    val clipData = intent.clipData
                    val tempList : List<Uri> = if(singleData != null) {
                        listOf(singleData)
                    } else if(clipData != null) {
                        val list = mutableListOf<Uri>()
                        for(i in 0 until clipData.itemCount) {
                            list.add(clipData.getItemAt(i).uri)
                        }
                        list
                    } else {
                        listOf()
                    }

                    tempList.forEach { uri ->
                        coroutineScope.launch {
                            val imageRequest = imageRequestBuilder.data(uri).size(size = 480)
                                .precision(Precision.EXACT).build()

                            val bitmap = try {
                                val result = imageLoader.execute(imageRequest)
                                if(result is SuccessResult) {
                                    (result.drawable as BitmapDrawable).bitmap
                                } else {
                                    null
                                }
                            } catch(e : Exception) {
                                null
                            }
                            viewModel.performIntent(EquationRecognizerUIEvent.AddImage(bitmap))
                        }
                    }
                }
            }

        }

    }


    LaunchedEffect(key1 = Unit) {
        viewModel.equationRecognizerUIEffectFlow.collectLatest {
            when(it) {
                is EquationRecognizerUIEffect.UploadImagePicker -> {
                    onAddImage()
                }
            }
        }
    }


    val equationRecognizerState = viewModel.equationRecognizerUIStateFlow.collectAsState(
        EquationRecognizerUIState.getDefault()).value

    val context = LocalContext.current

    Scaffold(containerColor = MaterialTheme.colorScheme.background, topBar = {
        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_equation_recognizer),
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            })
        }
    }) {

        AdUIContainer(Modifier.fillMaxSize().padding(it), content = {
            Column(modifier = it.padding(top = 24.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(
                    modifier = Modifier.padding(
                        top = Dimensions.padding24, start = Dimensions.defaultPadding,
                        end = Dimensions.defaultPadding),
                    text = stringResource(id = R.string.placeholder_choose_an_equation_image),
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)

                Button(modifier = Modifier.padding(top = Dimensions.defaultPadding), onClick = {
                    viewModel.performIntent(EquationRecognizerUIEvent.OnOpenImagePicker)
                }) {
                    Icon(
                        modifier = Modifier.padding(end = Dimensions.defaultPaddingHalf),
                        painter = painterResource(id = R.drawable.ic_add_photo),
                        contentDescription = "", tint = MaterialTheme.colorScheme.onSecondary)

                    androidx.compose.material.Text(
                        color = MaterialTheme.colorScheme.onSecondary,
                        text = stringResource(id = R.string.placeholder_upload_file),
                        fontSize = 16.sp)
                }

                if(equationRecognizerState.image != null) {
                    ConstraintLayout(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(
                                end = 12.dp, top = Dimensions.padding24)) {
                        val (bitmap, close) = createRefs()

                        Image(
                            modifier = Modifier
                                .padding(top = 8.dp, end = 8.dp)
                                .constrainAs(bitmap) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                }, bitmap = equationRecognizerState.image.asImageBitmap(),
                            contentDescription = "")

                        Box(modifier = Modifier
                            .size(20.dp)
                            .constrainAs(close) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer)
                            .padding(2.dp)) {
                            Icon(
                                modifier = Modifier.clickable {
                                    viewModel.performIntent(
                                        EquationRecognizerUIEvent.RemoveImage(
                                            equationRecognizerState.image))
                                }, painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        }

                    }
                }

                val defaultErrorMessage = stringResource(id = R.string.message_default_error_ai)
                if(equationRecognizerState.image != null) {
                    CustomButton(
                        label = stringResource(id = R.string.placeholder_button_equation)) {
                        viewModel.performIntent(
                            EquationRecognizerUIEvent.GenerateText(
                                equationRecognizerState.image, defaultErrorMessage))
                    }
                }

                if(equationRecognizerState.outputText.isNotEmpty() || equationRecognizerState.isGenerating) {

                    if(!equationRecognizerState.isGenerating) {
                        Text(
                            text = stringResource(id = R.string.label_generated_by_gemini),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
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

                            if(!equationRecognizerState.isGenerating) {
                                Icon(modifier = Modifier
                                    .size(28.dp)
                                    .padding(top = 8.dp, end = 8.dp)
                                    .constrainAs(share) {
                                        top.linkTo(parent.top)
                                        end.linkTo(copy.start)

                                    }
                                    .clickable {
                                        ContentUtils.shareContent(
                                            context = context,
                                            data = equationRecognizerState.outputText)
                                    }, painter = painterResource(id = R.drawable.ic_share),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary)

                                Icon(modifier = Modifier
                                    .size(28.dp)
                                    .padding(top = 8.dp, end = 8.dp)
                                    .constrainAs(copy) {
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                    }
                                    .clickable {
                                        ContentUtils.copyAndShowToast(
                                            context = context,
                                            result = equationRecognizerState.outputText)
                                    }, painter = painterResource(id = R.drawable.ic_copy),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary)
                            }

                            val result = if(equationRecognizerState.isGenerating) {
                                stringResource(id = R.string.placeholder_advance_generate_text)
                            } else {
                                getAnnotatedString(equationRecognizerState.outputText).toString()
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
                                            context = context,
                                            result = equationRecognizerState.outputText)
                                    }, onLongClick = {
                                        ContentUtils.copyAndShowToast(
                                            context = context,
                                            result = equationRecognizerState.outputText)
                                    })
                                    .padding(16.dp))
                        }
                    }
                }
            }

        }, bannerAd2 = {
            avengerAd.getAdMobBannerView(context = it, NLPAIUtils.getEquationBannerAd2())
        })
    }
}
