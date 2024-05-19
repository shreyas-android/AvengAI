package com.jackson.framework.feature.convertor.ui.urlendecoder

import android.app.Activity
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.jackson.framework.feature.convertor.R
import com.sparrow.framework.ui.component.CustomButton
import com.jackson.framework.feature.convertor.ui.ConvertorRoute
import com.jackson.framework.feature.convertor.utils.ContentUtils
import com.jackson.framework.feature.convertor.utils.ConvertorUtils
import com.jackson.framework.feature.convertor.widget.AdBannerView
import com.sparrow.framework.core.avengerad.AvengerAd
import com.sparrow.framework.feature.avengeradscreen.AvengerAdSecretKey
import java.net.URLDecoder
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun URLEncoderDecoderScreen(navController : NavController, activity: Activity, avengerAd: AvengerAd, navigateBack:()->Unit) {
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

            AdBannerView(modifier = Modifier.padding(top = 16.dp, bottom = 100.dp),
                activity = activity, avengerAd = avengerAd, bannerId = ConvertorUtils
                    .BANNER_AD_UNIT_ID_3, mRECId = ConvertorUtils.MREC_AD_UNIT_ID_3)

            val input = remember {
                mutableStateOf("")
            }

            val result = remember {
                mutableStateOf("")
            }

            URLInputField(input.value, onInputTextChange = {inputData->
                input.value = inputData
                if(AvengerAdSecretKey.APPLOVIN_TEXT_SECRET_KEY == inputData){
                    navController.navigate(ConvertorRoute.APPLOVIN_SCREEN.route)
                }
            })

            Row {
                CustomButton(
                    label = stringResource(R.string.button_encode),
                    onClick = {
                        result.value = URLEncoder.encode(input.value, "UTF-8")
                    })
                CustomButton(
                    label = stringResource(R.string.button_decode),
                    onClick = {
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

            AdBannerView(modifier = Modifier.padding(top = 100.dp),
                activity = activity, avengerAd = avengerAd, bannerId = ConvertorUtils
                    .BANNER_AD_UNIT_ID_4, mRECId = ConvertorUtils.MREC_AD_UNIT_ID_4)
        }
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