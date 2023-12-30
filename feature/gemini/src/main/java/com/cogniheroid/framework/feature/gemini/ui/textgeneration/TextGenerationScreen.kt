package com.cogniheroid.framework.feature.gemini.ui.textgeneration

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.cogniheroid.framework.feature.gemini.CogniHeroidAICore
import com.cogniheroid.framework.feature.gemini.R
import com.cogniheroid.framework.feature.gemini.ui.textgeneration.uistate.TextGenerationUIEvent
import com.cogniheroid.framework.feature.gemini.ui.textgeneration.uistate.TextGenerationUIState
import com.cogniheroid.framework.ui.component.CustomButton
import com.cogniheroid.framework.util.ContentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextGenerationScreen(navigateBack: () -> Unit) {

    val textGenerationViewModel = viewModel<TextGenerationViewModel>(
        factory = CogniHeroidAICore.textGenerationViewModelFactory)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_text_generation),
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

        TextGenerationView(modifier = Modifier,
            textGenerationUIState = textGenerationViewModel.textGenerationUIStateFlow.collectAsState().value,
            performIntent = { textGenerationUIEvent ->
                textGenerationViewModel.performIntent(textGenerationUIEvent)
            })

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextGenerationView(
    modifier: Modifier,
    textGenerationUIState: TextGenerationUIState,
    performIntent: (TextGenerationUIEvent) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextInputField(modifier = Modifier.padding(top = 32.dp), textGenerationUIState.inputText,
            onInputTextChange = { inputData ->
                performIntent(TextGenerationUIEvent.InputText(inputData))
            }, onClear = {
                performIntent(TextGenerationUIEvent.ClearText)
            })


        CustomButton(label = stringResource(R.string.label_generate_text), onClick = {
            focusManager.clearFocus()
            performIntent(TextGenerationUIEvent.GenerateText(textGenerationUIState.inputText))
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
                        stringResource(id = R.string.placeholder_generate_text)
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
            .padding(16.dp)
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