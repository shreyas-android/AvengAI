package com.cogniheroid.framework.feature.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.drawable.toBitmap
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.cogniheroid.framework.feature.chat.data.model.ChatDetailItem
import com.cogniheroid.framework.feature.chat.data.model.MessageItem
import com.cogniheroid.framework.feature.chat.data.model.ChatListItem
import com.cogniheroid.framework.util.ImageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatDetailScreen(
    chatViewModel: ChatViewModel,
    chatListItem: ChatListItem,
    navigateBack: () -> Unit
) {

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
        Surface(tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier
                .constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .statusBarsPadding(), colors = colors, title = {
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
                            Image(modifier = modifier, bitmap = drawable.toBitmap(avatarImageSize, avatarImageSize).asImageBitmap(), contentDescription = "")
                        }else{
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

        ChatDetailContainer(modifier = Modifier.constrainAs(chatList) {
            top.linkTo(topAppBar.bottom)
            bottom.linkTo(messageField.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }, chatViewModel.getChatDetailMap(chatListItem.messageItems))

        SendMessageField(modifier = Modifier.constrainAs(messageField) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }, onValueChange = {
            message.value = it
        }, onSendClick = {

        }, value = message.value)

    }

}

enum class SentSButtonState {
    SEND, VOICE
}

@Composable
fun SendMessageField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .navigationBarsPadding()
            .imePadding()
            .fillMaxWidth()
    ) {
        val (textField, sendButton) = createRefs()
        BasicTextField(modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(20.dp)
            )
            .constrainAs(textField) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(sendButton.start)
                width = Dimension.fillToConstraints
            }
            .padding(horizontal = 16.dp, vertical = 8.dp), value = value, onValueChange = {
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
                            fontSize = 14.sp
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

                    },
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = ""
                )

            }
        })

        val sendIcon = if (value.isEmpty()) {
            R.drawable.ic_mic
        } else {
            R.drawable.ic_send
        }


        IconButton(onClick = {
            onSendClick()
        }, modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(sendButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .background(shape = CircleShape, color = MaterialTheme.colorScheme.surface)) {
            Icon(painter = painterResource(id = sendIcon), contentDescription = "")
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatDetailContainer(
    modifier: Modifier = Modifier,
    chatItemsMap: Map<String, List<ChatDetailItem>>
) {

    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        val scrollIndex = if (chatItemsMap.values.isNotEmpty()) {
            chatItemsMap.values.size - 1
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
    Column(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
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
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = dateHeader, fontSize = 14.sp, modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        )
    }
}

@Composable
private fun NormalMessageItem(normalMessage: ChatDetailItem.NormalMessage) {
    MessageItem(messageItem = normalMessage.messageItem)
}

@Composable
private fun MessageItem(messageItem: MessageItem) {
    ConstraintLayout {
        val (image, username, message, messageTime, messageSent) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "")

        Text(text = messageItem.user.name, fontSize = 14.sp, modifier = Modifier
            .constrainAs(username) {
                start.linkTo(image.end)
                top.linkTo(parent.top)
            }
            .background(
                color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp))
        Text(text = messageItem.message, fontSize = 14.sp, modifier = Modifier
            .constrainAs(message) {
                start.linkTo(image.end)
                top.linkTo(username.bottom)
            }
            .background(
                color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp))

        Text(
            text = messageItem.messageTime.toString(),
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(messageTime) {
                end.linkTo(parent.end)
                top.linkTo(message.top)
            })

        Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "", modifier = Modifier.constrainAs(messageSent) {
                end.linkTo(messageTime.start)
                top.linkTo(message.top)
            })

    }
}

@Composable
private fun ReplyMessageItem(replyMessage: ChatDetailItem.ReplyMessage) {
    MessageItem(messageItem = replyMessage.messageItem)
}


