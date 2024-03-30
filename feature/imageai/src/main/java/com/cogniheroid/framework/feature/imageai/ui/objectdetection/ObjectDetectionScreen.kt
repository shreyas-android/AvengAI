package com.cogniheroid.framework.feature.imageai.ui.objectdetection

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import com.cogniheroid.framework.core.ai.data.UIResult
import com.cogniheroid.framework.feature.imageai.ImageAICore
import com.cogniheroid.framework.feature.imageai.R
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate.ObjectDetectionUIEffect
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate.ObjectDetectionUIEvent
import com.cogniheroid.framework.feature.imageai.ui.objectdetection.uistate.ObjectDetectionUIState
import com.sparrow.framework.ui.component.CustomButton
import com.sparrow.framework.ui.theme.Dimensions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ObjectDetectionAIScreen(onAddImage : () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val viewModel = viewModel<ObjectDetectionViewModel>(
        factory = ImageAICore.objectDetectionViewModelFactory)

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    LaunchedEffect(key1 = ImageAICore.imageIntentFlow) {
        ImageAICore.imageIntentFlow.collectLatest { intent ->
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

                        viewModel.performIntent(ObjectDetectionUIEvent.AddImage(bitmap))
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
                            viewModel.performIntent(ObjectDetectionUIEvent.AddImage(bitmap))
                        }
                    }
                }
            }

        }

    }


    LaunchedEffect(key1 = Unit) {
        viewModel.objectDetectionUIEffectFlow.collectLatest {
            when(it) {
                is ObjectDetectionUIEffect.UploadImagePicker -> {
                    onAddImage()
                }
            }
        }
    }

    val context = LocalContext.current

    val equationRecognizerState = viewModel.objectDetectionUIStateFlow.collectAsState(
        ObjectDetectionUIState.getDefault()).value
    Scaffold(containerColor = MaterialTheme.colorScheme.background, topBar = {
        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_object_detection),
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            })
        }
    }) {
        ConstraintLayout(modifier = Modifier.navigationBarsPadding().fillMaxSize().padding(it)) {
            val (banner1, container, banner2) = createRefs()

            Column(modifier = Modifier
                .constrainAs(container) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }.padding(top = 24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(
                    modifier = Modifier.padding(
                        top = Dimensions.padding24, start = Dimensions.defaultPadding,
                        end = Dimensions.defaultPadding),
                    text = stringResource(id = R.string.placeholder_choose_an_object_detect),
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)

                Button(modifier = Modifier.padding(top = Dimensions.defaultPadding), onClick = {
                    viewModel.performIntent(ObjectDetectionUIEvent.OnOpenImagePicker)
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
                                        ObjectDetectionUIEvent.RemoveImage(
                                            equationRecognizerState.image))
                                }, painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        }

                    }
                }

                val defaultErrorMessage = stringResource(id = R.string.message_default_error_ai)
                if(equationRecognizerState.image != null) {
                    CustomButton(
                        label = stringResource(id = R.string.placeholder_button_detect_object)) {
                        viewModel.performIntent(
                            ObjectDetectionUIEvent.DetectObject(
                                equationRecognizerState.image, defaultErrorMessage))
                    }
                }

                if(equationRecognizerState.image != null) {
                    when(val result = equationRecognizerState.outputUIResult) {
                        UIResult.Loading -> {

                        }

                        is UIResult.Error -> {

                        }

                        is UIResult.Success -> {

                            with(LocalDensity.current) {
                                Column {
                                    Box(modifier = Modifier.width(500.dp).height(700.dp)) {

                                        Image(
                                            modifier = Modifier.fillMaxSize()
                                                .padding(top = 8.dp, end = 8.dp),
                                            bitmap = equationRecognizerState.image.asImageBitmap(),
                                            contentDescription = "")
                                        result.data.forEach { objectDetectionInfo ->
                                            BoundingBox(objectDetectionInfo.rect)
                                        }
                                    }


                                    result.data.forEach { objectDetectionInfo ->
                                        Text(
                                            text = objectDetectionInfo.labelDescription,
                                            fontSize = 16.sp)
                                    }

                                }
                            }

                        }

                        null -> {

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Density.BoundingBox(rect : Rect){
    Box(modifier = Modifier.absoluteOffset(rect.left.toDp(), rect.top.toDp()).width(rect.width().toDp()).height(rect.height().toDp())
        .border(2.dp, color = Color.Red)){}
}
