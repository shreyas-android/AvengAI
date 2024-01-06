package com.cogniheroid.framework.feature.chat

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cogniheroid.android.ad.ui.theme.ComposeUITheme
import com.cogniheroid.framework.feature.chat.data.model.ChatListItem

enum class ChatRoute(val route: String) {
    CHAT_DETAIL("chat_detail"),
    HOME("home")
}

@Composable
fun ChatScreen() {
    val chatViewModel = viewModel<ChatViewModel>()
    ComposeUITheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ChatRoute.HOME.route) {

            composable(ChatRoute.HOME.route) {
               ChatListScreen(navController, chatViewModel)
            }

            composable(ChatRoute.CHAT_DETAIL.route) {
               chatViewModel.getSelectedChatListItem()?.let {
                    ChatDetailScreen(chatViewModel, chatListItem = it){
                        navController.navigateUp()
                    }
                }
            }


        }
    }

}