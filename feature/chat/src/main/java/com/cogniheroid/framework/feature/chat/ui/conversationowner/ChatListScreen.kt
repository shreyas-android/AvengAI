package com.cogniheroid.framework.feature.chat.ui.conversationowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.cogniheroid.framework.feature.chat.ChatRoute
import com.cogniheroid.framework.feature.chat.R
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.ui.theme.Dimensions
import com.cogniheroid.framework.util.CalendarUtils
import com.cogniheroid.framework.util.ImageUtils
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatListScreen(navController: NavController, chatViewModel: ChatViewModel) {

    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        val (topAppBar, chatListContainer, newChatButton) = createRefs()

        val colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
        Surface(modifier = Modifier.constrainAs(topAppBar) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }, tonalElevation = 3.dp) {
            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                Text(
                    text = stringResource(id = R.string.title_chat),
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                )
            })
        }
        ChatListContainer(modifier = Modifier.constrainAs(chatListContainer) {
            top.linkTo(topAppBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        },
            chatListItem = chatViewModel.getChatListItems().collectAsState(initial =null).value,
            onNewChatClicked = {
                coroutineScope.launch {
                    chatViewModel.updateSelectedChatListItem(
                        chatViewModel.getNewChatListItem(
                            "New Chat"
                        )
                    )
                    navController.navigate(ChatRoute.CHAT_DETAIL.route)
                }
            },
            onChatClicked = {
                chatViewModel.updateSelectedChatListItem(it)
                navController.navigate(ChatRoute.CHAT_DETAIL.route)
            })

        NewChatButton(modifier = Modifier
            .navigationBarsPadding()
            .padding(end = 16.dp, bottom = 16.dp)
            .constrainAs(newChatButton) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }) {

            coroutineScope.launch {
                chatViewModel.updateSelectedChatListItem(
                    chatViewModel.getNewChatListItem(
                        "New Chat"
                    )
                )
                navController.navigate(ChatRoute.CHAT_DETAIL.route)
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

        Text(text = stringResource(id = R.string.placeholder_loading_conversation_list),
            fontSize = 14.sp,
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
private fun ChatListContainer(
    modifier: Modifier = Modifier,
    chatListItem: List<ConversationItem>?,
    onChatClicked: (ConversationItem) -> Unit,
    onNewChatClicked: () -> Unit
) {
    if (chatListItem == null){
        LoadingView()
    }
    else if (chatListItem.isEmpty()) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (placeholder, button) = createRefs()
            Text(
                modifier = Modifier.constrainAs(placeholder) {
                    bottom.linkTo(button.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                },
                text = stringResource(id = R.string.placeholder_no_conversation),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .constrainAs(button) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        onNewChatClicked()
                    }
                    .padding(16.dp),
                text = stringResource(id = R.string.placeholder_no_conversation_description),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(chatListItem) { chatListItem ->
                ChatListItemContainer(chatListItem = chatListItem, onChatClicked = {
                    onChatClicked(it)
                })
            }
        }
    }
}

@Composable
private fun ChatListItemContainer(
    chatListItem: ConversationItem,
    onChatClicked: (ConversationItem) -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onChatClicked(chatListItem)
        }
        .padding(16.dp)) {
        val (image, title, subtitle, time, unreadCount) = createRefs()

        val context = LocalContext.current


        val drawable = ImageUtils.getComposeContactPlaceHolder(chatListItem.title)
        val imageLoader = ImageRequest.Builder(context).data(chatListItem.imageUri)
            .error(drawable).placeholder(drawable).transformations(CircleCropTransformation())
            .build()
        val painter = rememberAsyncImagePainter(model = imageLoader)

        val modifier = Modifier
            .padding(end = 16.dp)
            .constrainAs(image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }

        val avatarImageSize = with(LocalDensity.current) { Dimensions.avatarImageSize.toPx() }.toInt()
        if (chatListItem.imageUri == null) {
            Image(
                modifier = modifier,
                bitmap = drawable.toBitmap(avatarImageSize, avatarImageSize).asImageBitmap(),
                contentDescription = ""
            )
        } else {
            Image(modifier = modifier, painter = painter, contentDescription = "")
        }

        val titleModifier = if (chatListItem.lastMessage!=null){
            Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(image.end)
                end.linkTo(time.start)
                width = Dimension.fillToConstraints
            }
        }else{
            Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(image.end)
                end.linkTo(time.start)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        }
        Text(
            modifier = titleModifier, text = chatListItem.title, fontSize = 16.sp, fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        chatListItem.lastMessage?.let {
            Text(
                modifier = Modifier
                    .padding(top = Dimensions.padding4)
                    .constrainAs(subtitle) {
                        top.linkTo(title.bottom)
                        start.linkTo(image.end)
                        end.linkTo(unreadCount.start)
                        width = Dimension.fillToConstraints
                    }, text = it, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface
            )
        }

        chatListItem.lastMessageTime?.let {
            Text(
                modifier = Modifier.constrainAs(time) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }, text = getTheLastMessageTime(
                    it, stringResource(id = R.string.placeholder_yesterday)
                ), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface
            )
        }

        val unReadCountModifier = if (chatListItem.lastMessageTime != null) {
            Modifier
                .constrainAs(unreadCount) {
                    top.linkTo(time.bottom)
                    end.linkTo(time.end)
                }
        } else {
            Modifier.constrainAs(unreadCount) {
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        }

        if (chatListItem.unreadCount > 0) {
            Box(
                modifier = unReadCountModifier
                    .padding(top = Dimensions.padding4)
                    .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .padding(horizontal = Dimensions.padding4),
                contentAlignment = Alignment.Center
            ) {
                Text(text = chatListItem.unreadCount.toString(), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun NewChatButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(modifier = modifier, onClick = onClick) {
        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "")
    }
}

private fun getTheLastMessageTime(time: Long, yesterday: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    val todayCalendar = Calendar.getInstance()
    val yesterdayCalendar = todayCalendar.clone() as Calendar
    yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1)

    val isToday = CalendarUtils.isSameDay(calendar, todayCalendar)
    val isYesterday = CalendarUtils.isSameDay(calendar, yesterdayCalendar)
    val isSameWeek = CalendarUtils.isCurrentWeekDay(calendar)
    val isSameYear = CalendarUtils.isCurrentYear(calendar)

    return when {
        isToday -> {
            CalendarUtils.formatDate(calendar, CalendarUtils.FORMAT_TIME)
        }

        isYesterday -> {
            yesterday
        }

        isSameWeek -> {
            CalendarUtils.formatDate(calendar, CalendarUtils.FORMAT_WEEK_DAY_DATE)
        }

        isSameYear -> {
            CalendarUtils.formatDate(calendar, CalendarUtils.FORMAT_MONTH_DATE)
        }

        else -> {
            CalendarUtils.formatDate(calendar, CalendarUtils.FORMAT_DATE)
        }
    }
}