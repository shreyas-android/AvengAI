package com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration

import android.content.Intent
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import com.cogniheroid.framework.feature.gemini.CogniHeroidAICore
import com.cogniheroid.framework.feature.gemini.R
import com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIEffect
import com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIEvent
import com.cogniheroid.framework.feature.gemini.ui.advancetextgeneration.uistate.AdvanceTextGenerationUIState
import com.cogniheroid.framework.ui.component.CustomButton
import com.cogniheroid.framework.util.ContentUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvanceTextGeneration(
    onAddImage: () -> Unit, navigateBack: () -> Unit) {

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    val coroutineScope = rememberCoroutineScope()
    val advanceTextGenerationViewModel = viewModel<AdvanceTextGenerationViewModel>(
        factory = CogniHeroidAICore.advanceTextGenerationViewModelFactory
    )

    LaunchedEffect(key1 = CogniHeroidAICore.imageIntentFlow) {
        CogniHeroidAICore.imageIntentFlow.collectLatest { intent ->
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

                        advanceTextGenerationViewModel.performIntent(
                            AdvanceTextGenerationUIEvent
                                .AddImage(bitmap)
                        )
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
                            advanceTextGenerationViewModel.performIntent(
                                AdvanceTextGenerationUIEvent
                                    .AddImage(bitmap)
                            )
                        }
                    }
                }
            }

        }

    }

    LaunchedEffect(key1 = Unit) {
        advanceTextGenerationViewModel.advanceTextGenerationUIEffectFlow.collectLatest {
            when (it) {
                AdvanceTextGenerationUIEffect.ShowImagePicker -> {
                    onAddImage()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier, colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_advance_text_generation),
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navigateBack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "", tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            })
        }
        AdvanceTextGenerationView(modifier = Modifier,
            textGenerationUIState = advanceTextGenerationViewModel.advanceTextGenerationUIStateFlow.collectAsState().value,
            performIntent = { textGenerationUIEvent ->
                advanceTextGenerationViewModel.performIntent(textGenerationUIEvent)
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AdvanceTextGenerationView(
    modifier: Modifier,
    textGenerationUIState: AdvanceTextGenerationUIState,
    performIntent: (AdvanceTextGenerationUIEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {

            val (image, textInput) = createRefs()
            Box(modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(textInput.top)
                    bottom.linkTo(textInput.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(textInput.start)
                }
                .padding(start = 16.dp, end = 8.dp)
                .background(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .padding(8.dp)) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            focusManager.clearFocus()
                            performIntent(AdvanceTextGenerationUIEvent.OnOpenImagePicker)
                        },
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = "", tint = MaterialTheme.colorScheme.primary
                )
            }

            TextInputField(modifier = Modifier.constrainAs(textInput) {
                top.linkTo(parent.top)
                start.linkTo(image.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
                textGenerationUIState.inputText,
                onInputTextChange = { inputData ->
                    performIntent(AdvanceTextGenerationUIEvent.InputText(inputData))
                },
                onClear = {
                    performIntent(AdvanceTextGenerationUIEvent.ClearText)
                })

        }

        if (textGenerationUIState.bitmaps.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                items(textGenerationUIState.bitmaps) { image ->
                    ConstraintLayout(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(end = 12.dp)
                    ) {
                        val (bitmap, close) = createRefs()
                        Image(
                            modifier = Modifier
                                .padding(top = 8.dp, end = 8.dp)
                                .constrainAs(bitmap) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                }, bitmap = image.asImageBitmap(), contentDescription = ""
                        )

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .constrainAs(close) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                }
                                .background(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                                .padding(2.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        performIntent(
                                            AdvanceTextGenerationUIEvent.RemoveImage(
                                                image
                                            )
                                        )
                                    },
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }


                    }

                }
            }
        }

        CustomButton(
            modifier = Modifier.padding(top = 16.dp),
            label = stringResource(R.string.label_generate_text),
            onClick = {
                focusManager.clearFocus()
                performIntent(AdvanceTextGenerationUIEvent.GenerateText(textGenerationUIState.inputText))
            })


        if (textGenerationUIState.outputText.isNotEmpty() || textGenerationUIState.isGenerating) {

            if (!textGenerationUIState.isGenerating) {
                Text(
                    text = stringResource(id = R.string.label_generated_by_gemini),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            val cardColors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )

            Card(
                modifier = Modifier.padding(16.dp),
                colors = cardColors,
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 3.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val (text, share, copy) = createRefs()

                    if (!textGenerationUIState.isGenerating) {
                        Icon(modifier = Modifier
                            .size(28.dp)
                            .padding(top = 8.dp, end = 8.dp)
                            .constrainAs(share) {
                                top.linkTo(parent.top)
                                end.linkTo(copy.start)

                            }
                            .clickable {
                                focusManager.clearFocus()
                                ContentUtils.shareContent(
                                    context = context,
                                    data = textGenerationUIState.outputText
                                )
                            },
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary
                        )

                        Icon(modifier = Modifier
                            .size(28.dp)
                            .padding(top = 8.dp, end = 8.dp)
                            .constrainAs(copy) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .clickable {
                                focusManager.clearFocus()
                                ContentUtils.copyAndShowToast(
                                    context = context,
                                    result = textGenerationUIState.outputText
                                )
                            },
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    val result = if (textGenerationUIState.isGenerating) {
                        stringResource(id = R.string.placeholder_advance_generate_text)
                    } else {
                        textGenerationUIState.outputText
                    }

                    Text(text = HtmlCompat.fromHtml(result, 0).toString(), fontSize = 16.sp,
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
                                    result = textGenerationUIState.outputText
                                )
                            }, onLongClick = {
                                ContentUtils.copyAndShowToast(
                                    context = context,
                                    result = textGenerationUIState.outputText
                                )
                            })
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextInputField(
    modifier: Modifier = Modifier,
    inputText: String,
    onInputTextChange: (String) -> Unit,
    onClear: () -> Unit
) {

    OutlinedTextField(
        value = inputText,
        onValueChange = {
            onInputTextChange(it)
        },
        label = {
            Text(text = stringResource(R.string.hint_generate_text))
        },
        modifier = modifier
            .padding(start = 8.dp, end = 16.dp)
            .fillMaxWidth(), trailingIcon = {
            if (inputText.isNotEmpty()) {
                IconButton(onClick = {
                    onClear()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "", tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}

/*@Composable
internal fun PhotoReasoningRoute(
    viewModel: PhotoReasoningViewModel = viewModel(factory = GenerativeViewModelFactory)
) {
    val photoReasoningUiState by viewModel.uiState.collectAsState()

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    PhotoReasoningScreen(
        uiState = photoReasoningUiState,
        onReasonClicked = { inputText, selectedItems ->
            coroutineScope.launch {
                val bitmaps = selectedItems.mapNotNull {
                    val imageRequest = imageRequestBuilder
                        .data(it)
                        .size(size = 768)
                        .precision(Precision.EXACT)
                        .build()
                    try {
                        val result = imageLoader.execute(imageRequest)
                        if (result is SuccessResult) {
                            return@mapNotNull (result.drawable as BitmapDrawable).bitmap
                        } else {
                            return@mapNotNull null
                        }
                    } catch (e: Exception) {
                        return@mapNotNull null
                    }
                }
                viewModel.reason(inputText, bitmaps)
            }
        }
    )
}

@Composable
fun PhotoReasoningScreen(
    uiState: PhotoReasoningUiState = PhotoReasoningUiState.Loading,
    onReasonClicked: (String, List<Uri>) -> Unit = { _, _ -> }
) {
    var userQuestion by remember Save able { mutableStateOf("") }
    val imageUris = rememberSave able(saver = UriSaver()) { mutableStateListOf() }

    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { imageUri ->
        imageUri?.let {
            imageUris.add(it)
        }
    }

    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                IconButton(
                    onClick = {
                        pickMedia.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_image),
                    )
                }
                OutlinedTextField(
                    value = userQuestion,
                    label = { Text(stringResource(R.string.reason_label)) },
                    placeholder = { Text(stringResource(R.string.reason_hint)) },
                    onValueChange = { userQuestion = it },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )
                TextButton(
                    onClick = {
                        if (userQuestion.isNotBlank()) {
                            onReasonClicked(userQuestion, imageUris.toList())
                        }
                    },
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(stringResource(R.string.action_go))
                }
            }
            LazyRow(
                modifier = Modifier.padding(all = 8.dp)
            ) {
                items(imageUris) { imageUri ->
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                            .requiredSize(72.dp)
                    )
                }
            }
        }
        when (uiState) {
            PhotoReasoningUiState.Initial -> {
                // Nothing is shown
            }

            PhotoReasoningUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator()
                }
            }

            is PhotoReasoningUiState.Success -> {
                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Person Icon",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .requiredSize(36.dp)
                                .drawBehind {
                                    drawCircle(color = Color.White)
                                }
                        )
                        Text(
                            text = uiState.outputText, // TODO(that fire dev): Figure out Markdown support
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }

            is PhotoReasoningUiState.Error -> {
                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
            }
        }
    }
}*/


