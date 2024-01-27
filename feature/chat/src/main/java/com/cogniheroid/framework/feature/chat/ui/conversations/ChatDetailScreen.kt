package com.cogniheroid.framework.feature.chat.ui.conversations

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import coil.transform.CircleCropTransformation
import com.cogniheroid.framework.feature.chat.Instance
import com.cogniheroid.framework.feature.chat.R
import com.cogniheroid.framework.feature.chat.callback.ChatExternalCallback
import com.cogniheroid.framework.feature.chat.data.model.ChatDetailItem
import com.cogniheroid.framework.feature.chat.data.model.ConversationMessage
import com.cogniheroid.framework.feature.chat.ui.conversations.uistate.MessageEvent
import com.cogniheroid.framework.feature.chat.ui.conversations.uistate.MessageSideEffect
import com.cogniheroid.framework.feature.chat.utils.ConversationUtils
import com.cogniheroid.framework.feature.chat.utils.DisplayUtils
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.utils.DateTimeUtils
import com.cogniheroid.framework.ui.theme.Dimensions
import com.cogniheroid.framework.util.ImageUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatDetailScreen(
    chatListItem: ConversationItem,
    chatExternalCallback: ChatExternalCallback,
    navigateBack: () -> Unit
) {

    val messageViewModel = viewModel<MessageViewModel>(factory = Instance.messageViewModelFactory)

    LaunchedEffect(key1 = chatListItem) {
        messageViewModel.updateChatDetailMap(chatListItem)
    }

    LaunchedEffect(key1 = Unit) {
        messageViewModel.messageSideEffect.collectLatest {
            when (it) {
                is MessageSideEffect.OnAddAttachment -> {
                    chatExternalCallback.onAddAttachment(it.externalTextCallback)
                }
            }
        }
    }

    val message = remember() {
        mutableStateOf("")
    }

    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val (topAppBar, chatList, messageField) = createRefs()
        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(tonalElevation = 3.dp, modifier = Modifier
            .constrainAs(topAppBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .statusBarsPadding()) {
            TopAppBar(colors = colors, title = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    val drawable = ImageUtils.getComposeContactPlaceHolder(chatListItem.title)
                    val imageLoader = ImageRequest.Builder(context).data(chatListItem.imageUri)
                        .error(drawable).placeholder(drawable).transformations(
                            CircleCropTransformation()
                        ).build()
                    val painter = rememberAsyncImagePainter(model = imageLoader)

                    val modifier = Modifier.padding(end = 16.dp)
                    val avatarImageSize = with(LocalDensity.current) { 32.dp.toPx() }.toInt()
                    if (chatListItem.imageUri == null) {
                        Image(
                            modifier = modifier,
                            bitmap = drawable.toBitmap(avatarImageSize, avatarImageSize)
                                .asImageBitmap(),
                            contentDescription = ""
                        )
                    } else {
                        Image(modifier = modifier, painter = painter, contentDescription = "")
                    }

                    Text(
                        text = chatListItem.title,
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                }
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

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(chatList) {
                top.linkTo(topAppBar.bottom)
                bottom.linkTo(messageField.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }) {

            ChatDetailContainer(
                messageViewModel.conversationMessages.collectAsState().value
            )
        }

        SendMessageField(modifier = Modifier
            .constrainAs(messageField) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }, onValueChange = {
            message.value = it
        }, onSendClick = {
            messageViewModel.performEvent(
                MessageEvent.OnSendMessageEvent(
                    context,
                    System.currentTimeMillis(), message.value
                )
            )
            message.value = ""
        }, value = message.value, onAttachmentClick = {
            messageViewModel.performEvent(
                MessageEvent.OnAddAttachmentClicked
            )
        }
        )

    }

}


@Composable
fun SendMessageField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onAttachmentClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .padding(
                start = Dimensions.defaultPadding, end = Dimensions.defaultPadding,
                bottom = Dimensions.defaultPaddingHalf
            )
            .navigationBarsPadding()
            .imePadding()
            .fillMaxWidth()
    ) {
        val (textField, sendButton) = createRefs()
        BasicTextField(modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(Dimensions.size20)
            )
            .constrainAs(textField) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(sendButton.start)
                width = Dimension.fillToConstraints
            }
            .padding(
                horizontal = Dimensions.defaultPadding, vertical = Dimensions
                    .defaultPaddingHalf
            ), value = value, onValueChange = {
            onValueChange(it)
        }, decorationBox = { innerTextField ->
            ConstraintLayout {
                val (field, icon) = createRefs()
                Box(modifier = Modifier.constrainAs(field) {
                    start.linkTo(parent.start)
                    end.linkTo(icon.start)
                    width = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) {
                    innerTextField()
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.placeholder_start_typing),
                            fontSize = Dimensions.secondaryFontSize,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(modifier = Modifier
                    .constrainAs(icon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        onAttachmentClick()
                    }
                    .padding(start = Dimensions.padding4),
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )

            }
        }, textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
        )

        val sendIcon = R.drawable.ic_send /*if (value.isEmpty()) {
            R.drawable.ic_mic
        } else {
            R.drawable.ic_send
        }*/


        IconButton(onClick = {
            onSendClick()
        }, modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(sendButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .background(shape = CircleShape, color = MaterialTheme.colorScheme.primary)) {
            Icon(
                painter = painterResource(id = sendIcon), contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }


}

@Composable
private fun ChatDetailContainer(
    conversationMessage: ConversationMessage
) {
    when (conversationMessage) {
        is ConversationMessage.Loading -> {
            LoadingView()
        }

        is ConversationMessage.Messages -> {
            ConversationList(
                chatItemsMap = conversationMessage.messageMap
            )
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversationList(
    modifier: Modifier = Modifier,
    chatItemsMap: Map<String, List<ChatDetailItem>>
) {
    val scrollState = rememberLazyListState()
    val flattenList = chatItemsMap.values.flatten()
    LaunchedEffect(key1 = flattenList.size) {
        val scrollIndex = if (flattenList.isNotEmpty()) {
            flattenList.size - 1
        } else {
            0
        }
        scrollState.scrollToItem(scrollIndex)
    }

    LazyColumn(modifier = modifier, state = scrollState) {
        chatItemsMap.forEach { (key, value) ->
            stickyHeader {
                DateHeaderItem(dateHeader = key)
            }
            items(value) {
                ChatItemContainer(chatDetailItem = it)
            }
        }

    }
}

@Composable
private fun LoadingView() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (progressBar, description) = createRefs()
        CircularProgressIndicator(modifier = Modifier
            .constrainAs(progressBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(description.top)
            })

        Text(text = stringResource(id = R.string.placeholder_loading_message), fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(16.dp),
            fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center)
    }
}


@Composable
private fun ChatItemContainer(chatDetailItem: ChatDetailItem) {
    when (chatDetailItem) {
        is ChatDetailItem.NormalMessage -> NormalMessageItem(normalMessage = chatDetailItem)
        is ChatDetailItem.ReplyMessage -> ReplyMessageItem(replyMessage = chatDetailItem)
    }

}

@Composable
private fun DateHeaderItem(dateHeader: String) {
    Box(
        modifier = Modifier
            .padding(
                top = Dimensions.defaultPaddingHalf
            )
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        val time = DateTimeUtils.getDateAsMilliSecond(dateHeader)
        val formattedString = DateTimeUtils.formatDate(time, ConversationUtils.FULL_DATE_FORMAT)
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(Dimensions.size4)
                )
                .padding(
                    horizontal = Dimensions.defaultPadding,
                    vertical = Dimensions.defaultPaddingHalf
                ),
            text = formattedString, fontSize = Dimensions.tertiaryFontSize,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun NormalMessageItem(normalMessage: ChatDetailItem.NormalMessage) {
    MessageItem(messageItem = normalMessage.messageItem)
}

@Composable
private fun MessageItem(messageItem: MessageWithSender) {
    Log.d("CHECKMESSAGES", "CHEKCIG THE MESSAGE = ${messageItem.fileUri}")
    if (messageItem.message.isNullOrEmpty() && messageItem.fileUri.isNullOrEmpty()) {
        return
    }

    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.defaultPadding,
                vertical = Dimensions.defaultPaddingHalf
            )
    ) {

        val avatarImageSize =
            with(LocalDensity.current) { Dimensions.avatarImageSize.toPx() }.toInt()

        val drawable = ImageUtils.getComposeContactPlaceHolder(messageItem.senderName)
        val imageLoader = ImageRequest.Builder(context).data(messageItem.senderImageUri)
            .error(drawable).placeholder(drawable).transformations(CircleCropTransformation())
            .build()

        val painter = rememberAsyncImagePainter(model = imageLoader)

        if (messageItem.senderImageUri == null) {
            Image(
                bitmap = drawable.toBitmap(avatarImageSize, avatarImageSize).asImageBitmap(),
                contentDescription = ""
            )
        } else {
            Image(painter = painter, contentDescription = "")
        }

        Card(
            modifier = Modifier
                .padding(start = Dimensions.defaultPaddingHalf)
                .wrapContentWidth(), shape = RoundedCornerShape(Dimensions.size8)
        ) {
            ConstraintLayout(modifier = Modifier.padding(Dimensions.defaultPaddingHalf)) {
                val (username, message, messageTimeContainer) = createRefs()
                Text(
                    text = "SHREYAS",
                    fontSize = Dimensions.fontSize10,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .constrainAs(username) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    color = MaterialTheme.colorScheme.onSurface
                )

                val messageModifier = Modifier
                    .padding(top = Dimensions.padding4)
                    .constrainAs(message) {
                        start.linkTo(parent.start)
                        top.linkTo(username.bottom)
                        width = Dimension.fillToConstraints
                    }
                if (messageItem.messageContentType == MessageContentType.TEXT) {
                    messageItem.message?.let {
                        Text(
                            text = it,
                            fontSize = Dimensions.secondaryFontSize,
                            modifier = messageModifier,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = Dimensions.lineHeight
                        )
                    }
                } else {
                    val messageImageLoader = ImageRequest.Builder(context).data(messageItem.fileUri)
                        .size(400).precision(Precision.EXACT).transformations(
                            CircleCropTransformation())
                        .build()

                    Log.d(
                        "CHECKIMAGELOADER",
                        "CHEKCIN G THE IMAGE LOADER = ${messageImageLoader.allowConversionToBitmap}"
                    )

                    val bitmap:MutableState<Bitmap?> = rememberSaveable() {
                        mutableStateOf(null)
                    }

                    val scope = rememberCoroutineScope()
                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            bitmap.value = DisplayUtils.getBitmap(context, messageItem.fileUri!!)
                        }
                    }

                    bitmap.value?.asImageBitmap()?.let {
                        Image(
                            modifier = messageModifier,
                            bitmap = it,
                            contentDescription = ""
                        )
                    }

                    Log.d("CHECKIMAGE", "CHEKCIG N THE IMAGE = ${Uri.parse(messageItem.fileUri)}")
                    /*AsyncImage(modifier = messageModifier.size(300.dp), model = Uri.parse(messageItem.fileUri),
                        contentDescription = "Picture")*/

                }
                Row(modifier = Modifier.constrainAs(messageTimeContainer) {
                    top.linkTo(message.bottom)
                    end.linkTo(parent.end)
                }) {
                    val formattedString = DateTimeUtils.formatDate(
                        messageItem.messageTime,
                        ConversationUtils.TIME_FORMAT
                    )
                    Text(
                        text = formattedString,
                        fontSize = Dimensions.fontSize8,
                        modifier = Modifier
                            .padding(end = Dimensions.padding4, top = Dimensions.padding4),
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = Dimensions.lineHeight,
                        letterSpacing = Dimensions.letterSpacing
                    )


                    /*Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "", modifier = Modifier
                            .padding(top = Dimensions.padding4)
                            .size(Dimensions.size12))*/

                }
            }
        }
    }
}

@Composable
private fun ReplyMessageItem(replyMessage: ChatDetailItem.ReplyMessage) {
    MessageItem(messageItem = replyMessage.messageItem)
}


