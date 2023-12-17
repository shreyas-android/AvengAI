package com.configheroid.framework.feature.convertor.ui.datetimeconvertor

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cogniheroid.framework.feature.convertor.R
import com.cogniheroid.framework.ui.component.ConvertorButton
import com.cogniheroid.framework.util.ContentUtils
import com.configheroid.framework.feature.convertor.ui.datetimeconvertor.data.model.TimeZoneInfo
import com.configheroid.framework.feature.convertor.utils.ConvertorUtils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DateTimeConvertorScreen(navigateBack:()->Unit) {

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
                        text = stringResource(id = R.string.title_date_time_converter),
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                    )
                },navigationIcon = {
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

        }) {paddingValues ->
        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val showTimeZone = remember {
                mutableStateOf(false)
            }
            val input = remember {
                mutableStateOf("")
            }

            val milliSecond = remember {
                mutableStateOf(0L)
            }

            val result = remember {
                mutableStateOf("")
            }

            val pattern = remember { Regex("^\\d+\$") }
            DateTimeConvInputField(input.value, onInputTextChange = { newValue->
                if (newValue.isEmpty() || (newValue.matches(pattern) && (newValue.toLongOrNull() ?: 0L) < Long.MAX_VALUE)){
                    input.value = newValue
                    milliSecond.value = newValue.toLongOrNull() ?: 0L
                }
            })

            Row {
                Column {
                    com.cogniheroid.framework.ui.component.ConvertorButton(
                        label = stringResource(R.string.label_convert_to_date),
                        onClick = {
                            result.value = ConvertorUtils.getFormattedDate(milliSecond.value)
                        })

                    com.cogniheroid.framework.ui.component.ConvertorButton(
                        label = stringResource(R.string.label_convert_to_time),
                        onClick = {
                            result.value = ConvertorUtils.getFormattedTime(milliSecond.value)
                        })

                    com.cogniheroid.framework.ui.component.ConvertorButton(
                        label = stringResource(R.string.label_convert_to_date_time),
                        onClick = {
                            result.value = ConvertorUtils.getFormattedDateAndTime(milliSecond.value)
                        })
                }

                com.cogniheroid.framework.ui.component.ConvertorButton(
                    label = stringResource(R.string.label_now),
                    onClick = {
                        val calendar = Calendar.getInstance()
                        milliSecond.value = calendar.timeInMillis
                        input.value = calendar.timeInMillis.toString()
                        result.value = ConvertorUtils.getFormattedDateAndTime(milliSecond.value)
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
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        val (text, share, copy) = createRefs()

                        Icon(modifier = Modifier
                            .padding(top = 16.dp, end = 16.dp)
                            .constrainAs(share) {
                                top.linkTo(parent.top)
                                end.linkTo(copy.start)

                            }
                            .clickable {
                                ContentUtils.shareContent(context = context, data = result.value)
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
                                ContentUtils.copyAndShowToast(
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
                                    ContentUtils.copyAndShowToast(
                                        context = context,
                                        result = result.value
                                    )
                                }, onLongClick = {
                                    ContentUtils.copyAndShowToast(
                                        context = context,
                                        result = result.value
                                    )
                                })
                                .padding(16.dp)
                        )
                    }
                }
            }

            com.cogniheroid.framework.ui.component.ConvertorButton(modifier = Modifier.padding(top = 24.dp),
                label = stringResource(R.string.title_show_current_timezone), onClick = {
                    showTimeZone.value = true
                })

            if (showTimeZone.value) {
                TimeZoneDataItem(timeZoneInfo = ConvertorUtils.getCurrentTimeZoneInfo(),
                    onItemClick = {})
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateTimeConvInputField(inputText: String, onInputTextChange: (String) -> Unit) {

    OutlinedTextField(
        value = inputText,
        onValueChange = {
            onInputTextChange(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = {
            Text(text = stringResource(R.string.field_input_millisecond))
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun TimeZoneDataItem(timeZoneInfo: TimeZoneInfo, onItemClick: (TimeZoneInfo) -> Unit) {
    val cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    )
    Card(
        modifier = Modifier.padding(16.dp),
        colors = cardColors,
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(timeZoneInfo)
            }
            .padding(16.dp)) {
            timeZoneInfo.tzCountry?.let {
                Text(text = it.uppercase(), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            timeZoneInfo.tzDisplayName?.let {
                Text(fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp), text = it,

                )
            }
            val timeZoneCode = "${timeZoneInfo.tzCode}  ${timeZoneInfo.tzId}".trim()
            if (timeZoneCode.isNotEmpty()) {
                Text(fontSize = 14.sp,
                    text = timeZoneCode,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}