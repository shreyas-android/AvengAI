package com.configheroid.framework.feature.convertor.ui.urlendecoder

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cogniheroid.framework.feature.convertor.R
import com.configheroid.framework.feature.convertor.ui.component.ConvertorButton
import com.configheroid.framework.feature.convertor.utils.ConvertorUtils
import java.net.URLDecoder
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun URLEncoderDecoderScreen(navigateBack:()->Unit) {
    val context = LocalContext.current
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
            Surface(tonalElevation = 3.dp) {
                TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                    Text(
                        text = stringResource(id = R.string.title_url_encoder_decoder),
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

        }) {
        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(it), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            val input = remember {
                mutableStateOf("")
            }

            val result = remember {
                mutableStateOf("")
            }

            URLInputField(input.value, onInputTextChange = {inputData->
                input.value = inputData
            })

            Row {
                ConvertorButton(label = stringResource(R.string.button_encode), onClick = {
                    result.value = URLEncoder.encode(input.value, "UTF-8")
                })
                ConvertorButton(label = stringResource(R.string.button_decode), onClick = {
                    result.value = URLDecoder.decode(input.value, "UTF-8")
                })
            }

            if (result.value.isNotEmpty()) {

                Text(
                    text = stringResource(id = R.string.label_result),
                    fontSize = 16.sp, modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                )

                val cardColors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
                val clipboardLabel = context.getString(R.string.label_copied_to_clipboard)

                Card(
                    modifier = Modifier.padding(16.dp),
                    colors = cardColors,
                    elevation = CardDefaults.outlinedCardElevation(defaultElevation = 3.dp)
                ) {
                    ConstraintLayout(modifier = Modifier
                        .fillMaxWidth()) {
                        val (text, share, copy) = createRefs()

                        Icon(modifier = Modifier
                            .padding(top = 16.dp, end = 16.dp)
                            .constrainAs(share) {
                                top.linkTo(parent.top)
                                end.linkTo(copy.start)

                            }
                            .clickable {
                                ConvertorUtils.shareContent(context = context, data = result.value)
                            },
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary
                        )

                        Icon(modifier = Modifier
                            .padding(top = 16.dp, end = 16.dp)
                            .constrainAs(copy) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .clickable {
                                ConvertorUtils.copyAndShowToast(
                                    context = context,
                                    result = result.value
                                )
                            },
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary
                        )


                        Text(text = result.value, fontSize = 16.sp,
                            modifier = Modifier
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(share.start)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.fillToConstraints
                                }
                                .combinedClickable(onClick = {
                                    ConvertorUtils.copyAndShowToast(
                                        context = context,
                                        result = result.value
                                    )
                                }, onLongClick = {
                                    ConvertorUtils.copyAndShowToast(
                                        context = context,
                                        result = result.value
                                    )
                                })
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun URLEncoderDecoderButton(label: String, onClick: () -> Unit) {
    val buttonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    TextButton(
        shape = RoundedCornerShape(8.dp),
        colors = buttonColors, onClick = {
            onClick()
        },
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
    ) {
        androidx.compose.material.Text(
            color = MaterialTheme.colorScheme.onSecondary,
            text = label,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun URLInputField(inputText: String, onInputTextChange: (String) -> Unit) {

    OutlinedTextField(
        value = inputText,
        onValueChange = {
            onInputTextChange(it)
        },
        label = {
            Text(text = stringResource(R.string.field_input_url_encoder_decoder))
        },
        modifier = Modifier.padding(16.dp)
    )
}