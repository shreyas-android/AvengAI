package com.cogniheroid.framework.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.cogniheroid.framework.feature.chat.data.model.ChatListItem
import com.cogniheroid.framework.util.CalendarUtils
import com.cogniheroid.framework.util.ImageUtils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatListScreen(navController: NavController, chatViewModel: ChatViewModel) {

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
        ChatListContainer(modifier = Modifier.constrainAs(chatListContainer){
            top.linkTo(topAppBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        },
            chatListItem = listOf(
                ChatListItem(
                    1L, "2", "3",
                    4, 5
                )
            )
        ) {
            chatViewModel.updateSelectedChatListItem(it)
            navController.navigate(ChatRoute.CHAT_DETAIL.route)
        }

        NewChatButton(modifier = Modifier.navigationBarsPadding().padding(end = 16.dp, bottom = 16.dp).constrainAs(newChatButton){
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {

        }


    }

}


@Composable
private fun ChatListContainer(
    modifier: Modifier = Modifier,
    chatListItem: List<ChatListItem>,
    onChatClicked: (ChatListItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(chatListItem) {
            ChatListItemContainer(chatListItem = chatListItem[0], onChatClicked = {
                onChatClicked(it)
            })
        }
    }
}

@Composable
private fun ChatListItemContainer(
    chatListItem: ChatListItem,
    onChatClicked: (ChatListItem) -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
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

        Image(modifier = Modifier.constrainAs(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
        }, painter = painter, contentDescription = "")

        Text(modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top)
            start.linkTo(image.end)
            end.linkTo(time.start)
            width = Dimension.fillToConstraints
        }, text = chatListItem.title, fontSize = 16.sp)

        Text(modifier = Modifier
            .padding(top = 4.dp)
            .constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(image.end)
                end.linkTo(unreadCount.start)
                width = Dimension.fillToConstraints
            }, text = chatListItem.subtitle, fontSize = 14.sp
        )

        Text(
            modifier = Modifier.constrainAs(time) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }, text = getTheLastMessageTime(
                chatListItem.lastMessageMillis,
                stringResource(id = R.string.placeholder_yesterday)
            ), fontSize = 12.sp
        )

        Box(modifier = Modifier
            .padding(top = 4.dp)
            .constrainAs(unreadCount) {
                top.linkTo(time.bottom)
                end.linkTo(time.end)
            }
            .background(color = Color.Red, shape = CircleShape)
            .padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center) {
            Text(text = chatListItem.unreadCount.toString(), fontSize = 12.sp)
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