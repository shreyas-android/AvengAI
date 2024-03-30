package com.cogniheroid.framework.feature.inspireai.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cogniheroid.framework.feature.inspireai.InspireAICore
import com.cogniheroid.framework.feature.inspireai.R
import com.cogniheroid.framework.feature.inspireai.ui.uistate.QuoteUIEvent
import com.cogniheroid.framework.feature.inspireai.ui.viewmodel.InspireAIViewModel
import com.cogniheroid.framework.feature.inspireai.utils.getAnnotatedString
import com.cogniheroid.framework.util.ContentUtils
import com.sparrow.framework.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InspireAIScreen() {

    val inspireAIViewModel = viewModel<InspireAIViewModel>(factory = InspireAICore.inspireAIViewModelFactory)

    val defaultMessage = stringResource(id = R.string.message_default_error_ai)

    LaunchedEffect(Unit){
        inspireAIViewModel.performIntent(QuoteUIEvent.GenerateQuotes(defaultMessage))
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)) {

        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_quotes), fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface)
            })
        }

        val cardColors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface)

        val quotesUIState = inspireAIViewModel.currentQuote.collectAsState().value

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center) {
            Card(
                modifier = Modifier.padding(16.dp), colors = cardColors,
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 3.dp)) {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (text, share, copy) = createRefs()

                    if(!quotesUIState.isGenerating) {
                        Icon(modifier = Modifier.size(28.dp).padding(top = 8.dp, end = 8.dp)
                            .constrainAs(share) {
                                top.linkTo(parent.top)
                                end.linkTo(copy.start)

                            }.clickable {
                                ContentUtils.shareContent(
                                    context = context, data = quotesUIState.outputText)
                            }, painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary)

                        Icon(modifier = Modifier.size(28.dp).padding(top = 8.dp, end = 8.dp)
                            .constrainAs(copy) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }.clickable {
                                ContentUtils.copyAndShowToast(
                                    context = context, result = quotesUIState.outputText)
                            }, painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                    }

                    val result = if(quotesUIState.isGenerating) {
                        stringResource(id = R.string.placeholder_generating_quotes)
                    } else {
                        getAnnotatedString(quotesUIState.outputText)
                    }

                    Text(text = result.toString(), fontSize = 16.sp,
                        modifier = Modifier.constrainAs(text) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(share.start)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints
                            }.combinedClickable(onClick = {
                                ContentUtils.copyAndShowToast(
                                    context = context, result = quotesUIState.outputText)
                            }, onLongClick = {
                                ContentUtils.copyAndShowToast(
                                    context = context, result = quotesUIState.outputText)
                            }).padding(16.dp), letterSpacing = 1.sp, lineHeight = 28.sp)

                }
            }

            Button(
                onClick = {
                    inspireAIViewModel.performIntent(
                        QuoteUIEvent.GenerateQuotes(defaultMessage))
                }, // Calls the fetchQuote function from ViewModel
                modifier = Modifier.padding(top = 16.dp).align(
                    Alignment.CenterHorizontally) // Centers the button horizontally
            ) {
                Text(stringResource(R.string.button_generate_quotes))
            }
        }

    }
}
